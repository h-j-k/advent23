(ns advent23.day13
  (:require [clojure.string :as str]))

(defn is-reflection? [pattern type]
  (let [size (count pattern)]
    (loop [i 1 r []]
      (if (= i size)
        r
        (let [n (min i (- size i))]
          (recur (inc i) (if (= (subvec pattern (- i n) i) (rseq (subvec pattern i (+ i n)))) (conj r [type i]) r)))))))

(defn is-horizontal-reflection? [pattern] (is-reflection? pattern :horizontal))

(defn is-vertical-reflection? [pattern] (is-reflection? (mapv #(apply str %) (apply map list pattern)) :vertical))

(defn find-reflection [pattern pred]
  (let [horizontals (filter pred (is-horizontal-reflection? pattern))]
    (if (not (empty? horizontals))
      (first horizontals)
      (let [verticals (filter pred (is-vertical-reflection? pattern))]
        (if (not (empty? verticals)) (first verticals))))))

(defn summarize [results]
  (let [f (fn [acc [type v]] (+ acc (cond (= :horizontal type) (* 100 v) (= :vertical type) v :else 0)))]
    (str (reduce f 0 results))))

(defn part1 [input]
  (summarize
    (map #(find-reflection % (fn [_] true)) (map vec (filter #(not (= '("") %)) (partition-by #(= "" %) input))))))

(defn smudge [pattern x y]
  (let [grid (mapv #(str/split % #"") pattern) row (get grid y) col (get row x)]
    (mapv #(apply str %) (assoc grid y (assoc row x (if (= col ".") "#" "."))))))

(defn smudges [pattern]
  (apply concat
         (map (fn [y] (map (fn [x] (smudge pattern x y)) (range 0 (count (first pattern))))) (range 0 (count pattern)))))

(defn different-reflection [pattern]
  (let [excludes #{(find-reflection pattern (fn [_] true))} pred (fn [v] (not (contains? excludes v)))]
    (first (filter (fn [v] (some? v)) (map #(find-reflection % pred) (smudges pattern))))))

(defn part2 [input]
  (summarize
    (map different-reflection (mapv vec (filter #(not (= '("") %)) (partition-by #(= "" %) input))))))
