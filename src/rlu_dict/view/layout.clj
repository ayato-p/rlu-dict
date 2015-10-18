(ns rlu-dict.view.layout
  (:require [net.cgrand.enlive-html :as html]
            [rlu-dict.github-cli :as gc]
            [rlu-dict.security.auth :refer [current-member]]))

(def service-name "逆引き Clojure")

(def menu-spec)

(defn menu [req]
  (-> (if-let [member (current-member req)]
        (list
         [:li [:a {:href "/recipe/new" :title "新しく逆引きレシピを書く"} [:i.icon.ion-plus-round]]]
         [:li [:a {:href "/logout" :title "ログアウトする"} [:img.avatar {:src (str (:icon-img member))} ] "ログアウト"]])
        [:li [:a {:href (gc/generate-auth-url req) :title "ログインする"} "ログイン"]])
      html/html))

(html/deftemplate main-layout
  "template/layout.html"
  [req & {:keys [content]}]
  [:title] (html/content service-name)
  [:#content] (html/content content)
  [:#menu] (html/content (menu req)))
