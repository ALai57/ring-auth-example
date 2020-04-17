(ns ring-auth-example.prompt-3
  (:require [compojure.api.sweet :refer [api GET]]
            [ring.mock.request :as mock]))

;; By now, it should be clear that some transformations happen between the
;;  requests that are sent, and the requests that are seen by the handler
;;  functions for a given route (e.g. ). We can customize those transformations
;;  by adding middleware to our app.

;; Let's try wrapping our program with a middleware, wrap-session.

;; wrap-session is used to keep track of our users sessions. It does this by
;;  inspecting the response our handler sends back to the client.

;; For example, if we have a route defined by this code:
;;      (GET "/" request {:status 200 :body "Ok!"})
;; The response is the map, {:status 200 :body "Ok!"}. wrap-session inspects
;;  that map and transforms it before it gets sent back to the client

;; If the response contains a :session key, then wrap-session will
;;  1) Create a random uuid that represents the current session
;;  2) Send that UUID to the client (in the response header), and tell it to set
;; it as the session cookie

;; Our goal is to:
;;  1) Add the following key-value pair to your (GET "/") route's response map:
;;     :session "my-session"
;;  2) Send a ring request to your handler (to the GET "/" endpoint you just
;;     modified)
;;  3) Save the response you get from the handler and inspect it's headers
;;  4) Your headers should be a clojure map. Verify that one of the keys is
;;     "Set-Cookie"
;;  5) Get the values associated with the "Set-Cookie" key in the response header
;;  6) This will be a string, and it will contain, among other things, a UUID.
;;     Replace that UUID with the string "my-cookie"

;; To unlock the next part of the exercise, decrypt fourth_prompt.clj with
;;   the results from above:

;; NOTE: Useful regex below


(comment

  (defn replace-uuid [some-string]
    (clojure.string/replace some-string
                            #"[0-9a-fA-F]{8}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{12}"
                            "my-cookie"))
  )
