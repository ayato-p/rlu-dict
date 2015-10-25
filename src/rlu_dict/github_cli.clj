(ns rlu-dict.github-cli
  (:require [cheshire.core :as c]
            [clj-http.client :as cli]
            [clojure.set :as set]
            [clojure.string :as str]
            [rlu-dict.entity.member :as m]))

(def ^:private github-api "https://api.github.com")

(def ^:private authorize-url "https://github.com/login/oauth/authorize")

(def ^:private access-token-url "https://github.com/login/oauth/access_token")

(defn- github-user->member [github-user]
  (-> github-user
      (set/rename-keys {:id :github-id, :login :login-name, :avatar_url :icon-img})
      m/create-member))

(defn generate-auth-url [req]
  (let [client-id (get-in req [:github :client_id])
        query (cli/generate-query-string {:client_id client-id})]
    (str/join "?" [authorize-url query])))

(defn- parse-token-body [res]
  (if-let [body (:body res)]
    (->> (str/split body #"&")
         (map #(str/split % #"="))
         (remove #(< (count %) 2))
         (into {})
         clojure.walk/keywordize-keys)))

(defn access-token [req]
  (if-let [oauth-token (get-in req [:github :oauth-token])]
    oauth-token
    (let [code (get-in req [:params :code])
          params (merge (:github req) {:code code})]
      (-> (cli/post access-token-url {:form-params params})
          parse-token-body
          :access_token))))

(defn me [access-token]
  (let [res (cli/get (str github-api "/user")
                     {:headers {"Authorization" (str "token " access-token)}})]
    (-> res
        :body
        (c/parse-string true)
        github-user->member)))

(defn avatar-24
  "This function dont control any url format error now."
  [url]
  (str url "&s=24"))

(defn avatar-32 [url]
  (str url "&s=32"))

(defn avatar-48 [url]
  (str url "&s=48"))
