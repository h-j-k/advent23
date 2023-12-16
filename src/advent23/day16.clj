(ns advent23.day16)

(defn forward-slash [{:keys [d] {:keys [x y]} :point}]
  (cond
    (= d :north) [{:point {:x (inc x) :y y} :d :east}]
    (= d :east) [{:point {:x x :y (dec y)} :d :north}]
    (= d :south) [{:point {:x (dec x) :y y} :d :west}]
    (= d :west) [{:point {:x x :y (inc y)} :d :south}]))

(defn backslash [{:keys [d] {:keys [x y]} :point}]
  (cond
    (= d :north) [{:point {:x (dec x) :y y} :d :west}]
    (= d :east) [{:point {:x x :y (inc y)} :d :south}]
    (= d :south) [{:point {:x (inc x) :y y} :d :east}]
    (= d :west) [{:point {:x x :y (dec y)} :d :north}]))

(defn pipe [{:keys [d] {:keys [x y]} :point}]
  (cond
    (= d :north) [{:point {:x x :y (dec y)} :d d}]
    (= d :south) [{:point {:x x :y (inc y)} :d d}]
    :else [{:point {:x x :y (dec y)} :d :north} {:point {:x x :y (inc y)} :d :south}]))

(defn hyphen [{:keys [d] {:keys [x y]} :point}]
  (cond
    (= d :east) [{:point {:x (inc x) :y y} :d d}]
    (= d :west) [{:point {:x (dec x) :y y} :d d}]
    :else [{:point {:x (inc x) :y y} :d :east} {:point {:x (dec x) :y y} :d :west}]))

(defn pass [{:keys [d] {:keys [x y]} :point}]
  (cond
    (= d :north) [{:point {:x x :y (dec y)} :d d}]
    (= d :east) [{:point {:x (inc x) :y y} :d d}]
    (= d :south) [{:point {:x x :y (inc y)} :d d}]
    (= d :west) [{:point {:x (dec x) :y y} :d d}]))

(defn parse-row [y s]
  "https://stackoverflow.com/a/21192299"
  (let [m (re-matcher #"[^.]" s)]
    ((fn step []
       (when (. m find)
         (let [value (first (. m group))
               fn (cond (= \/ value) forward-slash (= \\ value) backslash (= \| value) pipe (= \- value) hyphen)]
           (cons {:fn fn :point {:x (. m start) :y y}} (lazy-seq (step)))))))))

(defn parse [input]
  (let [widgets (filter #(not (nil? %)) (flatten (map-indexed parse-row input)))]
    [(zipmap (map #(:point %) widgets) widgets)
     (fn [{{:keys [x y]} :point}] (and (<= 0 x (dec (count (first input)))) (<= 0 y (dec (count input)))))]))

(defn count-energized-tiles [widgets is-within? start]
  (loop [remaining (transient [start]) seen (transient #{})]
    (if (zero? (count remaining))
      (count (distinct (map #(:point %) (persistent! seen))))
      (let [current (get remaining (dec (count remaining)))]
        (recur (reduce conj!
                       (pop! remaining)
                       (if (contains? seen current) [] (filter is-within? ((:fn (get widgets (:point current) {:fn pass})) current))))
               (conj! seen current))))))

(defn part1 [input]
  (let [[widgets is-within?] (parse input)]
    (count-energized-tiles widgets is-within? {:point {:x 0 :y 0} :d :east})))
