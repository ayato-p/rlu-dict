(ns rlu-dict.view.main
  (:require [net.cgrand.enlive-html :as html]
            [rlu-dict.view.layout :as layout]))

(defn home [req]
  (->> (list
        [:h1 "Welcome!!"]
        [:ul
         [:li [:a {:href "/recipe"} "逆引きレシピ一覧"]]])
       html/html
       (layout/main-layout req :content)))
