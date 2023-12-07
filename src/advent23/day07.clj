(ns advent23.day07
  (:require [clojure.string :as str]))

(def hand-types [:high :pair :two-pair :three-of-kind :full-house :four-of-kind :five-of-kind])

(def rank "23456789TJQKA")

(defn get-type [hand]
  (let [counts (sort > (vals (frequencies (:hand hand))))]
    (cond
      (= counts '(5)) :five-of-kind
      (= counts '(4 1)) :four-of-kind
      (= counts '(3 2)) :full-house
      (= counts '(3 1 1)) :three-of-kind
      (= counts '(2 2 1)) :two-pair
      (= counts '(2 1 1 1)) :pair
      (= counts '(1 1 1 1 1)) :high
      )))

(defn sort-hands [a b]
  (loop [[a-char & rest-a] a [b-char & rest-b] b]
    (let [a-rank (str/index-of rank a-char) b-rank (str/index-of rank b-char)]
      (cond
        (< a-rank b-rank) -1
        (> a-rank b-rank) 1
        :else (recur rest-a rest-b)
        ))))

(defn sort-all-hands [hand-and-bids]
  (run! println (map (comp #(sort-by :hand sort-hands %) val) (sort-by #(.indexOf hand-types (key %)) (group-by get-type hand-and-bids))))
  (flatten
    (map (comp #(sort-by :hand sort-hands %) val)
         (sort-by #(.indexOf hand-types (key %)) (group-by get-type hand-and-bids)))))

(defn to-hand-and-bids [line] (zipmap [:hand :bid] (into [] (str/split line #"\s+"))))

(defn part1 [input]
  (reduce + (map-indexed #(* (inc %1) (parse-long (:bid %2))) (sort-all-hands (map to-hand-and-bids input)))))
