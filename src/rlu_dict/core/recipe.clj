(ns rlu-dict.core.recipe
  (:require [rlu-dict.db :as db]
            [rlu-dict.entity.recipe :as r]
            [rlu-dict.query :as q]
            [stch.sql :as s]))

(def limit 10)

(defn find-recipe* []
  (-> (r/find-recipe*)
      (s/select [:m.id :member-id] [:m.name :member-name] :m.icon-img)
      (s/left-join [:member :m]
                   '(= :r.member-id :m.id))
      (s/order-by [:r.updated-at :desc])))

(defn find-recipe-by-page [page]
  (-> (find-recipe*)
      (q/page page limit)
      db/fetch))

(defn max-page* [m]
  (let [num (-> m
                (q/count)
                db/fetch-one
                :count)]
    (long (Math/ceil (/ num limit)))))

(defn find-recipe-max-page []
  (max-page* (find-recipe*)))
