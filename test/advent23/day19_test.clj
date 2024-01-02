(ns advent23.day19-test
  (:require [advent23.day19 :refer :all]
            [clojure.test :refer :all]))

(def input (clojure.string/split-lines (slurp "resources/day19.txt")))

(def answers (clojure.string/split-lines (slurp "resources/day19-answers.txt")))

(deftest part1-test (testing "Part 1" (is (= (part1 input) (first answers)))))
