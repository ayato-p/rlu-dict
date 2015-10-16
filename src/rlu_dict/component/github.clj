(ns rlu-dict.component.github
  (:require [com.stuartsierra.component :as c]))

(defrecord GitHub [conf]
  c/Lifecycle
  (start [c]
    (assoc c :github conf))
  (stop [c]
    (assoc c :github nil)))

(defn new-github [conf]
  (map->GitHub {:conf (:github conf)}))
