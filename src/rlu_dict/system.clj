(ns rlu-dict.system
  (:require [com.stuartsierra.component :as c]
            [rlu-dict.component.webserver :as webserver]
            [rlu-dict.component.db :as db]))

(defn system-map [conf]
  {:db (db/new-database conf)
   :webserver (c/using
               (webserver/new-webserver conf)
               [:db])})

(defn new-system [conf]
  (c/map->SystemMap (system-map conf)))

(defn boot [sys-var conf]
  (alter-var-root
   sys-var
   (fn [s]
     (if (nil? s)
       (c/start (new-system conf))))))

(defn shutdown [sys-var]
  (alter-var-root
   sys-var
   (fn [s]
     (if s
       (c/stop s))
     nil)))
