(defproject rlu_dict "0.1.0-SNAPSHOT"
  :description "Reverse lookup dictionary for Japanese Clojurians"
  :url "http://example.com/FIXME"
  :license {:name "MIT License"}
  :source-paths ["src" "src-cljc"]
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [ring/ring-core "1.4.0"] ;; Ring
                 [org.immutant/immutant "2.1.0"] ;; Server
                 ;; [bidi "1.20.3"] ;; Routing
                 [compojure "1.4.0"] ;; Routing
                 [funcool/clojure.jdbc "0.6.1"] ;; JDBC
                 [org.postgresql/postgresql "9.4-1201-jdbc41"] ;; PostgreSQL
                 [hikari-cp "1.2.4"] ;; Connection pool
                 [stch-library/sql "0.1.1"] ;; SQL DSL
                 [enlive "1.1.6"] ;; Template
                 [com.stuartsierra/component "0.3.0"] ;; Component
                 [com.taoensso/timbre "4.1.4"] ;; Logging

                 ;; Middlewares
                 [ring/ring-defaults "0.1.5"]
                 [ring/ring-devel "1.4.0"]

                 ;; Misc
                 [buddy/buddy-auth "0.7.1"]
                 [inflections "0.9.14"]
                 [potemkin "0.4.1"]
                 [metosin/ring-http-response "0.6.5"]
                 [prismatic/schema "1.0.1"]
                 [markdown-clj "0.9.74"]
                 [clj-http "1.1.2"]
                 [cheshire "5.5.0"]]
  :main rlu_dict.main
  :uberjar-name "rlu-dict-standalone.jar"
  :profiles
  {:uberjar
   {:aot :all}

   :dev
   {:source-paths ["env/dev/clj"]
    :dependencies [[clj-liquibase "0.5.3"]]
    :plugins [[lein-exec "0.3.5"]]
    :repl-options {:init-ns rlu-dict.repl}
    :aliases {"migrate" ["do"
                         ["exec" "-p" "etc/migration.clj"]]}}})
