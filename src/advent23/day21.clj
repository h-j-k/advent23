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
  (let [[[offset-y offset-x :as offset] rocks]
        (reduce
          (fn [[start rocks] row] [(or start (:start row)) (into rocks (:rocks row))])
          [nil #{}] (map-indexed parse-row input))]
    [offset (into #{} (map (fn [[y x]] [(- y offset-y) (- x offset-x)]) rocks))]))

(defn is-not-in [[[offset-y offset-x] rocks]]               ; assume offset is mid-point of initial maze
  (fn [[y x :as p]] (not (contains? rocks p))))

(defn next-step [offset-rocks [y x]]
  (filterv (is-not-in offset-rocks) (map (fn [[dy dx]] [(+ y dy) (+ x dx)]) [[-1 0] [1 0] [0 -1] [0 1]])))

(defn part1 [input steps]
  (let [offset-rocks (parse-map input)]
    (loop [i 0 origins #{[0 0]}]
      (if (= i steps)
        (str (count origins))
        (recur (inc i) (into #{} (apply concat (map #(next-step offset-rocks %) origins))))))))
