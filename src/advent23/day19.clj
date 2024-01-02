(ns advent23.day19
  (:require [clojure.string :as str]))

(defn to-workflow-step [step]
  (let [[_ xmas op raw-threshold result] (re-matches #"([xmas])(<|>)(\d+):(.+)" step)
        threshold (parse-long raw-threshold)
        getter (fn [xmas-values] (get xmas-values (str/index-of "xmas" xmas)))
        pred (if (= "<" op) (fn [value] (< value threshold)) (fn [value] (> value threshold)))]
    (comp (fn [bool] (if bool result)) pred getter)
    ))

(defn to-workflow-map [workflow]
  (let [[_ name steps else] (re-matches #"([a-z]+)\{((?:[xmas][<>]\d+:[ARa-z]+,?)+),([ARa-z]+)\}" workflow)]
    [name [(vec (rseq (mapv to-workflow-step (str/split steps #",")))) else]]))

(defn to-ratings [rating]
  (mapv parse-long (drop 1 (re-matches #"^\{x=(\d+),m=(\d+),a=(\d+),s=(\d+)\}$" rating))))

(defn workflows-and-ratings [input]
  (let [[workflows ratings] (map vec (filter #(not (= '("") %)) (partition-by #(= "" %) input)))]
    [(into {} (map to-workflow-map workflows)) (map to-ratings ratings)]))

(defn process-steps [[steps else] rating]
  (if (not (empty? steps))
    (let [r ((peek steps) rating)] (if (some? r) r (recur [(pop steps) else] rating)))
    else))

(defn process-workflow [workflow-map rating]
  (loop [steps-else (get workflow-map "in")]
    (let [r (process-steps steps-else rating)]
      (cond (= "A" r) (reduce + rating) (= "R" r) 0 :else (recur (get workflow-map r))))))

(defn part1 [input]
  (let [[workflow-map ratings] (workflows-and-ratings input)]
    (str (reduce + (map #(process-workflow workflow-map %) ratings)))))
