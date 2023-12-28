(ns advent23.day15
  (:require [clojure.string :as str]))

(defn part1 [input]
  (let [hash (fn [acc char] (rem (* (+ acc (int char)) 17) 256))]
    (str (reduce + (map (fn [v] (reduce hash 0 v)) (str/split (first input) #","))))))
