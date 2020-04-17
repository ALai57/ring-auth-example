(ns ring-auth-example.prompt-7
  (:require [org.httpkit.server :as server]
            [buddy.auth.backends.session :refer [session-backend]]
            [buddy.auth.middleware :refer [wrap-authentication]]
            [compojure.api.sweet :refer [api GET POST]]
            [cheshire.core :as json]
            [taoensso.timbre :as log]
            [taoensso.timbre.appenders.core :as appenders]
            [ring.util.http-response :refer [ok]]
            [ring.middleware.cookies :refer [wrap-cookies]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.middleware.session.memory :as mem]
            )
  (:gen-class))

;; This is a map between cookies and sessions
;; Wrap sessions takes the cookie, and looks in this database to find the
;; corresponding session
(def cookie->session (atom {}))

(defn wrap-components
  "Add components into the request map"
  [handler components]
  (fn [request]
    (handler (assoc request :components components))))

(defn log-writer
  "This appender writes to a file so you can see the logs later"
  [fname]
  (assoc log/example-config
         :appenders
         {:spit (appenders/spit-appender {:fname fname})}))

(defn wrap-logging
  "Wraps all incoming requests and logs them"
  [handler]
  (fn [{:keys [components request-method uri] :as request}]
    (log/with-config (:logging components)
      (log/infof "%s request received for %s" request-method uri)
      (handler request))))

(defprotocol Authentication
  (check-password [this credentials]))

(defrecord Database [db-atom]
  Authentication
  (check-password [this credentials]
    (let [{:keys [username password]} credentials
          {true-password :password :as user} (get @db-atom username)]
      (when (= true-password password)
        (dissoc user :password)))))

(defn app
  "Given a set of components to use, I handle all Ring requests
  and return responses

  An example component: Logging
  You could pass in a logger that writes to a file, or a logger that writes to
  STDOUT"
  [components]
  (-> (api
        (GET "/" {:keys [components]}
          (ok {:status 200, :body "ping!"}))

        (POST "/login" {:keys [body session] :as request}
          (let [credentials (-> body
                                slurp
                                (json/parse-string keyword))
                user (check-password (:db components) credentials)]
            (if user 
              (assoc (ok {:message "Generating session"})
                     :session
                     (:username credentials))
              (ok {:status 200, :body "username/login is incorrect"}))
            )
          )

        (POST "/echo" request
          (clojure.pprint/pprint (select-keys request [:session :session/key]))
          (log/info request)))

      wrap-logging
      (wrap-session (or (:session-options components) {}))
      wrap-cookies
      (wrap-components components)))


;; VERIFYING AUTHENTICATION
(comment
  (check-password (->Database (atom {"andrew" {:password "mypassword"
                                               :avatar "someurl.com/andrew"
                                               :id 1}}))
                  {:username "andrew"
                   :password "mypassword"})

  (require '[ring.mock.request :as mock])

  ;; Prove that it works!
  (def session-cookie
    (let [app-with-components
          (app {:logging (log-writer "log.txt")
                :session-options {:store (mem/memory-store cookie->session)}
                :db (->Database (atom {"test-user" {:password "password"
                                                    :id 2
                                                    :avatar "someurl.com/test-user"}}))})
          response (-> :post
                       (mock/request "/login"
                                     (json/generate-string {:username "test-user"
                                                            :password "password"}))
                       app-with-components)]
      (-> response
          :headers
          (get "Set-Cookie")
          first)))

  ;; Let's take a look at what's in the session store
  @cookie->session

  ;; Hit the echo endpoint and see what happens when we add a cookie to the
  ;; header, and without a cookie in the header.
  (let [app-with-components
        (app {:logging (log-writer "log.txt")
              :session-options {:store (mem/memory-store cookie->session)}})]
    (-> :post
        (mock/request "/echo")
        (assoc-in [:headers "cookie"] session-cookie)
        app-with-components))

  (let [app-with-components
        (app {:logging (log-writer "log.txt")
              :session-options {:store (mem/memory-store cookie->session)}})]
    (-> :post
        (mock/request "/echo")
        app-with-components))

  )
