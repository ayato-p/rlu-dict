(ns rlu-dict.middleware
  (:require [ring.middleware.defaults :as ring-middleware]))

(defn create-from-conf [handler conf]
  (-> handler
      (ring-middleware/wrap-defaults ring-middleware/site-defaults)))
