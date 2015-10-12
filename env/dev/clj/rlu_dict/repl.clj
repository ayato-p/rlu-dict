(ns rlu-dict.repl
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.pprint :refer (pprint)]
            [clojure.repl :refer :all]
            [clojure.string :as str]
            [clojure.test :as test]
            [clojure.tools.namespace.repl :refer (refresh refresh-all)]
            [com.stuartsierra.component :as c]
            [rlu-dict.system :as system]))

(defn- config-reader []
  (edn/read-string (slurp "dev-resources/config-local.edn")))

(def system nil)

(defn start
  "Starts the current development system."
  []
  (system/boot #'system (config-reader)))

(defn stop
  "Shuts down and destroys the current development system."
  []
  (system/shutdown #'system))

(defn go
  "Initializes the current development system and starts it running."
  []
  (start))

(defn reset []
  (stop)
  (refresh :after 'rlu-dict.repl/go))
