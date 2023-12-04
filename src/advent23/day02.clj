(ns advent23.day02
  (:require [clojure.string :as str]))

(defn &<= [this other]
  (and (<= (:r this) (:r other)) (<= (:g this) (:g other)) (<= (:b this) (:b other))))

(defn least-number [game]
  (let [least-of (fn [a b] {:r (max (:r a) (:r b)) :g (max (:g a) (:g b)) :b (max (:b a) (:b b))})]
    (reduce * (vals (reduce least-of {:r 0 :g 0 :b 0} (:sets game))))))

(defn to-cube-set [s]
  (let [parse (fn [re] (parse-long (or (last (re-find re s)) "0")))]
    {:r (parse #"(\d+) r") :g (parse #"(\d+) g") :b (parse #"(\d+) b")}))

(defn to-game [s]
  (let [[_ id sets] (re-matches #"Game (\d+): (.+)" s)]
    {:id (parse-long id) :sets (map to-cube-set (str/split sets #"; "))}))

(defn part1 [input target]
  (let [is-in (fn [game target] (every? #(&<= % target) (:sets game)))]
    (reduce + (map #(:id %) (filter #(is-in % target) (map to-game input))))))

(defn part2 [input] (reduce + (map least-number (map to-game input))))
