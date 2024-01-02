(ns advent23.day25
  (:require [clojure.string :as str])
  (:import (org.jgrapht.alg StoerWagnerMinimumCut)
           (org.jgrapht.graph DefaultEdge SimpleGraph)))

(defn parse-row [row]
  (let [[_ component others] (re-find #"^([a-z]{3}): ([a-z ]+)$" row)] [component (str/split others #" ")]))

(defn update-connections [connections [source targets]]
  (reduce (fn [r target] (update r target (fnil conj []) source)) connections targets))

(defn to-graph [connections]
  (let [graph (SimpleGraph. DefaultEdge)]
    (doseq [component (keys connections)] (.addVertex graph component))
    (doseq [[source targets] connections target targets] (.addEdge graph source target))
    graph))

(defn part1 [input]
  (let [maps (into {} (map parse-row input))
        connections (reduce update-connections maps maps)
        min-cut (.minCut (StoerWagnerMinimumCut. (to-graph connections)))]
    (str (* (count min-cut) (- (count (keys connections)) (count min-cut))))))
