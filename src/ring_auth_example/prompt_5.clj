(ns ring-auth-example.prompt-5)

;; We're getting close =)

;; Last prompt, we found out that generating a cookie actually causesed us to
;;  save the session in a session store behind the scenes. Remember that the
;;   session storage maintains the mapping, cookie->session

;; We got this persistent storage for free, when we used wrap-session! But let's
;;  take a closer look.

;; wrap-session takes an optional map that configures session storage. At the
;;  moment, we haven't used a configuration map, so we get the default
;;  configuration - which is an atom that implements the SessionStore protocol
;;  from ring.middleware.session.memory. That protocol is what's used to perform
;;  the critical session storage functions.

;; In our naive implementation, we didn't specify session storage and hold
;;  onto a reference to it. So we can't easily inspect the default
;;  session-storage that is used

;; For this exercise, we want to explicitly set the session storage.
;;  [ring.middleware.session.memory :as mem] has a session storage option, called
;;  memory-store, which we will use. Try creating a session store using a map
;;  inside an atom (atom {})

;; Go to definition on the wrap session middleware. It's documented very well.
;; By looking at the source, see if you can identify what we need to do to
;; configure session storage

;; Next:
;;   1) Explicitly set the session storage in wrap-sessions
;;   2) Send a request to our GET "/" endpoint. This should generate a session -
;;      verify this by inspecting the response to confirm there is a Set-cookie
;;      header
;;   3) If you have a Set-Cookie header, the middleware also made a session in
;;      the session store! Inspect the session storage, see that you actually
;;      have something in there!
;;   4) In your GET "/" endpoint, we want to change the session data. Change the
;;      session data from "my-session", to the map {:username "my-user"}
;;   5) Send another request to your GET "/" endpoint.
;;   6) Inspect your session storage. Select the key-value pair associated with
;;      your new session. You should have a map with one key. Replace any UUIDs
;;      with the string "cookie monster"

;; When you've got this working, decrypt the next prompt with the map you got
;;  from following the instructions above.
