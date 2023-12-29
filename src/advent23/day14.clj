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

(defn count-load [line]
  (let [length (count line)] (reduce + 0 (map-indexed (fn [i v] (if (= \O v) (- length i) 0)) line))))

(defn part1 [input]
  (let [grid (map #(str/split % #"") input)
        transformed (map #(apply str %) (apply map list grid))]
    (str (reduce + 0 (map (comp count-load shift-left) transformed)))))
