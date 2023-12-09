(ns advent23.day09-test
  (:require [advent23.day09 :refer :all]
            [clojure.test :refer :all]))

(def input (clojure.string/split-lines (slurp "resources/day09.txt")))

(def test-input
  [
   "0 3 6 9 12 15",
   "1 3 6 10 15 21",
   "10 13 16 21 30 45"
   ])

(deftest part1-test (testing "Part 1" (is (= (part1 input) 1696140818))))

(deftest part2-test (testing "Part 2" (is (= (part2 input) 1152))))
