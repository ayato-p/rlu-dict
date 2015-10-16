(ns rlu-dict.middleware.github)

(defn wrap-github [handler {:as conf :keys [client_id client_secret]}]
  (fn [req]
    (handler (assoc req :github conf))))
