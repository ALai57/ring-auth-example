(ns ring-auth-example.answers
  (:require [ring-auth-example.encryption :refer [encrypt-problem
                                                  decrypt-problem
                                                  write-to-file
                                                  file->bytes]]
            [ring.mock.request :as mock]
            [clojure.string :as str]))


(defn encrypt-file [encryption-key file-name]
  (-> encryption-key
      (encrypt-problem (file->bytes file-name))
      (write-to-file (str file-name ".encrypted"))))

(defn decrypt-file [encryption-key file-name]
  (->> encryption-key
       (decrypt-problem (str file-name ".encrypted"))
       (spit (str file-name ".decrypted"))))

(def encryption-keys [{:decryption-key "Let's get started!"}
                      (into (sorted-map) (mock/request :get "/"))
                      #{:form-params
                        :headers
                        :params
                        :protocol
                        :query-params
                        :remote-addr
                        :request-method
                        :route-params
                        :scheme
                        :server-name
                        :server-port
                        :uri
                        :compojure/route
                        :compojure.api.middleware/options
                        :ring.swagger.middleware/data}
                      "ring-session=my-cookie;Path=/;HttpOnly"
                      {"ring-session" {:value "yum, cookies!"},
                       "Path" {:value "/"}}
                      {"cookie monster" {:username "my-user"}}
                      (into (sorted-set) #{:session :session/key})])

(map (fn [encryption-key n]
       (encrypt-file encryption-key (str "src/ring_auth_example/prompt_" n ".clj")))
     encryption-keys
     (range 1 8))
