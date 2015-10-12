(ns rlu-dict.middleware.db
  (:require [rlu-dict.db :as db]))

(defn wrap-connection-source [handler connection-source]
  (fn [req]
    (db/with-connection-source connection-source
      (handler req))))
