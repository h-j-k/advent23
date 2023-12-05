(ns advent23.day04-test
  (:require [advent23.day04 :refer :all]
            [clojure.test :refer :all]))

(def input (clojure.string/split-lines (slurp "resources/day04.txt")))

(deftest part1-test (testing "Part 1" (is (= (part1 input) 22897))))

(deftest part2-test (testing "Part 2" (is (= (part2 input) 5095824))))
