(ns rlu-dict.middleware
  (:require [ring.middleware.defaults :as ring-middleware]
            [ring.middleware.reload :as reload]
            [rlu-dict.middleware.db :as db]
            [rlu-dict.middleware.github :as github]))

(defn create-from-conf [handler conf]
  (-> handler
      reload/wrap-reload
      (ring-middleware/wrap-defaults ring-middleware/site-defaults)
      (db/wrap-connection-source (:connection-source conf))
      (github/wrap-github (:github conf))))
