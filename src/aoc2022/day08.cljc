(ns aoc2022.day08
  (:require [aoc2022.utils.input :as ui]
            [clojure.core.matrix :as m]))

(def input (ui/day-input-intmatrix 8))

(defn visible
  [m]
  (let [f (fn [[x y] v]
            (let [up (->> (m/get-column m y) (take x))
                  left (->> (m/get-row m x) (take y))
                  down (->> (m/get-column m y) (drop (inc x)))
                  right (->> (m/get-row m x) (drop (inc y)))
                  xf (map #(if (every? (partial > v) %) 1 0))]
              (transduce xf + 0 [up left down right])))]
     (-> (m/emap-indexed f m) (m/gt 0) m/esum)))

(defn part-1
  []
  (visible input))

(defn viewing-distance
  [m]
  (let [f (fn [[x y] v]
            (let [up (->> (m/get-column m y) (take x) reverse)
                  left (->> (m/get-row m x) (take y) reverse)
                  down (->> (m/get-column m y) (drop (inc x)))
                  right (->> (m/get-row m x) (drop (inc y)))
                  xf (map (fn [dir]
                            (if (zero? (count dir))
                              0
                              (let [view (->> dir (take-while #(< % v)) count)]
                                (+ view (if (seq (drop view dir)) 1 0))))))]
              (transduce xf * 1 [up left down right])))]
     (-> (m/emap-indexed f m) m/maximum)))

(defn part-2
  []
  (viewing-distance input))
