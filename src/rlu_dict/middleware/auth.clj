(ns rlu-dict.middleware.auth
  (:require [buddy.auth.accessrules :refer [error wrap-access-rules]]
            [buddy.auth.middleware :as buddy-mw]
            [rlu-dict.security.auth :as auth]
            [rlu-dict.util.response :as res]))

(def rules
  [{:pattern #"^/recipe/(?:new|edit)"
    :handler (fn [req]
               (let [member (auth/current-member req)]
                 (some? member)))}])

(defn on-error [req val]
  (-> (res/unauthorized "You need to login with GitHub acccount.") ;; TODO
      res/html))

(defn wrap-auth [handler]
  (-> handler
      (wrap-access-rules {:rules rules
                          :on-error on-error})
      (buddy-mw/wrap-authorization auth/backend)
      (buddy-mw/wrap-authentication auth/backend)))
