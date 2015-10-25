(ns rlu-dict.view.main
  (:require [clojure.string :as str]
            [markdown.core :as md]
            [net.cgrand.enlive-html :as html]
            [rlu-dict.core.container :as core]
            [rlu-dict.github-cli :as gc]
            [rlu-dict.util.view :as uv]
            [rlu-dict.view.layout :as layout]
            [rlu-dict.view.recipe :refer [article]]))

(defn top-contributors []
  (let [contributors (core/top-contributors 30)]
    [:section.panel
     [:header.bg-light-green "最も投稿しているユーザー"]
     [:main.top-contributors
      (for [{:keys [login-name icon-img]} (repeat 80 (first contributors))]
        [:a {:href (str "/u/" login-name)}
         [:img.avatar {:src (gc/avatar-32 icon-img)}]])]]))

(defn resently-updated []
  (let [[f-recipes s-resipes] (partition 4  (core/resently-updated-recipes 8))]
    [:section.panel
     [:header.bg-light-green "最近更新されたレシピ"]
     [:main.recently-updated
      [:div.row
       [:div.col-md-6
        (for [r f-recipes] (article r))]
       [:div.col-md-6
        (for [r s-resipes] (article r))]]]]))

(defn how-to-write []
  [:section.panel
   [:header.bg-light-green "書き方"]
   [:main#markdown-ex
    "foo"]])

(defn home [req]
  (->> (-> (list
            (top-contributors)
            (resently-updated)
            (how-to-write))
           html/html
           (html/transform [:main#markdown-ex] (html/content (uv/parse-markdown "```clojure\n(defn hello [] \"逆引き Clojure\")\n```"))))
       (layout/main-layout req :content))
  #_(->> (list
          [:h1 "Welcome!!"]
          [:ul
           [:li [:a {:href "/recipe"} "逆引きレシピ一覧"]]])
         html/html
         (layout/main-layout req :content)))
