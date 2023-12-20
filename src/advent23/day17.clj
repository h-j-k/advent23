(ns advent23.day17
  (:import (java.util HashMap PriorityQueue)))

(defn parse-row [y s]
  (let [m (re-matcher #"." s)]
    ((fn step []
       (when (. m find) (cons {:loss (parse-long (. m group)) :point {:x (. m start) :y y}} (lazy-seq (step))))))))

(defn parse [input]
  (let [blocks (filter #(not (nil? %)) (flatten (map-indexed parse-row input)))
        target-x (dec (count (first input)))
        target-y (dec (count input))]
    [(zipmap (map #(:point %) blocks) blocks)
     (fn [{{:keys [x y]} :point}] (and (= x target-x) (= y target-y)))
     (fn [{{:keys [x y]} :point}] (and (<= 0 x target-x) (<= 0 y target-y)))]))

(defn next-step [blocks {:keys [last-steps loss] {:keys [x y]} :point} max direction]
  (let [next {
              :x (cond (= direction \<) (dec x) (= direction \>) (inc x) :else x)
              :y (cond (= direction \^) (dec y) (= direction \v) (inc y) :else y)
              }]
    {:last-steps (conj (if (= (count last-steps) max) (into [] (rest last-steps)) last-steps) direction)
     :loss       (+ loss (:loss (get blocks next {:loss 0})))
     :point      next}))

(defn except [directions] (filter (fn [d] (not (contains? directions d))) "^v<>"))

(defn opposite [direction] (cond (= direction \^) \v (= direction \v) \^ (= direction \<) \> (= direction \>) \<))

(defn next-steps [min max]
  (fn [last-steps]
    (let [x (peek last-steps)]
      (cond
        (and (= (count last-steps) max) (= (count (distinct last-steps)) 1)) (except #{(opposite x) x})
        (= (count (distinct (take-last min last-steps))) 1) (except #{(opposite x)})
        :else [x]))))

(defn process-next [blocks current is-within? min max {:keys [remaining seen] :as state}]
  (doseq [next (filter is-within? (map #(next-step blocks current max %) ((next-steps min max) (:last-steps current))))]
    (let [key (dissoc next :loss) value (:loss next)]
      (if (< value (.getOrDefault seen key Long/MAX_VALUE)) (do (.put seen key value) (.add remaining next)))))
  state)

(defn process [blocks is-reached? is-within? min max]
  (let [origins (new PriorityQueue (comparator (fn [a b] (< (:loss a) (:loss b)))))]
    (.add origins {:last-steps [] :loss 0 :point {:x 0 :y 0}})
    (loop [state {:remaining origins :seen (new HashMap)}]
      (let [current (.poll (:remaining state))]
        (if (is-reached? current)
          (str (- (:loss current) (:loss (get blocks {:x 0 :y 0}))))
          (recur (process-next blocks current is-within? min max state)))))))

(defn part1 [input]
  (let [[blocks is-reached? is-within?] (parse input)]
    (process blocks is-reached? is-within? 1 3)))

(defn part2 [input]
  (let [[blocks is-reached? is-within?] (parse input)
        part2-is-reached? (fn [v] (and (is-reached? v) (= (count (distinct (take-last 4 (:last-steps v)))) 1)))]
    (process blocks part2-is-reached? is-within? 4 10)))
