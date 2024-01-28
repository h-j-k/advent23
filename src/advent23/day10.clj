(ns advent23.day10)

(defn parse-row [y row]
  (let [m (re-matcher #"[SJL7F|-]" row)]
    (loop [r {:start nil :pipes []}]
      (if (. m find)
        (if (= "S" (. m group))
          (let [s (assoc r :start [y (. m start)])]
            (recur (update s :pipes conj [[y (. m start)] (first (. m group))])))
          (recur (update r :pipes conj [[y (. m start)] (first (. m group))])))
        r))))

(defn parse-map [input]
  (reduce
    (fn [[start pipes] row] [(or start (:start row)) (into pipes (:pipes row))])
    [nil {}] (map-indexed parse-row input)))

(defn delta [[y x] [py px]] [(- y py) (- x px)])

(defn loop-along [start pipes]
  (loop [[y x :as curr] [(inc (first start)) (last start)] prev start seen #{} verticals {}]
    (let [seen' (conj seen curr) [dy dx] (delta curr prev)]
      (case (get pipes curr)
        \S [seen' verticals]
        \J (recur (if (zero? dy) [(dec y) x] [y (dec x)])
                  curr seen' (update verticals y conj x))
        \L (recur (if (zero? dy) [(dec y) x] [y (inc x)])
                  curr seen' (update verticals y conj x))
        \7 (recur (if (zero? dy) [(inc y) x] [y (dec x)])
                  curr seen' verticals)
        \F (recur (if (zero? dy) [(inc y) x] [y (inc x)])
                  curr seen' verticals)
        \| (recur [(+ y dy) (+ x dx)]
                  curr seen' (update verticals y conj x))
        \- (recur [(+ y dy) (+ x dx)]
                  curr seen' verticals)))))

(defn count-if [pred xs] (reduce (fn [acc x] (if (pred x) (inc acc) acc)) 0 xs))

(defn enclosed [seen verticals h w]
  (for [y (range h)
        :let [row-verticals (verticals y)]
        :when row-verticals
        :let [min-vertical (apply min row-verticals)
              max-vertical (apply max row-verticals)]
        x (range w)
        :when (and (< min-vertical x max-vertical)
                   (not (seen [y x]))
                   (odd? (count-if #(< % x) row-verticals)))]
    1))

(defn part1 [input]
  (let [[start pipes] (parse-map input) [seen _] (loop-along start pipes)]
    (str (/ (count seen) 2))))

(defn part2 [input]
  (let [[start pipes] (parse-map input) [seen verticals] (loop-along start pipes)]
    (str (count (enclosed seen verticals (count input) (count (first input)))))))
