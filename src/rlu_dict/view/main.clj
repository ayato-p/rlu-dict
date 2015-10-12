(ns rlu-dict.view.main
  (:require [net.cgrand.enlive-html :as html]
            [rlu-dict.view.layout :as layout]))

(defn home [req]
  (->> (-> [:h1 "Welcome!!"]
           html/html)
       (layout/main-layout req :content)))
