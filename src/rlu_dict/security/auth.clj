(ns rlu-dict.security.auth
  (:require [buddy.auth.backends.session :refer [session-backend]]
            [buddy.auth.protocols :as proto]))

(def backend (session-backend))

(defn current-member [req]
  (proto/-parse backend req))
