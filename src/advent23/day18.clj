(ns advent23.day18)

(defn calculate-area [{:keys [x1 y1 area vertices]} {:keys [direction delta]}]
  (let [delta-value (parse-long delta)
        x2 (cond (= "L" direction) (- x1 delta-value) (= "R" direction) (+ x1 delta-value) :else x1)
        y2 (cond (= "U" direction) (+ y1 delta-value) (= "D" direction) (- y1 delta-value) :else y1)]
    {:x1 x2 :y1 y2 :area (+ area (* x1 y2) (- (* x2 y1))) :vertices (+ vertices (abs (- x1 x2)) (abs (- y1 y2)))}))

(defn calculate-area-with-vertices [plans]
  (let [{:keys [area vertices]} (reduce calculate-area {:x1 0 :y1 0 :area 0 :vertices 0} plans)]
    (str (+ 1 (abs (/ area 2)) (/ vertices 2)))))

(defn parse [line] (zipmap [:direction :delta] (rest (re-matches #"^([LRUD]) (\d+) .*$" line))))

(defn part1 [input] (calculate-area-with-vertices (map parse input)))
