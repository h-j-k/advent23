(ns advent23.day14
  (:require [clojure.string :as str]))

(defn shift-left [line]
  (let [next (re-matcher #"\.O" line)]
    (if (. next find)
      (let [suffix (subs line (. next end))
            target (re-matcher #"^.*([O#])\." (subs line 0 (inc (. next start))))]
        (if (. target find)
          (recur (str (subs line 0 (dec (. target end))) "O" (subs line (. target end) (dec (. next end))) "." suffix))
          (recur (str "O" (apply str (repeat (dec (. next end)) ".")) suffix)))
        )
      line)))

(defn count-load [grid]
  (let [size (count grid)] (reduce + (map-indexed (fn [i v] (* (- size i) (count (filter #(= \O %) v)))) grid))))

(defn counter-clockwise-flip [grid] (mapv #(apply str %) (apply map list grid)))

(defn tilt-north [grid]
  (counter-clockwise-flip (mapv shift-left (counter-clockwise-flip (mapv #(str/split % #"") grid)))))

(defn part1 [input] (str (count-load (tilt-north input))))

(defn tilt-west [grid] (mapv shift-left grid))

(defn tilt-south [grid]
  (rseq (counter-clockwise-flip (mapv shift-left (counter-clockwise-flip (rseq (mapv #(str/split % #"") grid)))))))

(defn tilt-east [grid] (mapv str/reverse (mapv shift-left (mapv #(apply str %) (mapv str/reverse grid)))))

(defn part2 [input]
  (let [spin-cycle (fn [grid] (tilt-east (tilt-south (tilt-west (tilt-north grid)))))]
    (str (count-load (reduce (fn [acc _] (spin-cycle acc)) input (range 1000)))))) ; magic?
