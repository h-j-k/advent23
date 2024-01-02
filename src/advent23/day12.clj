(ns advent23.day12
  (:require [clojure.string :as str]))

(defn count-damaged [condition]
  (let [m (re-matcher #"(#+|\?+)" condition)]
    (loop [r []]
      (if (not (. m find)) r (recur (conj r (if (str/includes? (. m group) "?") 0 (count (. m group)))))))))

(defn is-valid? [condition blocks]
  (let [damaged (count-damaged condition)]
    (loop [damaged-rest damaged blocks-rest blocks]
      (cond
        (empty? damaged-rest) true
        (empty? blocks-rest) (= 0 (first damaged-rest))
        (< (peek damaged-rest) (peek blocks-rest)) (not (= (get condition (- (count condition) (peek damaged-rest))) "."))
        (= (peek damaged-rest) (peek blocks-rest)) (recur (pop damaged-rest) (pop blocks-rest))
        :else false
        ))))

(defn expand-state [condition index blocks]
  (let [prefix (subs condition 0 index) suffix (subs condition (inc index))]
    (filterv (fn [v] (is-valid? v blocks)) (map str/reverse [(str prefix "." suffix) (str prefix "#" suffix)]))))

(defn expand [condition blocks]
  (loop [r [] to-process [condition]]
    (if (empty? to-process)
      (count r)
      (let [next (str/reverse (peek to-process)) m (re-matcher #"(\?)" next) rest (pop to-process)]
        (if (not (. m find))
          (recur (into r (filter (fn [v] (= blocks (count-damaged v))) [(str/reverse next)])) rest)
          (recur r (into rest (expand-state next (. m start) blocks))))))))

(defn process [row]
  (let [[condition target] (str/split row #" ")] (expand condition (mapv parse-long (str/split target #",")))))

(defn part1 [input] (str (reduce + 0 (map process input))))
