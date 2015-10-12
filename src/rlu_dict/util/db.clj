(ns rlu-dict.util.db
  (:require [clojure.set :as set]
            [jdbc.core :as jdbc])
  (:import javax.sql.DataSource))

(defn dbspec->hikari-cp-spec [conf]
  (-> conf
      (set/rename-keys {:vendor :adapter
                        :name   :database-name
                        :host   :server-name
                        :port   :port-number
                        :user   :username})))

(deftype SimpleDataSource [dbspec]
  DataSource
  (getConnection [this]
    (.connection (jdbc/connection dbspec)))
  (getConnection [this username password]
    (.connection (jdbc/connection dbspec {:user username
                                          :password password}))))

(defn make-simple-datasource [dbspec]
  (->SimpleDataSource dbspec))
