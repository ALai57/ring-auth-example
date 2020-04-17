(ns ring-auth-example.prompt-6)


;; We've verified that your session storage atom has something, time to use it!

;; Do the same thing you did last time:
;;   1) Explicitly set the session storage in wrap-sessions
;;   2) Send a request to your GET "/" endpoint (this should generate a session)
;;   3) Save the response you get. Inspect the header and save the cookie.
;;   4) Inspect the session storage, see that you actually have something in there!

;; Now, we'll add the finishing touches:
;;   5) Add a GET "/echo" route to your handler. The echo route should bind the
;;       request so that it's in scope, and then it should print out the
;;       request. But because this will print AFTER the middleware runs, it will
;;       allow us to see how the wrap-session middleware is working
;;   6) Send a request BACK to the handler, adding the session cookie in the
;;       header
;;   7) Send a request BACK to the handler, DO NOT add a session cookie in the
;;       header

;; Inspect the results that you printed!

;; What did the wrap-session middleware do when you added the cookie?
;; What did the wrap-session middleware do when you didn't add the cookie?

;; Specifically, inspect your request map that you printed out. What keys were
;; modified?

;; When you have your answer, put the keys you identified into a sorted-set.
;; Decrypt the next prompt with this sorted set
