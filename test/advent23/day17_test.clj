(ns advent23.day17-test
  (:require [clojure.test :refer :all])
  (:require [advent23.day17 :refer [part1]]))

(def input (clojure.string/split-lines (slurp "resources/day17.txt")))

(def example (clojure.string/split-lines (slurp "resources/day17-example.txt")))

(def answers (clojure.string/split-lines (slurp "resources/day17-answers.txt")))

(deftest part1-test (testing "Part 1" (is (= (part1 input) (first answers)))))
