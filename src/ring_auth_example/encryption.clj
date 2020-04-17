(ns ring-auth-example.encryption
  (:require [buddy.core.crypto :as crypto]
            [buddy.core.codecs :as codecs]
            [buddy.core.codecs.base64 :as b64]
            [buddy.core.nonce :as nonce]
            [buddy.core.hash :as hash]))

;; Welcome to the Clojurepalooza workshop on using Ring to manage sessions and
;;  authenticate users!
;; Feel free to peruse this source code if you'd like, and when you're ready,
;;  take a look at ring-auth-example.prompt-0.clj

(def initialization-vec (b64/encode "111111111111"))

(defn file->bytes [file]
  (with-open [xin (clojure.java.io/input-stream (clojure.java.io/file file))
              xout (java.io.ByteArrayOutputStream.)]
    (clojure.java.io/copy xin xout)
    (.toByteArray xout)))

(defn hash-encryption-key [encryption-key]
  (-> encryption-key
      hash
      str
      hash/sha256))

(defn decrypt-problem [file-name decryption-key]
  (-> file-name
      file->bytes
      (crypto/decrypt (hash-encryption-key decryption-key) initialization-vec {:algorithm :aes128-cbc-hmac-sha256})
      (codecs/bytes->str)))

(defn write-to-file [the-bytes the-file]
  (with-open [o (clojure.java.io/output-stream the-file)]
    (.write o the-bytes)))

(defn encrypt-problem
  "encryption-key: should be a plain clojure object
  problem: should be byte array"
  [encryption-key problem]
  (crypto/encrypt problem (hash-encryption-key encryption-key) initialization-vec
                  {:algorithm :aes128-cbc-hmac-sha256}))


(comment
  ;; This is an example showing how to encrypt and decrypt project.clj

  ;; Encrypt and decrypt project.clj
  (-> {:decryption-key "Showing how this works"}
      (encrypt-problem (file->bytes "project.clj"))
      (write-to-file "project.clj.encrypted"))

  (->> {:decryption-key "Showing how this works"}
       (decrypt-problem "project.clj.encrypted")
       (spit "project.clj.decrypted"))

  )
