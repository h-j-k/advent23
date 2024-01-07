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
  (let [n0 (process input 65) n1 (process input 196) n2 (process input 327)
        ; ** = f(202300) = f(202300 * 131 + 65) = f(26501365)
        ; to solve ax^2 + bx + c...
        ; n2 = f(2) = f(2 * 131 + 65) = f(327) [a(2 * 2) + 2b + c = 4a + 2b + c]
        ; n1 = f(1) = f(1 * 131 + 65) = f(196) [a(1 * 1) + 1b + c = a + b + c]
        ; n0 = f(0) = f(0 * 131 + 65) = f( 65) [a(0 * 0) + 0b + c = c]
        ; therefore:
        ; c = n0 <==
        ; b = n1 - n0 - a
        ; 4a = n2 - 2(n1 - n0 - a) - n0
        ; 2a = n2 - 2n1 + n0
        ; a = (n0 - 2n1 + n2) / 2 <==
        ; b = (2n1 - 2n0 - (n0 - 2n1 + n2)) / 2
        ; b = (-3n0 + 4n1 - n2) / 2 <==
        a (/ (+ n0 (- (* 2 n1)) n2) 2) b (/ (+ (- (* 3 n0)) (* 4 n1) (- n2)) 2) x 202300]
    (str (+ (* a x x) (* b x) n0))))
