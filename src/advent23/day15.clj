(ns advent23.day15
  (:require [clojure.string :as str]))

(use 'flatland.ordered.map)

(defn hash-value [value]
  (let [hash-char (fn [acc char] (rem (* (+ acc (int char)) 17) 256))]
    (reduce hash-char 0 value)))

(defn part1 [input] (str (reduce + (map hash-value (str/split (first input) #",")))))

(defn process [boxes instruction]
  (let [[label focal-length?] (str/split instruction #"[=-]") index (hash-value label) current (get boxes index)]
    (assoc boxes index
                 (if (empty? focal-length?) (dissoc current label) (assoc current label (parse-long focal-length?))))))

(defn part2 [input]
  (str (reduce + (apply concat
                        (map-indexed
                          (fn [i x] (map-indexed (fn [j y] (* (inc i) (inc j) (second y))) x))
                          (reduce process (vec (repeat 256 (ordered-map []))) (str/split (first input) #",")))))))
