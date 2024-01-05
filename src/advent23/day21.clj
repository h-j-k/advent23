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

(defn is-not-in [[[offset _] rocks]]
  ; assume offset is mid-point of initial *square* maze
  (fn [[y x]]
    (let [size (inc (* offset 2)) shifter (fn [v] (- (mod (+ v offset) size) offset))]
      (not (contains? rocks [(shifter y) (shifter x)])))))

(defn next-step [offset-rocks [y x]]
  (filterv (is-not-in offset-rocks) (map (fn [[dy dx]] [(+ y dy) (+ x dx)]) [[-1 0] [1 0] [0 -1] [0 1]])))

(defn process [input steps]
  (let [offset-rocks (parse-map input)]
    (loop [i 0 origins #{[0 0]}]
      (if (= i steps)
        (count origins)
        (recur (inc i) (into #{} (apply concat (map #(next-step offset-rocks %) origins))))))))

(defn part1 [input steps] (str (process input steps)))

(defn part2 [input]
  (let [v1 (process input 65) v2 (process input 196) v3 (process input 327)
        a (/ (+ v1 (- (* 2 v2)) v3) 2)
        b (/ (+ (- (* 3 v1)) (* 4 v2) (- v3)) 2)
        x 202300]
    (+ (* a x x) (* b x) v1)))