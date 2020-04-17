(ns ring-auth-example.prompt-4)

;; We just got a cookie back from our ring handler. Behind the scenes, we also
;;  added that cookie to our session store, which is a databae that maps
;;  cookies-> sessions. In the future, we can use the cookie to look up the
;;  session-related-data (session-related data is all data under the :session
;;  key in a request map, in our case, it's just the value "my-session")

;; Normally, this cookie is stored by the client (in Google Chrome, you can see
;;  this saved under inspector->application->cookies). With subsequent requests,
;;  the cookie would be sent back to the server (by the client), and used to
;;  look up a session on the server.

;; However, right now, we don't have a good way of ingesting any cookies from
;;  the request and turning them into clojure data structures. Moving forward,
;;  we will want to make sure we are able to translate cookies in the request
;;  into appropriate clojure data structures

;; To do that, we can add additional middleware that wraps cookies. The
;;  middleware is from [ring.middleware.cookies :refer [wrap-cookies]] which
;;  will take incoming cookies on the HTTP header, and parse them into clojure
;;  data structures.

;; When we add this middleware, any time a request contains a cookie, the
;;  middleware will take the cookie in the header, parse the cookie (which is
;;  just a string at this point), and the results of the parsing the cookie onto
;;  the request map, under the key :cookies.

;; Now, do the following:
;;  1) Add the wrap-cookies middleware to your handler.
;;  2) Send your Ring app a request to the GET "/" endpoint. Save the response
;;     and extract the values from the `Set-Cookie` header the same way you did
;;     before.
;;  3) Send your app another request to the GET "/" endpoint, but this time, add
;;     a cookie onto the request. To do this, generate your ring request map,
;;     and assoc-in [:headers "cookie"] with the value you extracted from the
;;     previous request (should be something like
;;     "ring-session=someUUID;Path=/;HttpOnly")
;;  4) Inspect the request when it's in scope inside your GET "/" route. Extract
;;     anything under the :cookies key

;; To unlock the next part of the exercise, decrypt fifth_prompt.clj with the
;;  values associated with the :cookies key (the ones you just extracted above).

;; Since cookies (and sessions) are generated randomly, you'll need to replace
;;  any UUIDs with the string "yum, cookies!"
