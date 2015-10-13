(ns rlu-dict.handler.recipe
  (:require [compojure.core :refer [defroutes context GET POST]]
            [rlu-dict.util.response :as res]
            [rlu-dict.view.recipe :as view]))

(defn index [req]
  (-> (view/index req [])
      res/ok
      res/html))

(defn show [req]
  (-> (view/show req)
      res/ok
      res/html))

(defn new [req]
  (-> (view/new req)
      res/ok
      res/html))

(defn new-post [req]
  (prn (:params req))
  (-> (res/found "/recipe")
      res/html))

(defroutes recipe-routes
  (context "/recipe" []
           (GET "/" [req] index)
           (GET "/new" [req] new)
           (POST "/new" [req] new-post)
           (GET "/:id" [req] show)))
