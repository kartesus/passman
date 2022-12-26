(ns passman.app
  (:require [clojure.tools.cli :refer [parse-opts]]
            [passman.db :refer [list-passwords]]))

(def cli-options
  [[nil "--list"]])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [opts (:options (parse-opts args cli-options))]
    (cond
      (:list opts) (list-passwords)
      :else (println "help"))))

