(ns advent23.day09
  (:require [clojure.string :as str]))

(defn parse-to-numbers [line] (into [] (map parse-long (str/split line #" "))))

(defn diff [list]
  (loop [[a & rest] list result []]
    (if (nil? rest) result (recur rest (conj result (- (first rest) a))))))

(defn process-1 [numbers]
  (loop [current numbers result 0]
    (if (every? #(= % 0) current) result (recur (diff current) (+ result (peek current))))))

(defn process-2 [numbers]
  (loop [current (rseq numbers) result 0]
    (if (every? #(= % 0) current) result (recur (diff current) (+ result (last current))))))

(defn part1 [input] (reduce + (map process-1 (map parse-to-numbers input))))

(defn part2 [input] (reduce + (map process-2 (map parse-to-numbers input))))
