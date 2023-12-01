(ns advent23.day01_test
  (:require [advent23.day01 :refer :all]
            [clojure.test :refer :all]))

(deftest day01.part1
  (testing "Part 1"
    (is (= (part1 (clojure.string/split-lines (slurp "resources/day01.txt"))) 56049))))

(deftest day01.part2
  (testing "Part 2"
    (is (= (part2 (clojure.string/split-lines (slurp "resources/day01.txt"))) 54530))))
