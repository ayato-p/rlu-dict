(ns rlu-dict.handler.main
  (:require [rlu-dict.util.response :as res]
            [compojure.core :refer [GET defroutes]]
            [rlu-dict.view.main :as view]))

(defn home [req]
  (-> (view/home req)
      res/ok
      res/html))

(defroutes main-routes
  (GET "/" [req] home))
