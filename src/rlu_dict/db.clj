(ns rlu-dict.db
  (:require [jdbc.core :as jdbc]))

(def ^:dynamic *connection-source*)
(def ^:dynamic *connection*)

(defmacro with-connection-source [connection-source & body]
  `(binding [*connection-source* ~connection-source]
     ~@body))

(defn connection []
  (jdbc/connection *connection-source*))

(defmacro with-connection [& body]
  `(with-open [conn# (connection)]
     (binding [*connection conn#]
       ~@body)))

(defmacro with-connection-dwim [& body]
  `(if (bound? #'*connection*)
     (do ~@body)
     (with-connection ~@body)))
