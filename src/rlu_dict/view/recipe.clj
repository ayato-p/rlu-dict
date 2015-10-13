(ns rlu-dict.view.recipe
  (:require [net.cgrand.enlive-html :as html]
            [rlu-dict.util.view :as vu]
            [rlu-dict.view.layout :as layout]))

(defn index [req recipes]
  (->> [:h1 "逆引きレシピ一覧"]
       html/html
       (layout/main-layout req :content)))

(defn show [req]
  (->> [:h1 "recipe show"]
       html/html
       (layout/main-layout req :content)))

(def recipe-form
  [:form {:action "/recipe/new" :method "post"}
   (vu/anti-forgery-field)
   [:input.full-width {:name "title"}]
   [:textarea.full-width {:name "content"}]
   [:button.bg-blue "Submit"]])

(defn new [req]
  (->> [:section.panel
        [:header.bg-light-blue "Recipe new"]
        [:main recipe-form]]
       html/html
       (layout/main-layout req :content)))
