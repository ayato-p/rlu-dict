(ns rlu-dict.query
  (:refer-clojure :exclude [count])
  (:require [stch.sql :as s]))

(def default-limit 10)

(defn page
  ([m page]
   (page m page default-limit))
  ([m page limit]
   (-> m
       (s/offset (* (dec page) limit))
       (s/limit limit))))

(defn count [m]
  (-> m
      (s/replace-select '(count *))
      (dissoc :order-by)))
