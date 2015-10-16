(ns rlu-dict.entity.common)

(defn current-time []
  (java.sql.Timestamp. (System/currentTimeMillis)))

(defn update-datetime [m]
  {:pre [(map? m)]}
  (let [now (current-time)]
    (cond-> m
      (nil? (:created-at m)) (assoc :created-at now)
      :else (assoc :updated-at now))))
