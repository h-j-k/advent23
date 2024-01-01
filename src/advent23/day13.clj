(ns advent23.day13
  (:require [clojure.string :as str]))

(defn is-reflection? [pattern type]
  (let [size (count pattern)]
    (loop [i 1 r []]
      (if (= i size)
        r
        (let [n (min i (- size i))]
          (recur (inc i) (if (= (subvec pattern (- i n) i) (rseq (subvec pattern i (+ i n)))) (conj r [type i]) r)))))))

(defn find-reflection [pattern pred]
  (let [horizontals (filter pred (is-reflection? pattern :horizontal))]
    (if (not (empty? horizontals))
      (first horizontals)
      (let [verticals (filter pred (is-reflection? (mapv #(apply str %) (apply map list pattern)) :vertical))]
        (if (not (empty? verticals)) (first verticals))))))

(defn process [input mapper]
  (let [f (fn [acc [type v]] (+ acc (cond (= :horizontal type) (* 100 v) (= :vertical type) v :else 0)))]
    (str (reduce f 0 (map mapper (map vec (filter #(not (= '("") %)) (partition-by #(= "" %) input))))))))

(defn part1 [input] (process input #(find-reflection % (fn [_] true))))

(defn smudge [pattern x y]
  (let [grid (mapv #(str/split % #"") pattern) row (get grid y) col (get row x)]
    (mapv #(apply str %) (assoc grid y (assoc row x (if (= col ".") "#" "."))))))

(defn smudges [pattern]
  (let [indices (fn [coll] (range 0 (count coll)))]
    (apply concat (map (fn [y] (map (fn [x] (smudge pattern x y)) (indices (first pattern)))) (indices pattern)))))

(defn different-reflection [pattern]
  (let [excludes #{(find-reflection pattern (fn [_] true))} pred (fn [v] (not (contains? excludes v)))]
    (first (filter some? (map #(find-reflection % pred) (smudges pattern))))))

(defn part2 [input] (process input different-reflection))
