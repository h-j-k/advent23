(ns advent23.day18-test
  (:require [advent23.day18 :refer :all]
            [clojure.test :refer :all]))

(def input (clojure.string/split-lines (slurp "resources/day18.txt")))

(def answers (clojure.string/split-lines (slurp "resources/day18-answers.txt")))

(deftest part1-test (testing "Part 1" (is (= (part1 input) (first answers)))))

(deftest part2-test (testing "Part 2" (is (= (part2 input) (second answers)))))
