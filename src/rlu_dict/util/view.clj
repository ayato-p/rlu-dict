(ns rlu-dict.util.view
  (:require [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]))

(defn anti-forgery-field []
  [:input {:type "hidden" :name "__anti-forgery-token" :value *anti-forgery-token*}])
