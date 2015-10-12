(ns rlu-dict.util.namespace
  (:require [potemkin.namespaces :as p]))

(defmacro import-ns [ns]
  (require ns)
  `(do
     (require (quote ~ns))
     (p/import-vars ~(into [ns] (keys (ns-publics ns))))))
