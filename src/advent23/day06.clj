(ns advent23.day06
  (:require [clojure.string :as str]))

(defn count-better-timings [[time distance]]
  (count (filter #(< distance %) (map #(* % (- time %)) (range 1 time)))))

(defn time-and-distances [[times distances]]
  (let [f (fn [s] (into [] (map parse-long (str/split s #"\s+"))))]
    (map vector (rest (f times)) (rest (f distances)))))

(defn part1 [input]
  (reduce * (map count-better-timings (time-and-distances input))))