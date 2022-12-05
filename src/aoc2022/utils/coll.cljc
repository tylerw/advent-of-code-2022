(ns aoc2022.utils.coll)

(defn transpose [m]
  (apply mapv vector m))
