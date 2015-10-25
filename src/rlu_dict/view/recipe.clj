(ns rlu-dict.view.recipe
  (:require [net.cgrand.enlive-html :as html]
            [rlu-dict.util.time :as ut]
            [rlu-dict.util.view :as vu]
            [rlu-dict.view.layout :as layout]))

(defn article [{:as recipe :keys [id title icon-img member-name updated-at]}]
  [:article.recipe-table-row
   [:div.table-cell-avatar
    [:img.avatar {:src icon-img}]]
   [:div.table-cell
    [:span.status.small (str (ut/humanize updated-at) " に " member-name " が更新しました")]
    [:span.body [:a {:href (str "/recipe/" id)} title]]]])

(defn index [req recipes max-page current-page]
  (->> [:section.panel
        [:header.bg-light-green "逆引きレシピ一覧"]
        [:main
         [:div.recipe-table
          (for [r recipes] (article r))]
         (vu/pager "/recipe" max-page current-page)]]
       html/html
       (layout/main-layout req :content)))

(defn show [req {:as recipe :keys [title content]}]
  (let [parsed (vu/parse-markdown content)]
    (->> (-> [:section.panel
              [:header.bg-light-green title]
              [:main#recipe-content.word-wrap]]
             html/html
             (html/transform [:main#recipe-content] (html/content parsed)))
         (layout/main-layout req :title title :content))))

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
