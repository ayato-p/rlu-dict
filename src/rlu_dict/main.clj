(ns rlu-dict.main
  (:require [clojure.edn :as edn]
            [environ.core :refer [env]]
            [rlu-dict.system :as system])
  (:gen-class))

(defonce system nil)

(def options
  {:db
   {:vendor (env :db-vendor)
    :name (env :db-name)
    :host (env :db-host)
    :port (env :db-port)
    :user (env :db-user)
    :password (env :db-password)
    :sslmode (env :db-sslmode)}

   :webserver
   {:host "0.0.0.0"
    :port (env :port)}

   :github
   {:client_id (env :github-client-id)
    :client_secret (env :github-client-secret)}})

(defn -main [& args]
  (system/boot #'system options))
