(ns advent23.day05
  (:require [clojure.string :as str]))

(defn in-range-or-nil [n r]
  (let [{dest :dest src :src range :range} r] (if (<= src n (+ src range -1)) (+ dest (- n src)))))

(defn find-next [n map-of-ranges]
  (or (first (filter number? (map #(in-range-or-nil n %) (:ranges map-of-ranges)))) n))

(defn to-ranges [numbers]
  (zipmap [:dest :src :range] (map parse-long (str/split numbers #" "))))

(defn to-map [m]
  (zipmap [:map :ranges] (conj [(first m)] (reverse (sort-by :src (map to-ranges (drop 1 m)))))))

(defn process [input]
  (let [[seeds & list-of-maps] (filter #(not (= '("") %)) (partition-by #(= "" %) input))]
    (zipmap [:seed-numbers :maps]
            [(map parse-long (drop 1 (str/split (first seeds) #" ")))
             (map to-map list-of-maps)])))

(defn part1 [input]
  (let [{seed-numbers :seed-numbers maps :maps} (process input)]
    (reduce min (map #(reduce find-next % maps) seed-numbers))))
