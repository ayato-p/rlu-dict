(ns rlu-dict.middleware.auth
  (:require [buddy.auth.accessrules :refer [wrap-access-rules]]
            [buddy.auth.middleware :as buddy-mw]
            [rlu-dict.security.auth :as auth]
            [rlu-dict.util.response :as res]))

(def rules [])

(defn wrap-auth [handler]
  (-> handler
      (wrap-access-rules {:rules rules
                          :on-error res/not-found!})
      (buddy-mw/wrap-authorization auth/backend)
      (buddy-mw/wrap-authentication auth/backend)))
