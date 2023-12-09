(ns advent23.day08)

(defn parse-nodes [line]
  (let [[_ node left right] (re-matches #"([A-Z]+) = .([A-Z]+), ([A-Z]+)." line)]
    {node {:left left :right right}}))

(defn process [input]
  (let [[[instructions] & list-of-nodes] (filter #(not (= '("") %)) (partition-by #(= "" %) input))
        nodes (into {} (map parse-nodes (flatten list-of-nodes)))]
    [(fn [i] (get instructions (rem i (count instructions)))) nodes]))

(defn part1 [input]
  (let [[step nodes] (process input)]
    (loop [i 0 node "AAA"]
      (if (= node "ZZZ")
        i
        (let [instruction (step i) next-step (inc i) next-pair (get nodes node)]
          (cond
            (= instruction \L) (recur next-step (:left next-pair))
            (= instruction \R) (recur next-step (:right next-pair))
            ))))))
