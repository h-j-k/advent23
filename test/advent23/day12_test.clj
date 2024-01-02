(ns advent23.day12-test
  (:require [advent23.day12 :refer :all]
            [clojure.test :refer :all]))

(def input (clojure.string/split-lines (slurp "resources/day12.txt")))

(def answers (clojure.string/split-lines (slurp "resources/day12-answers.txt")))

(deftest part1-test (testing "Part 1" (is (= (part1 input) (first answers)))))
