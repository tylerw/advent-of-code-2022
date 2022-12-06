(ns aoc2022.day06
  (:require [aoc2022.utils.input :as ui]
            [net.cgrand.xforms :as x]))

(def input (ui/day-input-string 6))

(defn base
  [n]
  (->> input
       (x/count (comp (x/partition n 1)
                      (take-while #(not (apply distinct? %)))))
       (+ n)))

(defn part-1 [] (base 4))

(defn part-2 [] (base 14))
