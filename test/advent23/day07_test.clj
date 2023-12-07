(ns advent23.day07-test
  (:require [advent23.day07 :refer :all]
            [clojure.test :refer :all]))

(def input (clojure.string/split-lines (slurp "resources/day07.txt")))

(deftest part1-test (testing "Part 1" (is (= (part1 input) 251287184))))

(deftest part2-test (testing "Part 2" (is (= (part2 input) 5905))))
