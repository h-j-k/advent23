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

(defn next-step [blocks {:keys [last-steps loss] {:keys [x y]} :point} direction]
  (let [next {
              :x (cond (= direction \<) (dec x) (= direction \>) (inc x) :else x)
              :y (cond (= direction \^) (dec y) (= direction \v) (inc y) :else y)
              }]
    {:last-steps (conj (if (= (count last-steps) 3) (into [] (rest last-steps)) last-steps) direction)
     :loss       (+ loss (:loss (get blocks next {:loss 0})))
     :point      next}))

(defn except [directions] (filter (fn [d] (not (contains? directions d))) "^v<>"))

(defn opposite [direction] (cond (= direction \^) \v (= direction \v) \^ (= direction \<) \> (= direction \>) \<))

(defn next-steps [last-steps]
  (cond
    (< (count last-steps) 3) (except #{(opposite (last last-steps))})
    (= last-steps [\^ \> \v]) (rest last-steps)
    (= last-steps [\> \v \<]) (rest last-steps)
    (= last-steps [\v \< \^]) (rest last-steps)
    (= last-steps [\< \^ \>]) (rest last-steps)
    (= last-steps [\^ \< \v]) (rest last-steps)
    (= last-steps [\< \v \>]) (rest last-steps)
    (= last-steps [\v \> \^]) (rest last-steps)
    (= last-steps [\> \^ \<]) (rest last-steps)
    :else (let [x (last last-steps)] (except #{(opposite x) (if (and (= (count (distinct last-steps)) 1)) x)}))))

(defn process-next [blocks current is-within? {:keys [remaining seen] :as state}]
  (doseq [next (filter is-within? (map #(next-step blocks current %) (next-steps (:last-steps current))))]
    (let [key (dissoc next :loss) value (:loss next)]
      (if (< value (.getOrDefault seen key Long/MAX_VALUE)) (do (.put seen key value) (.add remaining next)))))
  state)

(defn process [blocks is-reached? is-within?]
  (let [origins (new PriorityQueue (comparator (fn [a b] (< (:loss a) (:loss b)))))]
    (.add origins {:last-steps [] :loss 0 :point {:x 0 :y 0}})
    (loop [state {:remaining origins :seen (new HashMap)}]
      (let [current (.poll (:remaining state))]
        (if (is-reached? current)
          (:loss current)
          (recur (process-next blocks current is-within? state)))))))

(defn part1 [input]
  (let [[blocks is-reached? is-within?] (parse input)]
    (str (process blocks is-reached? is-within?))))
