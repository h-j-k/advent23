(ns advent23.day14-test
  (:require [clojure.test :refer :all])
  (:require [advent23.day14 :refer [part1]]))

(def input (clojure.string/split-lines (slurp "resources/day14.txt")))

(def answers (clojure.string/split-lines (slurp "resources/day14-answers.txt")))

(deftest part1-test (testing "Part 1" (is (= (part1 input) (first answers)))))
