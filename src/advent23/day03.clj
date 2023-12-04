(ns advent23.day03)

(defn get-groups [re y s]
  "https://stackoverflow.com/a/21192299"
  (let [m (re-matcher re s)]
    ((fn step []
       (when (. m find)
         (cons {:element {:value (. m group) :symbol? (nil? (re-matches #"\d+" (. m group)))}
                :points  (map (fn [x] {:x x :y y}) (range (. m start) (. m end)))}
               (lazy-seq (step))))))))

(defn process-with [re input]
  (let [f (fn [y s] (into (get-groups #"\d+" y s) (get-groups re y s)))]
    (group-by (fn [group] (true? (:symbol? (:element group)))) (flatten (map-indexed f input)))))

(defn nearby [p]
  (let [{x :x y :y} p]
    #{
      {:x (- x 1) :y (- y 1)}, {:x x :y (- y 1)}, {:x (+ x 1) :y (- y 1)},
      {:x (- x 1) :y y}, {:x (+ x 1) :y y},
      {:x (- x 1) :y (+ y 1)}, {:x x :y (+ y 1)}, {:x (+ x 1) :y (+ y 1)}
      }))

(defn explode-all [symbols] (into #{} (mapcat nearby (mapcat #(:points %) symbols))))

(defn near-symbol? [targets parts] (filter (fn [p] (some #(contains? targets %) (:points p))) parts))

(defn part-number-of [part] (parse-long (:value (:element part))))

(defn two-nearby-parts-product-else-0 [parts gear]
  (let [nearby-parts (near-symbol? (explode-all [gear]) parts)]
    (if (= (count nearby-parts) 2) (reduce * (map part-number-of nearby-parts)) 0)))

(defn part1 [input]
  (let [{parts false symbols true} (process-with #"[^0-9.]+" input) targets (explode-all symbols)]
    (reduce + (map part-number-of (near-symbol? targets parts)))))

(defn part2 [input]
  (let [{parts false gears true} (process-with #"\*" input)]
    (reduce + (map #(two-nearby-parts-product-else-0 parts %) gears))))
