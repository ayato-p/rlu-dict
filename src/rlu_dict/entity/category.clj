(ns rlu-dict.entity.category
  (:require [rlu-dict.db :as db]
            [rlu-dict.entity.common :refer [update-datetime]]
            [schema.core :as sc]
            [stch.sql :as s]))

(sc/defrecord Category
    [id         :- (sc/maybe sc/Int)
     name       :- sc/Str
     created-at :- sc/Inst
     updated-at :- sc/Inst])

(def default-category (map->Category {}))

(defn make-category [m]
  {:pre [(map? m)]}
  (merge default-category (select-keys m (keys default-category))))

(defn save-category [m]
  (let [m (-> m
              update-datetime
              (dissoc :id))]
    (-> (s/insert-into :category)
        (s/values [m])
        (db/execute)
        first)))
