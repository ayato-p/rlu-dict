(ns rlu-dict.view.main
  (:require [markdown.core :as md]
            [net.cgrand.enlive-html :as html]
            [rlu-dict.util.view :as uv]
            [rlu-dict.view.layout :as layout]))

(defn home [req]
  (->> (uv/parse-markdown "```clojure\n(defn hello [] \"Hello,world\")\n```")
       (layout/main-layout req :content))
  #_(->> (list
          [:h1 "Welcome!!"]
          [:ul
           [:li [:a {:href "/recipe"} "逆引きレシピ一覧"]]])
         html/html
         (layout/main-layout req :content)))
