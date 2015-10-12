(ns rlu-dict.routes
  (:require [compojure.core :refer [defroutes GET]]
            [rlu-dict.handler.main :as main]))

(defroutes app
  main/main-routes)
