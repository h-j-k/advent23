(ns advent23.day05
  (:require [clojure.string :as str]))

(defn in-range-or-nil [n {:keys [from to delta]}] (if (<= from n to) (+ n delta)))

(defn find-next [n map-of-ranges]
  (or (first (filter number? (map #(in-range-or-nil n %) map-of-ranges))) n))

(defn to-ranges [numbers]
  (let [[dest src range] (map parse-long (str/split numbers #" "))]
    {:from src :to (+ src (dec range)) :delta (- dest src)}))

(defn to-map [m] (sort-by :from (map to-ranges (drop 1 m))))

(defn process [input]
  (let [[seeds & list-of-maps] (filter #(not (= '("") %)) (partition-by #(= "" %) input))]
    (zipmap [:seed-numbers :maps]
            [(map parse-long (drop 1 (str/split (first seeds) #" ")))
             (map to-map list-of-maps)])))

(defn seed-ranges [seed-numbers]
  (sort-by :start (map (fn [[start range]] {:start start :stop (+ start (dec range))}) (partition 2 seed-numbers))))

(defn process-by-intervals [seeds maps]
  (loop [[{:keys [start stop]} & rest-seeds :as seeds] seeds
         [{:keys [from to delta]} & rest-maps :as maps] maps
         result []]
    (if (or (empty? seeds) (empty? maps))
      (sort-by :start (into result seeds))
      (cond
        (> start to) (recur seeds rest-maps result)
        (< stop from) (recur rest-seeds maps (conj result {:start start :stop stop}))
        (<= from start stop to) (recur rest-seeds maps (conj result {:start (+ delta start) :stop (+ delta stop)}))
        (<= from start to stop) (recur (conj rest-seeds {:start (inc to) :stop stop})
                                       rest-maps
                                       (conj result {:start (+ delta start) :stop (+ delta to)}))
        (<= start from stop to) (recur rest-seeds maps (-> result
                                                           (conj {:start start :stop (dec from)})
                                                           (conj {:start (+ delta from) :stop (+ delta stop)})))))))

(defn part1 [input]
  (let [{seed-numbers :seed-numbers maps :maps} (process input)]
    (reduce min (map #(reduce find-next % maps) seed-numbers))))

(defn part2 [input]
  (let [{seed-numbers :seed-numbers maps :maps} (process input)]
    (:start (first (reduce process-by-intervals (seed-ranges seed-numbers) maps)))))
