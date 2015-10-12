(ns rlu-dict.util.response
  (:require [rlu-dict.util.namespace :refer [import-ns]]
            [ring.util.http-response]))

(import-ns ring.util.http-response)

(defn html [res]
  (assoc-in res [:headers "Content-Type"] "text/html"))
