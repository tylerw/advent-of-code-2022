(ns aoc2022.day01
  (:require [aoc2022.utils.input :as ui]
            [net.cgrand.xforms :as x]))

(def input
  (->> (ui/day-input 1)
       (into []
              (comp (map parse-long)
                    (partition-by nil?)
                    (take-nth 2)
                    (map #(apply + %))))))

(defn part-1
  []
  (->> input
      (apply max)))


(defn part-2
  []
  (->> input
       (transduce (comp (x/sort >) (take 3)) +)))
