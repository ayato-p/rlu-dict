(ns rlu-dict.repl
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.pprint :refer (pprint)]
            [clojure.repl :refer :all]
            [clojure.string :as str]
            [clojure.test :as test]
            [clojure.tools.namespace.repl :refer (refresh refresh-all)]
            [com.stuartsierra.component :as c]
            [rlu-dict.db :as db]
            [rlu-dict.system :as system]))

(defn- config-reader []
  (edn/read-string (slurp "dev-resources/config-local.edn")))

(def system nil)

(defn go
  []
  (system/boot #'system (config-reader))
  (alter-var-root #'db/*connection-source* (constantly (:db system))))

(defn stop
  []
  (system/shutdown #'system)
  (alter-var-root #'db/*connection-source* (constantly nil)))

(defn reset []
  (stop)
  (refresh :after 'rlu-dict.repl/go))

(defn drop-all-table! []
  (let [sql "select table_name from information_schema.tables where table_schema = 'public'"
        tables (mapcat vals (db/fetch sql))]
    (doseq [t tables]
      (db/execute (str "drop table " t) {}))))

(defn- rnd-str
  ([] (rnd-str 8))
  ([limit]
   (let [num+alpha (concat (range (int \0) (inc (int \9)))
                           (range (int \a) (inc (int \z)))
                           (range (int \A) (inc (int \Z))))]
     (->> (repeatedly limit #(rand-nth num+alpha))
          (map char)
          (clojure.string/join)))))

(comment
  (let [recipes (repeatedly 10000 #(rlu-dict.entity.recipe/make-recipe {:title (rnd-str 20) :content (rnd-str 500) :member-id 1}))]
    (doseq [r recipes]
      (rlu-dict.entity.recipe/save-recipe r))))
