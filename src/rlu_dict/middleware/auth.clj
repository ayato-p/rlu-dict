(ns rlu-dict.middleware.auth
  (:require [buddy.auth.accessrules :refer [wrap-access-rules]]
            [buddy.auth.backends.session :refer [session-backend]]
            [buddy.auth.middleware :as buddy-mw]
            [rlu-dict.util.response :as res]))

(def backend (session-backend))

(def rules [])

(defn wrap-auth [handler]
  (-> handler
      (wrap-access-rules {:rules rules
                          :on-error res/not-found!})
      (buddy-mw/wrap-authorization backend)
      (buddy-mw/wrap-authentication backend)))
