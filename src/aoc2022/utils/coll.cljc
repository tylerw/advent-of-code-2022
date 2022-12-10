(ns aoc2022.utils.coll)

(defn transpose [m]
  (apply mapv vector m))

(defn enumerate
  ([coll] (map-indexed vector coll))
  ([coll start] (map-indexed (fn [i v] [(+ start i) v]) coll)))

(defn index-of
  [pred coll]
  (->> coll
       (keep-indexed (fn [i v] (when (pred v) i)))
       first))
