(ns rlu-dict.entity.member
  (:require [rlu-dict.db :as db]
            [rlu-dict.entity.common :refer [update-datetime]]
            [schema.core :as sc]
            [stch.sql :as s]))

(sc/defrecord Member
    [id         :- (sc/maybe sc/Int)
     name       :- sc/Str
     github-id  :- sc/Int
     icon-img   :- (sc/maybe sc/Str)
     created-at :- sc/Inst
     updated-at :- sc/Inst])

(def default-member (map->Member {}))

(defn create-member [m]
  {:pre [(map? m)]}
  (merge default-member
         (select-keys m (keys default-member))))

(defn find-member [& [where-clause]]
  (-> (s/select :*)
      (s/from :member)
      (s/where (or where-clause '(= 1 1)))
      db/fetch))

(defn find-first-member [& [where-clause]]
  (first (find-member where-clause)))

(defn save-member [m]
  {:pre [(map? m)]}
  (let [m (-> m
              update-datetime
              (dissoc :id))]
    (-> (s/insert-into :member)
        (s/values [m])
        db/execute
        first)))

(defn find-or-save-member [m]
  (let [where-clause `(= github-id ~(:github-id m))]
    (if-let [member (find-first-member where-clause)]
      member
      (save-member m))))
