(ns rlu-dict.middleware
  (:require [immutant.web.middleware :as web]
            [ring.middleware.defaults :as ring-middleware]
            [rlu-dict.middleware.auth :as auth]
            [rlu-dict.middleware.db :as db]
            [rlu-dict.middleware.github :as github]))

(defn create-from-conf [handler conf]
  (-> handler
      web/wrap-development
      auth/wrap-auth
      (ring-middleware/wrap-defaults ring-middleware/site-defaults)
      (db/wrap-connection-source (:connection-source conf))
      (github/wrap-github (:github conf))))
