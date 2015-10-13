(ns rlu-dict.middleware
  (:require [ring.middleware.defaults :as ring-middleware]
            [rlu-dict.middleware.db :as db]
            [ring.middleware.reload :as reload]))

(defn create-from-conf [handler conf]
  (-> handler
      reload/wrap-reload
      (ring-middleware/wrap-defaults ring-middleware/site-defaults)
      (db/wrap-connection-source (:connection-source conf))))
