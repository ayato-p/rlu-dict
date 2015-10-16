(ns rlu-dict.component.webserver
  (:require [com.stuartsierra.component :as c]
            [immutant.web :as web]
            [rlu-dict.middleware :as m]
            [rlu-dict.routes :as r]
            [taoensso.timbre :as log]))

(def default-server-config {:port 3000})

(defn default-handler [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello, world"})

(defrecord WebServer [conf handler db github]
  c/Lifecycle
  (start [c]
    (let [conf (merge default-server-config conf)
          m-conf (-> conf
                     (assoc :connection-source db)
                     (assoc :github (:conf github)))
          handler (m/create-from-conf #'r/app m-conf)]
      (log/info "Start WebServer:" (:port conf))
      (assoc c :server (web/run handler conf))))

  (stop [c]
    (log/info "Stop WebServer")
    (if (:server c)
      (do (web/stop (:server c))
          (assoc c :server nil))
      c)))

(defn new-webserver [conf]
  (map->WebServer {:handler default-handler
                   :conf (:webserver conf)}))
