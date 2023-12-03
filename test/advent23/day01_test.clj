(ns advent23.day01-test
  (:require [advent23.day01 :refer :all]
            [clojure.test :refer :all]))

(def input (clojure.string/split-lines (slurp "resources/day01.txt")))

(deftest test-part1 (testing "Part 1" (is (= (part1 input) 56049))))

(deftest test-part2 (testing "Part 2" (is (= (part2 input) 54530))))
