(ns rlu-dict.component.db
  (:require [com.stuartsierra.component :as c]
            [jdbc.core :as jdbc]
            [jdbc.proto :as jproto]
            [rlu-dict.util.db :as ud]
            [taoensso.timbre :as log]))

(defn- make-datasource [{:keys [use-pool?] :as conf}]
  (let [spec (dissoc conf :use-pool?)]
    (if use-pool?
      nil
      (ud/make-simple-datasource spec))))

(defrecord Db [dbspec datasource]
  jproto/IConnection
  (connection [this]
    (jproto/connection datasource))

  c/Lifecycle
  (start [c]
    (log/info "Start Database")
    (if datasource
      c
      (assoc c :datasource (make-datasource dbspec))))
  (stop [c]
    (log/info "Stop Database")
    (if datasource
      (do
        (if (instance? java.io.Closeable datasource)
          (.close datasource))
        (assoc c :datasource nil))
      c)))

(defn new-database [conf]
  (map->Db {:dbspec (:db conf)}))
