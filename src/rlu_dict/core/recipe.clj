(ns rlu-dict.core.recipe
  (:require [rlu-dict.db :as db]
            [rlu-dict.entity.category :as c]
            [rlu-dict.entity.common :refer [update-datetime]]
            [rlu-dict.entity.recipe :as r]
            [rlu-dict.query :as q]
            [rlu-dict.security.auth :as auth]
            [stch.sql :as s]))

(def limit 10)

(defn find-recipe* []
  (-> (r/find-recipe*)
      (s/select [:m.id :member-id] [:m.name :member-name] :m.icon-img)
      (s/left-join [:member :m]
                   '(= :r.member-id :m.id))
      (s/order-by [:r.updated-at :desc])))

(defn find-by-page [page]
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

(defn attach-category [recipe category]
  (let [r-id (:id recipe)
        c-id (:id category)
        m (update-datetime {:recipe-id r-id :category-id c-id})]
    (-> (s/insert-into :recipe-category)
        (s/values [m])
        db/execute
        first)))

(defn save-recipe [{:as req :keys [params]}]
  (let [{:keys [category title content]} params
        m-id (:id (auth/current-member req))
        c (c/make-category {:name category})
        r (r/make-recipe {:title title :content content :member-id m-id})]
    (db/atomic
     (let [r' (r/save-recipe r)
           c' (c/save-category c)]
       (attach-category r' c')
       r'))))
