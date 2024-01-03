(ns advent23.day21)

(defn parse-row [y row]
  (let [m (re-matcher #"(#|S)" row)]
    (loop [r {:start nil :rocks []}]
      (if (. m find)
        (if (= "S" (. m group))
          (recur (assoc r :start [y (. m start)]))
          (recur (update r :rocks conj [y (. m start)])))
        r))))

(defn parse-map [input]
  (reduce
    (fn [[start rocks] row] [(or start (:start row)) (into rocks (:rocks row))])
    [nil #{}] (map-indexed parse-row input)))

(defn next-step [rocks [y x]]
  (filterv (fn [p] (not (contains? rocks p))) (map (fn [[dy dx]] [(+ y dy) (+ x dx)]) [[-1 0] [1 0] [0 -1] [0 1]])))

(defn part1 [input steps]
  (let [[start rocks] (parse-map input)]
    (loop [i 0 origins #{start}]
      (if (= i steps)
        (str (count origins))
        (recur (inc i) (into #{} (apply concat (map #(next-step rocks %) origins))))))))
