(ns advent23.day01-test
  (:require [advent23.day01 :refer :all]
            [clojure.test :refer :all]))

(def input (clojure.string/split-lines (slurp "resources/day01.txt")))

(deftest part1-test (testing "Part 1" (is (= (part1 input) 56049))))

(deftest part2-test (testing "Part 2" (is (= (part2 input) 54530))))
