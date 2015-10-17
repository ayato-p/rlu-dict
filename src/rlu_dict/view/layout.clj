(ns rlu-dict.view.layout
  (:require [net.cgrand.enlive-html :as html]
            [rlu-dict.github-cli :as gc]
            [rlu-dict.security.auth :refer [current-member]]))

(def service-name "逆引き Clojure")

(defn menu [req]
  (-> (if-let [member (current-member req)]
        [:a {:href "/logout"} "ログアウト"]
        [:a {:href (gc/generate-auth-url req)} "ログイン"])
      html/html))

(html/deftemplate main-layout
  "template/layout.html"
  [req & {:keys [content]}]
  [:title] (html/content service-name)
  [:#content] (html/content content)
  [:#menu] (html/content (menu req)))
