(ns rlu-dict.system
  (:require [com.stuartsierra.component :as c]
            [rlu-dict.component.github :as github]
            [rlu-dict.component.db :as db]
            [rlu-dict.component.webserver :as webserver]))

(defn system-map [conf]
  {:db (db/new-database conf)
   :github (github/new-github conf)
   :webserver (c/using
               (webserver/new-webserver conf)
               [:db :github])})

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
