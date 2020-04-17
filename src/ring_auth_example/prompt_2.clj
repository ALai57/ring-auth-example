(ns ring-auth-example.prompt-2)

;; Ring apps are single arity functions that expect the input to be a request
;;  map (like the one you sent in the first prompt.) Then those requests are
;;  routed the appropriate handlers

;; Routing happens through a routing layer. We are going to use a routing
;;  library, Compojure. This library will route our requests to the appropriate
;;  handling function. However, it also transforms our original request!

;; Build a simple Ring app using [compojure.api.sweet :refer [api]]. This app
;;  should have a single endpoint, GET "/". By binding the request to a symbol,
;;  you can inspect it later: here's a suggestion to get you going:
;;  (api (GET "/" request #_(YOUR CODE HERE))). With the code above, `request`
;;  is in scope for the GET "/" endpoint.

;; While the `request` map is in scope, inspect it (println or somesuch)!
;;  Extract all the keys and turn them into a sorted set

;; To unlock the next part of the exercise, decrypt third_prompt.clj with
;;  the sorted set you just created.


(comment

  )
