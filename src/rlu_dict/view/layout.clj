(ns rlu-dict.view.layout
  (:require [net.cgrand.enlive-html :as html]
            [rlu-dict.github-cli :as gc]
            [rlu-dict.security.auth :refer [current-member]]))

(def service-name "逆引き Clojure")

(defn gen-title [title]
  (if title
    (str title " | " service-name)
    service-name))

(def menu-spec)

(defn menu [req]
  (-> (if-let [member (current-member req)]
        (list
         [:li [:a.no-deco {:href "/recipe/new" :title "新しく逆引きレシピを書く"}
               [:i.icon.ion-plus-round]]]
         [:li [:a.no-deco {:href "/logout" :title "ログアウトする"}
               [:img.avatar {:src (str (:icon-img member))} ] "ログアウト"]])
        [:li [:a.no-deco {:href (gc/generate-auth-url req) :title "ログインする"} "ログイン"]])
      html/html))

(html/deftemplate main-layout
  "template/layout.html"
  [req & {:keys [title content]}]
  [:title] (html/content (gen-title title))
  [:#content] (html/content content)
  [:#menu] (html/content (menu req)))
