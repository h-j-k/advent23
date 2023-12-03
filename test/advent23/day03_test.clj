(ns advent23.day03-test
  (:require [advent23.day03 :refer [part1]]
            [clojure.string]
            [clojure.test :refer :all]))

(def input (clojure.string/split-lines (slurp "resources/day03.txt")))

(def test-input '[
                  "467..114..",
                  "...*......",
                  "..35..633.",
                  "......#...",
                  "617*......",
                  ".....+.58.",
                  "..592.....",
                  "......755.",
                  "...$.*....",
                  ".664.598.."])

(deftest part1-test (testing "Part 1" (is (= (part1 input) 540212))))
