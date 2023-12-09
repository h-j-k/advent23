(ns advent23.day08
  (:require [clojure.string :as str]))

(defn parse-nodes [line]
  (let [[_ node left right] (re-matches #"(\w+) = .(\w+), (\w+)." line)]
    {node {:left left :right right}}))

(defn parse-input [input]
  (let [[[instructions] & list-of-nodes] (filter #(not (= '("") %)) (partition-by #(= "" %) input))
        nodes (into {} (map parse-nodes (flatten list-of-nodes)))]
    [(fn [i] (get instructions (rem i (count instructions)))) nodes]))

(defn process [step nodes start-node end-pred]
  (loop [i 0 current start-node]
    (if (end-pred current)
      i
      (let [instruction (step i) next-step (inc i) next-pair (get nodes current)]
        (cond
          (= instruction \L) (recur next-step (:left next-pair))
          (= instruction \R) (recur next-step (:right next-pair))
          )))))

(defn gcd [a b] (loop [a (abs a) b (abs b)] (if (zero? b) a, (recur b (mod a b)))))

(defn lcm [a b] (cond (zero? a) 0 (zero? b) 0 :else (abs (* b (quot a (gcd a b))))))

(defn part1 [input]
  (let [[step nodes] (parse-input input)] (process step nodes "AAA" #(= % "ZZZ"))))

(defn part2 [input]
  (let [[step nodes] (parse-input input)
        node-ending (fn [s] #(str/ends-with? % s))]
    (reduce lcm (map #(process step nodes % (node-ending "Z")) (filter (node-ending "A") (keys nodes))))))
