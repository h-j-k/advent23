(ns advent23.day03)

(defn get-groups [re y s]
  "https://stackoverflow.com/a/21192299"
  (let [m (re-matcher re s)]
    ((fn step []
       (when (. m find)
         (cons {:element {:value (. m group) :symbol? (nil? (re-matches #"\d+" (. m group)))}
                :points  (map (fn [x] {:x x :y y}) (range (. m start) (. m end)))}
               (lazy-seq (step))))))))

(defn get-numbers-and-symbols [y s]
  (let [numbers (get-groups #"\d+" y s)
        symbols (get-groups #"[^0-9.]+" y s)]
    (into numbers symbols)))

(defn explode [p]
  (let [{x :x y :y} p]
    (list
      {:x (- x 1) :y (- y 1)},
      {:x x :y (- y 1)},
      {:x (+ x 1) :y (- y 1)},
      {:x (- x 1) :y y},
      {:x (+ x 1) :y y},
      {:x (- x 1) :y (+ y 1)},
      {:x x :y (+ y 1)},
      {:x (+ x 1) :y (+ y 1)}
      )))

(defn part1 [input]
  (let [groups (flatten (map-indexed get-numbers-and-symbols input))]
    (let [{numbers false symbols true} (group-by (fn [group] (true? (:symbol? (:element group)))) groups)]
      (let [neighboring-symbols (into #{} (flatten (map explode (flatten (map #(:points %) symbols)))))]
        (reduce + (map (fn [n] (Integer/parseInt (:value (:element n))))
                       (filter (fn [n] (some #(contains? neighboring-symbols %) (:points n))) numbers)))))))