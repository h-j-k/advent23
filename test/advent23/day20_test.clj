(ns advent23.day20-test
  (:require [clojure.test :refer :all])
  (:require [advent23.day20 :refer :all]))

(def input (clojure.string/split-lines (slurp "resources/day20.txt")))

(def answers (clojure.string/split-lines (slurp "resources/day20-answers.txt")))

(deftest part1-test (testing "Part 1" (is (= (part1 input) (first answers)))))
