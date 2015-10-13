(ns rlu-dict.routes
  (:require [compojure.core :refer [defroutes GET]]
            [rlu-dict.handler.main :as main]
            [rlu-dict.handler.recipe :as recipe]))

(defroutes app
  main/main-routes
  recipe/recipe-routes)
