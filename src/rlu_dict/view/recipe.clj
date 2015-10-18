(ns rlu-dict.view.recipe
  (:require [net.cgrand.enlive-html :as html]
            [rlu-dict.util.view :as vu]
            [rlu-dict.view.layout :as layout]))

(defn index [req recipes]
  (->> [:section.panel
        [:header.bg-light-green "逆引きレシピ一覧"]
        [:main]]
       html/html
       (layout/main-layout req :content)))

(defn show [req {:as recipe :keys [title content]}]
  (let [parsed (vu/parse-markdown content)]
    (->> (-> [:section.panel
              [:header.bg-light-green title]
              [:main#recipe-content]]
             html/html
             (html/transform [:main#recipe-content] (html/content parsed)))
         (layout/main-layout req :content))))

(defn recipe-form []
  [:form {:action "/recipe/new" :method "post"}
   (vu/anti-forgery-field)
   [:input.full-width
    {:name "title" :placeholder "タイトル"}]
   [:input.full-width
    {:name "category" :placeholder "カテゴリ"}]
   [:textarea.full-width.content
    {:name "content" :placeholder "本文"}]
   [:button.s.bg-blue "公開"]])

(defn new [req]
  (->> [:section.panel
        [:header.bg-light-green "新しい逆引きレシピ"]
        [:main (recipe-form)]]
       html/html
       (layout/main-layout req :content)))
