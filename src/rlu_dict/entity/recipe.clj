(ns rlu-dict.entity.recipe
  (:require [rlu-dict.db :as db]
            [schema.core :as s]
            [stch.sql :as sql]))

(s/defrecord Recipe
    [id         :- s/Int
     title      :- s/Str
     content    :- s/Str
     member-id  :- s/Int
     request-id :- s/Int
     created-at :- s/Inst
     updated-at :- s/Inst])
