(ns advent23.day13)

(defn is-horizontal-reflection? [pattern]
  (let [size (count pattern)]
    (loop [r 1]
      (if (= r size)
        0
        (let [n (min r (- size r))]
          (if (= (subvec pattern (- r n) r) (rseq (subvec pattern r (+ r n)))) r (recur (inc r))))))))

(defn is-vertical-reflection? [pattern] (is-horizontal-reflection? (mapv #(apply str %) (apply map list pattern))))

(defn part1 [input]
  (let [patterns (filter #(not (= '("") %)) (partition-by #(= "" %) input))]
    (str (reduce + (map
                     (fn [p] (+ (* 100 (is-horizontal-reflection? p)) (is-vertical-reflection? p)))
                     (map vec patterns))))))
