(ns rlu-dict.view.main
  (:require [clojure.string :as str]
            [markdown.core :as md]
            [net.cgrand.enlive-html :as html]
            [rlu-dict.github-cli :as gc]
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

(defn login [req]
  (->> (list
        [:span "GitHub へとリダイレクトしてます…"]
        [:meta {:http-equiv "refresh"
                :content (str/join ";"["0" (str "URL=" (gc/generate-auth-url req))])}])
       html/html
       (layout/main-layout req :content)))
