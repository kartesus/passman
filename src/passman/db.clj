(ns passman.db
  (:require [babashka.pods :as pods]
            [honey.sql :as sql]
            [honey.sql.helpers :as h]
            [babashka.fs :as fs]))

(pods/load-pod 'org.babashka/go-sqlite3 "0.1.0")
(require '[pod.babashka.go-sqlite3 :as sqlite])

(def DB "passman.db")

(defn create-db
  []
  (when-not (fs/exists? DB)
    (sqlite/execute! DB
                     (-> (h/create-table :passwords)
                         (h/with-columns [[:url :text]
                                          [:username :text]
                                          [:password :text]
                                          [[:unique nil :url :username]]])
                         (sql/format)))))

(defn insert-password
  [url username password]
  (sqlite/execute! DB
                   (-> (h/insert-into :passwords)
                       (h/columns :url :username :password)
                       (h/values [[url username password]])
                       (sql/format))))

(defn list-passwords
  []
  (sqlite/query DB
                (-> (h/select :url :username :password)
                    (h/from :passwords)
                    (sql/format))))

(comment
  (create-db)
  (insert-password "https://github.com" "kartesus" "123456")
  (list-passwords))