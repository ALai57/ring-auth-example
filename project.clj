(defproject ring-auth-example "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 ;; For compatibility with Java 11
                 [org.flatland/ordered "1.5.9"]

                 ;; Routing library
                 [metosin/compojure-api "1.1.12"]

                 ;; Server
                 [http-kit "2.3.0"]

                 ;; Logging
                 [com.taoensso/timbre "4.10.0"]

                 ;; JSON parsing
                 [cheshire "5.10.0"]

                 ;; Ring
                 [ring "1.8.0"]

                 ;; For sending mock requests to the handler
                 [ring/ring-mock "0.4.0"]

                 ;; Authentication
                 [buddy/buddy-auth "2.2.0"]

                 ;; Required to make program not crash?
                 ;; For some reason excluding this crashes the program...
                 [org.clojure/tools.reader "1.2.2"]
                 ]
  :main ^:skip-aot ring-auth-example.core

  :plugins [[lein-figwheel "0.5.19"] ;; For interactive CLJS
            [lein-cljsbuild "1.1.7"] ;; For building JS client
            ]

  :ring {:handler ring_auth_example.core/app
         ;;:init ring_auth_example.core/init-routes
         }

  :figwheel {:ring-handler ring_auth_example.core/app
             :css-dirs ["resources/public/css"]}

  :target-path "target/%s"

  :profiles {:uberjar {:aot :all}})
