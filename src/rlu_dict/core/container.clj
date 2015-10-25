(ns rlu-dict.core.container
  (:require [rlu-dict.db :as db]
            [stch.sql :as s]))

(defn top-contributors [limit]
  (-> (s/select :m.* ['(count-distinct r.title) :recipe-count])
      (s/from [:member :m])
      (s/left-join [:recipe :r]
                   `(= r.member-id m.id))
      (s/group :m.id)
      (s/order-by :recipe-count)
      (s/limit limit)
      db/fetch))

(defn resently-updated-recipes [limit]
  (-> (s/select :r.* [:m.name :member-name] :m.icon-img)
      (s/from [:recipe :r])
      (s/left-join [:member :m]
                   '(= m.id r.member-id))
      (s/limit limit)
      db/fetch))
