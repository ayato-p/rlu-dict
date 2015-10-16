(ns rlu-dict.view.layout
  (:require [net.cgrand.enlive-html :as html]))

(def service-name "逆引き Clojure")

(defn menu [req]
  (-> [:a {:href "/login"} "ログイン"]
      html/html))

(html/deftemplate main-layout
  "template/layout.html"
  [req & {:keys [content]}]
  [:title] (html/content service-name)
  [:#content] (html/content content)
  [:#menu] (html/content (menu req)))
