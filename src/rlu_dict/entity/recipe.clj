(ns rlu-dict.entity.recipe
  (:require [rlu-dict.db :as db]
            [rlu-dict.entity.common :refer [update-datetime]]
            [schema.core :as sc]
            [stch.sql :as s]))

(sc/defrecord Recipe
    [id         :- (sc/maybe sc/Int)
     title      :- sc/Str
     content    :- sc/Str
     member-id  :- sc/Int
     request-id :- (sc/maybe sc/Int)
     created-at :- sc/Inst
     updated-at :- sc/Inst])

(def default-recipe (map->Recipe {}))

(defn make-recipe [m]
  {:pre [(map? m)]}
  (merge default-recipe (select-keys m (keys default-recipe))))

(defn find-recipe* [& [where-clause]]
  (-> (s/select :r.*)
      (s/from [:recipe :r])
      (s/where (or where-clause '(= 1 1)))))

(defn find-recipe [& [where-clause]]
  (-> (find-recipe* where-clause)
      db/fetch))

(defn find-first-recipe [& [where-clause]]
  (-> (find-recipe* where-clause)
      (s/limit 1)
      db/fetch
      first))

(defn save-recipe [m]
  (let [m (-> m
              update-datetime
              (dissoc :id))]
    (-> (s/insert-into :recipe)
        (s/values [m])
        db/execute
        first)))
