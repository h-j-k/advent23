(ns advent23.day04
  (:require [clojure.set :as set]
            [clojure.string :as str]))

(defn to-points [card]
  (let [exp (count (set/intersection (:winning card) (:own card)))]
    (cond (> exp 0) (long (Math/pow 2 (- exp 1))) :else 0)))

(defn process [s]
  (let [[_ id winning own] (re-matches #"Card\s+(\d+):([^|]+)\|(.+)" s)
        to-numbers (fn [xs] (into #{} (map parse-long (drop 1 (str/split xs #"\s+")))))]
    {:id (parse-long id) :winning (to-numbers winning) :own (to-numbers own)})
  )

(defn part1 [input] (reduce + (map (comp to-points process) input)))