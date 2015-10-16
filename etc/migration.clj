(ns migration
  (:require [clj-liquibase.change :as ch]
            [clj-liquibase.cli :as cli]
            [clj-liquibase.core :refer [defchangelog]]
            [clojure.edn :as edn]
            [jdbc.core :as jdbc]
            [rlu-dict.util.db :as ud]))

(defn make-datasource []
  (-> "dev-resources/config-local.edn"
      slurp
      edn/read-string
      :db
      ud/make-simple-datasource))

(def create-table-member
  (ch/create-table
   :member
   [[:id         :int           :null false :pk true :autoinc true]
    [:name       [:varchar 255] :null false]
    [:github-id  :bigint        :null false]
    [:icon-img   :text          :null true]
    [:created-at :datetime      :null false]
    [:updated-at :datetime      :null false]]))

(def create-table-recipe
  (ch/create-table
   :recipe
   [[:id         :int           :null false :pk true :autoinc true]
    [:title      [:varchar 255] :null false]
    [:content    :text          :null false]
    [:member-id  :int           :null false]
    [:request-id :int           :null true]
    [:created-at :datetime      :null false]
    [:updated-at :datetime      :null false]]))

(def create-table-request
  (ch/create-table
   :request
   [[:id         :int           :null false :pk true :autoinc true]
    [:title      [:varchar 255] :null false]
    [:content    :text          :null false]
    [:member-id  :int           :null false]
    [:solved-at  :datetime      :null true]
    [:created-at :datetime      :null false]
    [:updated-at :datetime      :null false]]))

(def create-table-category
  (ch/create-table
   :category
   [[:id         :int           :null false :pk true :autoinc true]
    [:name       [:varchar 255] :null false]
    [:created-at :datetime      :null false]
    [:updated-at :datetime      :null false]]))

(def create-table-recipe-category
  (ch/create-table
   :recipe-category
   [[:id          :int      :null false :pk true :autoinc true]
    [:recipe-id   :int      :null false]
    [:category-id :int      :null false]
    [:created-at  :datetime :null false]
    [:updated-at  :datetime :null false]]))

(def create-index-member
  (ch/create-index :member [:id]))

(def create-index-github-id
  (ch/create-index :member [:github-id]))

(def create-index-recipe
  (ch/create-index :recipe [:id]))

(def create-index-request
  (ch/create-index :request [:id]))

(def create-index-category
  (ch/create-index :category [:id]))

(def create-index-recipe-category
  (ch/create-index :recipe-category [:recipe-id :category-id] :unique true))

(def changeset-1 ["id=1" "author=ayato_p" [create-table-member
                                           create-table-recipe
                                           create-table-request
                                           create-table-category
                                           create-table-recipe-category
                                           create-index-member
                                           create-index-github-id
                                           create-index-recipe
                                           create-index-request
                                           create-index-category
                                           create-index-recipe-category]])

(defchangelog app-changelog "rlu-dict" [changeset-1])

(cli/entry "update" {:datasource (make-datasource) :changelog app-changelog})
