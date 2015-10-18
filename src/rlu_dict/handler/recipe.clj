(ns rlu-dict.handler.recipe
  (:require [compojure.core :refer [defroutes context GET POST]]
            [rlu-dict.core.recipe :as core]
            [rlu-dict.entity.recipe :as recipe]
            [rlu-dict.security.auth :as auth]
            [rlu-dict.util.response :as res]
            [rlu-dict.view.recipe :as view]))

(defn index [{:as req :keys [params]}]
  (let [max-page (core/find-recipe-max-page)
        current-page (or (Long/parseLong (:page params)) 1)
        recipes (core/find-recipe-by-page current-page)]
    (-> (view/index req recipes max-page current-page)
        res/ok
        res/html)))

(defn show [{:as req :keys [params]}]
  (let [id (Long/parseLong (:id params) 10)
        recipe (recipe/find-first-recipe `(= id ~id))]
    (-> (view/show req recipe)
        res/ok
        res/html)))

(defn new [req]
  (-> (view/new req)
      res/ok
      res/html))

(defn new-post [{:as req :keys [params]}]
  (let [m (auth/current-member req)
        recipe (-> params
                   (assoc :member-id (:id m))
                   recipe/make-recipe)]
    (when-let [{:keys [id]} (recipe/save-recipe recipe)] ;; TODO error handling
      (-> (res/found (str "/recipe/" id))
          res/html))))

(defroutes recipe-routes
  (context "/recipe" []
           (GET "/" [req] index)
           (GET "/new" [req] new)
           (POST "/new" [req] new-post)
           (GET ["/:id" :id #"[0-9]+"] [req] show)))
