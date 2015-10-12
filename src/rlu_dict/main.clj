(ns rlu-dict.main
  (:require [clojure.edn :as edn]
            [rlu-dict.system :as system])
  (:gen-class))

(defonce system nil)

(defn -main [& args]
  (system/boot #'system (edn/read-string (slurp "resources/config.edn"))))
