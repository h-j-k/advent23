(ns advent23.day08-test
  (:require [advent23.day08 :refer :all]
            [clojure.test :refer :all]))

(def input (clojure.string/split-lines (slurp "resources/day08.txt")))

(deftest part1-test (testing "Part 1" (is (= (part1 input) 12361))))

(deftest part2-test (testing "Part 2" (is (= (part2 input) 18215611419223))))
