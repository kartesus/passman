(ns passman.app
  (:require [clojure.tools.cli :refer [parse-opts]]))

(def cli-options
  [[nil "--list"]])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [opts (:options (parse-opts args cli-options))]
    (cond
      (:list opts) (println "list")
      :else (println "help"))))
