(ns advent23.day06
  (:require [clojure.string :as str]))

(defn process [f [times distances]]
  (let [record-beating (fn [[t d]] (count (filter #(< d %) (map #(* % (- t %)) (range 1 t)))))]
    (reduce * (map record-beating (map vector (rest (f times)) (rest (f distances)))))))

(defn part1 [input]
  (process #(into [] (map parse-long (str/split % #"\s+"))) input))

(defn part2 [input]
  (process #(into [] (map parse-long (str/split (str/replace % #"\s+" "") #":"))) input))
