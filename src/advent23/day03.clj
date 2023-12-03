(ns advent23.day03)

(defn get-groups [re y s]
  "https://stackoverflow.com/a/21192299"
  (let [m (re-matcher re s)]
    ((fn step []
       (when (. m find)
         (cons {:element {:value (. m group) :symbol? (nil? (re-matches #"\d+" (. m group)))}
                :points  (map (fn [x] {:x x :y y}) (range (. m start) (. m end)))}
               (lazy-seq (step))))))))

(defn get-parts-and-symbols [y s]
  (let [parts (get-groups #"\d+" y s) symbols (get-groups #"[^0-9.]+" y s)] (into parts symbols)))

(defn get-parts-and-gears [y s]
  (let [parts (get-groups #"\d+" y s) gears (get-groups #"\*" y s)] (into parts gears)))

(defn process-with [f input]
  (group-by (fn [group] (true? (:symbol? (:element group)))) (flatten (map-indexed f input))))

(defn neighbors-of [p]
  (let [{x :x y :y} p]
    #{
      {:x (- x 1) :y (- y 1)},
      {:x x :y (- y 1)},
      {:x (+ x 1) :y (- y 1)},
      {:x (- x 1) :y y},
      {:x (+ x 1) :y y},
      {:x (- x 1) :y (+ y 1)},
      {:x x :y (+ y 1)},
      {:x (+ x 1) :y (+ y 1)}
      }))

(defn explode-all [symbols] (into #{} (mapcat neighbors-of (mapcat #(:points %) symbols))))

(defn near-symbol? [part targets] (some #(contains? targets %) (:points part)))

(defn number-of [part] (Integer/parseInt (:value (:element part))))

(defn two-neighboring-parts-product [parts gear]
  (let [neighboring-parts (filter #(near-symbol? % (explode-all [gear])) parts)]
    (if (= (count neighboring-parts) 2) (reduce * (map number-of neighboring-parts)) 0)))

(defn part1 [input]
  (let [{parts false symbols true} (process-with get-parts-and-symbols input) targets (explode-all symbols)]
    (reduce + (map number-of (filter #(near-symbol? % targets) parts)))))

(defn part2 [input]
  (let [{parts false gears true} (process-with get-parts-and-gears input)]
    (reduce + (map #(two-neighboring-parts-product parts %) gears))))
