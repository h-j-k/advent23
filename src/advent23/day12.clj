(ns advent23.day12
  (:require [clojure.string :as str]))

(defn count-damaged [condition]
  (let [m (re-matcher #"(#+)" condition)]
    (loop [r []] (if (not (. m find)) (apply str (interpose "," r)) (recur (conj r (count (. m group))))))))

(defn expand-state [condition index]
  (let [prefix (subs condition 0 index) suffix (subs condition (inc index))]
    [(str prefix "." suffix) (str prefix "#" suffix)]))

(defn expand [condition]
  (loop [r [] to-process [condition]]
    (if (empty? to-process)
      r
      (let [next (peek to-process) m (re-matcher #"(\?)" next) rest (pop to-process)]
        (if (not (. m find)) (recur (conj r next) rest) (recur r (into rest (expand-state next (. m start)))))))))

(defn process [row]
  (let [[condition target] (str/split row #" ")]
    (count (filter #(= target %) (map count-damaged (expand condition))))))

(defn part1 [input] (str (reduce + 0 (map process input))))
