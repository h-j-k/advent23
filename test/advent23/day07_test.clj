(ns advent23.day07-test
  (:require [advent23.day07 :refer [part1]]
            [clojure.test :refer :all]))

(def input (clojure.string/split-lines (slurp "resources/day07.txt")))

(def test-input
  [
   "32T3K 765",
   "QQQJA 483",
   "T55J5 684",
   "KK677 28",
   "KTJJT 220"
   ])

(deftest part1-test (testing "Part 1" (is (= (part1 input) 251287184))))
