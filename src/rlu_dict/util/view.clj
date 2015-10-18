(ns rlu-dict.util.view
  (:require [clj-http.client :refer [generate-query-string]]
            [clojure.string :as str]
            [markdown.core :as md]
            [ring.middleware.anti-forgery :as anti-forgery]))

(defn anti-forgery-field []
  [:input {:type "hidden" :name "__anti-forgery-token" :value anti-forgery/*anti-forgery-token*}])

(defn parse-markdown [text]
  (let [in (java.io.StringReader. text)
        out (java.io.ByteArrayOutputStream.)]
    (md/md-to-html in out)
    (net.cgrand.tagsoup/parser (java.io.ByteArrayInputStream. (.toByteArray out)))))

(defn pager [url max current]
  (let [f #(str/join "?" [url (generate-query-string {:page %})])
        pages (take 5 (drop-while (some-fn neg? zero?)
                                  (iterate inc (- current 2))))]
    [:ol.pager
     [:li [:a {:href (f 1)} "«"]]
     (for [n pages
           :when (<= n max)]
       [:li (when (= current n) {:class "active"})
        [:a {:href (f n)} n]])
     [:li [:a {:href (f max)} "»"]]]))
