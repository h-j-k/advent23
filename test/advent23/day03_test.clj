(ns advent23.day03-test
  (:require [advent23.day03 :refer :all]
            [clojure.string]
            [clojure.test :refer :all]))

(def input (clojure.string/split-lines (slurp "resources/day03.txt")))

(deftest part1-test (testing "Part 1" (is (= (part1 input) 540212))))

(deftest part2-test (testing "Part 2" (is (= (part2 input) 87605697))))
