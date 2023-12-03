(ns advent23.day02
  (:require [clojure.string :as str]))

(defn less-than-equals [this other]
  (and (<= (:r this) (:r other)) (<= (:g this) (:g other)) (<= (:b this) (:b other))))

(defn is-in [game target] (every? #(less-than-equals % target) (:sets game)))

(defn fewest-of [this other]
  {:r (max (:r this) (:r other)) :g (max (:g this) (:g other)) :b (max (:b this) (:b other))})

(defn fewest-number [game]
  (let [fewest (reduce fewest-of {:r 0 :g 0 :b 0} (:sets game))]
    (reduce * [(:r fewest) (:g fewest) (:b fewest)])))

(defn to-cube-set [s]
  {
   :r (Integer/parseInt (or (last (re-find #"(\d+) r" s)) "0"))
   :g (Integer/parseInt (or (last (re-find #"(\d+) g" s)) "0"))
   :b (Integer/parseInt (or (last (re-find #"(\d+) b" s)) "0"))
   })

(defn to-game [s]
  (let [[_ id raw-sets] (re-matches #"Game (\d+): (.+)" s)]
    (let [sets (map to-cube-set (str/split raw-sets #"; "))]
      {:id (Integer/parseInt id) :sets sets})))

(defn part1 [input target]
  (reduce + (map #(:id %) (filter #(is-in % target) (map to-game input)))))

(defn part2 [input]
  (reduce + (map fewest-number (map to-game input))))
