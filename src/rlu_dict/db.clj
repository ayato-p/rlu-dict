(ns rlu-dict.db
  (:require [clojure.string :as str]
            [jdbc.core :as jdbc]
            [inflections.core :as inf]
            [stch.sql.format :as sf]))

(def ^:dynamic *connection-source*)
(def ^:dynamic *connection*)

(defmacro with-connection-source [connection-source & body]
  `(binding [*connection-source* ~connection-source]
     ~@body))

(defn connection []
  (jdbc/connection *connection-source*))

(defmacro with-connection [& body]
  `(with-open [conn# (connection)]
     (binding [*connection* conn#]
       ~@body)))

(defmacro with-connection-dwim [& body]
  `(if (bound? #'*connection*)
     (do ~@body)
     (with-connection ~@body)))

(defn execute
  ([q]
   (execute q {:returning :all}))
  ([q opt]
   (let [qs (if (string? q) q (sf/format q {}))]
     (with-connection-dwim
       (jdbc/execute *connection* qs opt)))))

(defn fetch
  ([q]
   (fetch q {:identifiers (comp str/lower-case inf/dasherize)}))
  ([q opt]
   (let [qs (if (string? q) q (sf/format q {:quoting :postgresql}))]
     (with-connection-dwim
       (jdbc/fetch *connection* qs opt)))))
