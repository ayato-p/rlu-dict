(ns rlu-dict.handler.main
  (:require [clj-http.client :as cli]
            [compojure.core :refer [GET defroutes]]
            [rlu-dict.entity.member :as m]
            [rlu-dict.github-cli :as gc]
            [rlu-dict.util.response :as res]
            [rlu-dict.view.main :as view]))

(defn home [req]
  (-> (view/home req)
      res/ok
      res/html))

(defn logout [req]
  (let [session (:session req)
        dissoced (dissoc session :identity)]
    (-> (res/found "/")
        (assoc :session dissoced)
        res/html)))

(defn auth-callback [req]
  (let [member (-> req
                   gc/access-token
                   gc/me
                   m/find-or-save-member)
        session (:session req)
        updated (assoc session :identity member)]
    (-> (res/found "/")
        (assoc :session updated)
        res/html)))

(defroutes main-routes
  (GET "/" [req] home)
  (GET "/logout" [req] logout)
  (GET "/auth-callback" [req] auth-callback))
