(ns advent23.day08-test
  (:require [advent23.day08 :refer :all]
            [clojure.test :refer :all]))

(def input (clojure.string/split-lines (slurp "resources/day08.txt")))

(def test-input-1
  [
   "RL",
   "",
   "AAA = (BBB, CCC)",
   "BBB = (DDD, EEE)",
   "CCC = (ZZZ, GGG)",
   "DDD = (DDD, DDD)",
   "EEE = (EEE, EEE)",
   "GGG = (GGG, GGG)",
   "ZZZ = (ZZZ, ZZZ)",
   ])

(def test-input-2
  [
   "LLR",
   "",
   "AAA = (BBB, BBB)",
   "BBB = (AAA, ZZZ)",
   "ZZZ = (ZZZ, ZZZ)",
   ])

(deftest part1-test (testing "Part 1" (is (= (part1 input) 12361))))
