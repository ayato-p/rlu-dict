(ns rlu-dict.github-cli
  (:require [cheshire.core :as c]
            [clj-http.client :as cli]
            [clojure.set :as set]
            [clojure.string :as str]
            [rlu-dict.entity.member :as m]))

(def ^:private github-api "https://api.github.com")

(def ^:private authorize-url "https://github.com/login/oauth/authorize")

(def ^:private access-token-url "https://github.com/login/oauth/access_token")

{:html_url "https://github.com/ayato-p", :gravatar_id "", :followers_url "https://api.github.com/users/ayato-p/followers", :subscriptions_url "https://api.github.com/users/ayato-p/subscriptions", :site_admin false, :email "lumia[at]nandeger.com", :following_url "https://api.github.com/users/ayato-p/following{/other_user}", :hireable nil, :name "ayato_p", :type "User", :received_events_url "https://api.github.com/users/ayato-p/received_events", :login "ayato-p", :following 10, :updated_at "2015-10-16T12:48:32Z", :bio nil, :organizations_url "https://api.github.com/users/ayato-p/orgs", :id 1432156, :events_url "https://api.github.com/users/ayato-p/events{/privacy}", :url "https://api.github.com/users/ayato-p", :public_gists 61, :repos_url "https://api.github.com/users/ayato-p/repos", :public_repos 40, :starred_url "https://api.github.com/users/ayato-p/starred{/owner}{/repo}", :location "Tokyo, Japan", :blog "http://ayalog.com/", :followers 20, :company "Cybozu Startups, Inc.", :gists_url "https://api.github.com/users/ayato-p/gists{/gist_id}", :created_at "2012-02-13T02:59:10Z", :avatar_url "https://avatars.githubusercontent.com/u/1432156?v=3"}

(defn- github-user->member [github-user]
  (-> github-user
      (set/rename-keys {:id :github-id :avatar_url :icon-img})
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
