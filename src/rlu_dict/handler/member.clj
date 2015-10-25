(ns rlu-dict.handler.member
  (:require [compojure.core :refer [GET defroutes]]))

(defn show-member [req]
  (get-in req [:params :login-name]))

(defroutes member-routes
  (GET "/u/:login-name" [req] show-member))
