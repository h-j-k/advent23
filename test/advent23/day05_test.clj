(ns advent23.day05-test
  (:require [advent23.day05 :refer :all]
            [clojure.test :refer :all]))

(def input (clojure.string/split-lines (slurp "resources/day05.txt")))

(deftest part1-test (testing "Part 1" (is (= (part1 input) 240320250))))

(deftest part2-test (testing "Part 2" (is (= (part2 input) 28580589))))
