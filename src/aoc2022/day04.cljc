(ns aoc2022.day04
  (:require [aoc2022.utils.input :as ui]
            [clojure.string :as str]
            [clojure.set :as set]
            [net.cgrand.xforms :as x]))

(defn range->set
  [s]
  (let [[a b] (-> s (str/split #"-" 2) (->> (map parse-long)))]
    (set (range a (inc b)))))

(def input
  (->> (ui/day-input 4)
       (into []
              (map #(-> % (str/split #"," 2) (->> (map range->set)))))))

(defn part-1
  []
  (->> input
      (x/count (comp (map (fn [[a b]] (or (set/subset? a b) (set/subset? b a))))
                     (filter identity)))))

(defn part-2
  []
  (->> input
       (x/count (comp (map (fn [[a b]] (set/intersection a b)))
                      (filter seq)))))
