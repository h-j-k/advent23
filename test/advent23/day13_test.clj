(ns advent23.day13-test
  (:require [clojure.test :refer :all])
  (:require [advent23.day13 :refer :all]))

(def input (clojure.string/split-lines (slurp "resources/day13.txt")))

(def answers (clojure.string/split-lines (slurp "resources/day13-answers.txt")))

(deftest part1-test (testing "Part 1" (is (= (part1 input) (first answers)))))
