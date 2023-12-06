(ns advent23.day06-test
  (:require [advent23.day06 :refer :all]
            [clojure.test :refer :all]))

(def input (clojure.string/split-lines (slurp "resources/day06.txt")))

(deftest part1-test (testing "Part 1" (is (= (part1 input) 1083852))))

(deftest part2-test (testing "Part 2" (is (= (part2 input) 23501589))))
