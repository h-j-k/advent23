(ns advent23.day15-test
  (:require [clojure.test :refer :all])
  (:require [advent23.day15 :refer [part1]]))

(def input (clojure.string/split-lines (slurp "resources/day15.txt")))

(def answers (clojure.string/split-lines (slurp "resources/day15-answers.txt")))

(deftest part1-test (testing "Part 1" (is (= (part1 input) (first answers)))))
