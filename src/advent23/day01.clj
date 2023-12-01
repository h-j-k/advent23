(ns advent23.day01
  (:require [clojure.string :as str]))

(defn calibrate [s]
  (def numbers (filter number? (map read-string (str/split s #""))))
  (+ (* 10 (first numbers)) (last numbers)))

(defn part1 [input] (reduce + (map calibrate input)))

(defn remap [s]
  (reduce-kv str/replace s
             (array-map "one" "o1e" "two" "t2o" "three" "t3e" "four" "f4r" "five" "f5e" "six" "s6x" "seven" "s7n" "eight" "e8t" "nine" "n9e")))

(defn part2 [input] (reduce + (map calibrate (map remap input))))
