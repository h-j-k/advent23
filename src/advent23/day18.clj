(ns advent23.day18)

(defn calculate-area [{:keys [x1 y1 area vertices]} {:keys [direction delta]}]
  (let [x2 (cond (= "L" direction) (- x1 delta) (= "R" direction) (+ x1 delta) :else x1)
        y2 (cond (= "U" direction) (+ y1 delta) (= "D" direction) (- y1 delta) :else y1)]
    {:x1 x2 :y1 y2 :area (+ area (* x1 y2) (- (* x2 y1))) :vertices (+ vertices (abs (- x1 x2)) (abs (- y1 y2)))}))

(defn process [parser input]
  (let [{:keys [area vertices]} (reduce calculate-area {:x1 0 :y1 0 :area 0 :vertices 0} (map parser input))]
    (str (+ 1 (abs (/ area 2)) (/ vertices 2)))))

(defn parse-plan [line]
  (let [[_ direction delta] (re-find #"^([LRUD]) (\d+)" line)]
    (zipmap [:direction :delta] [direction (parse-long delta)])))

(defn part1 [input] (process parse-plan input))

(defn parse-color [line]
  (let [[_ hex-delta direction] (re-find #"#([0-9a-f]{5})([0-3]).$" line)]
    (zipmap [:direction :delta] [(str (nth "RDLU" (parse-long direction))) (Long/parseLong hex-delta 16)])))

(defn part2 [input] (process parse-color input))
