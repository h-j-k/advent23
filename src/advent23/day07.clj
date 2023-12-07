(ns advent23.day07
  (:require [clojure.string :as str]))

(def hand-types [:high :pair :two-pair :three-of-kind :full-house :four-of-kind :five-of-kind])

(def part-1-rank "23456789TJQKA")

(def part-2-rank "J23456789TQKA")

(defn part-1-type [hand]
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

(defn part-2-type [hand]
  (if (nil? (str/index-of (:hand hand) "J"))
    (part-1-type hand)
    (last
      (sort-by
        #(.indexOf hand-types %)
        (map part-1-type
             (map #(assoc hand :hand (str/replace (:hand hand) "J" (str %))) (drop 1 part-2-rank)))))))

(defn sort-hands [rank]
  (fn [a b]
    (loop [[a-char & rest-a] a [b-char & rest-b] b]
      (let [a-rank (str/index-of rank a-char) b-rank (str/index-of rank b-char)]
        (cond
          (< a-rank b-rank) -1
          (> a-rank b-rank) 1
          :else (recur rest-a rest-b)
          )))))

(defn to-hand-and-bids [line] (zipmap [:hand :bid] (into [] (str/split line #"\s+"))))

(defn process [input get-type rank]
  (let [hand-and-bids (map to-hand-and-bids input)]
    (reduce
      +
      (map-indexed
        #(* (inc %1) (parse-long (:bid %2)))
        (flatten
          (map (comp #(sort-by :hand (sort-hands rank) %) val)
               (sort-by #(.indexOf hand-types (key %)) (group-by get-type hand-and-bids))))))))

(defn part1 [input] (process input part-1-type part-1-rank))

(defn part2 [input] (process input part-2-type part-2-rank))
