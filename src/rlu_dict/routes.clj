(ns rlu-dict.routes
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [rlu-dict.handler.main :as main]
            [rlu-dict.handler.member :as member]
            [rlu-dict.handler.recipe :as recipe]))

(defroutes app
  main/main-routes
  recipe/recipe-routes
  member/member-routes
  (route/not-found "Not found")) ;; TODO
