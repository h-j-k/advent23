(ns advent23.day04
  (:require [clojure.set :as set]
            [clojure.string :as str]))

(defn winning-cards [card] (count (set/intersection (:winning card) (:own card))))

(defn to-points [card]
  (let [exp (winning-cards card)] (if (> exp 0) (long (Math/pow 2 (- exp 1))) 0)))

(defn process-cards [cards card]
  (let [n (get cards (- (:id card) 1)) copies (range (winning-cards card))]
    (reduce (fn [acc c] (assoc acc c (+ (get acc c) n))) cards (map #(+ (:id card) %) copies))))

(defn process [s]
  (let [[_ id winning own] (re-matches #"Card\s+(\d+):([^|]+)\|(.+)" s)
        to-numbers (fn [xs] (into #{} (map parse-long (drop 1 (str/split xs #"\s+")))))]
    {:id (parse-long id) :winning (to-numbers winning) :own (to-numbers own)})
  )

(defn part1 [input] (reduce + (map (comp to-points process) input)))

(defn part2 [input]
  (reduce + (reduce process-cards (into [] (repeat (count input) 1)) (map process input))))
