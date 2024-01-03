(ns advent23.day21-test
  (:require [clojure.test :refer :all])
  (:require [advent23.day21 :refer :all]))

(def input (clojure.string/split-lines (slurp "resources/day21.txt")))

(def answers (clojure.string/split-lines (slurp "resources/day21-answers.txt")))

(deftest part1-test (testing "Part 1" (is (= (part1 input 64) (first answers)))))
