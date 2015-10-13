(ns rlu-dict.util.view
  (:require [markdown.core :as md]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]))

(defn anti-forgery-field []
  [:input {:type "hidden" :name "__anti-forgery-token" :value *anti-forgery-token*}])

(defn parse-markdown [text]
  (let [in (java.io.StringReader. text)
        out (java.io.ByteArrayOutputStream.)]
    (md/md-to-html in out)
    (net.cgrand.tagsoup/parser (java.io.ByteArrayInputStream. (.toByteArray out)))))
