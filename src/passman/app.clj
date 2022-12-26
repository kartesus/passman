(ns passman.app
  (:require [clojure.tools.cli :refer [parse-opts]]
            [passman.db :refer [list-passwords insert-password]]
            [passman.password :refer [generate-password]]
            [table.core :as t]))

(def cli-options
  [["-l" "--length LENGTH" "Length of the password" :default 20 :parse-fn #(Integer/parseInt %)]
   ["-g" "--generate" "Generate a password"]
   [nil "--list" "List all passwords"]
   ["-h" "--help" "Show help"]])

(defn -main
  [& args]
  (let [parsed-opts (parse-opts args cli-options)
        opts        (:options parsed-opts)
        url         (first (:arguments parsed-opts))
        username    (second (:arguments parsed-opts))]
    (cond
      (:list opts)     (t/table (list-passwords))
      (:generate opts) (let [password (generate-password (:length opts))]
                         (when (and url username)
                           (insert-password url username password)
                           (println "Saving password to database...")))
      :else            parsed-opts)))

