(ns advent23.day02-test
  (:require [advent23.day02 :refer :all]
            [clojure.test :refer :all]))

(def input (clojure.string/split-lines (slurp "resources/day02.txt")))

(deftest test-part1 (testing "Part 1" (is (= (part1 input {:r 12 :g 13 :b 14}) 2545))))
