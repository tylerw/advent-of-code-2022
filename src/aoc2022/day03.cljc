(ns aoc2022.day03
  (:require [aoc2022.utils.input :as ui]
            [clojure.set :as set]
            [net.cgrand.xforms :as x]))

(defn priority
  [c]
  (if (Character/isUpperCase c)
    (-> c int (- (dec (int \A)) -26))
    (-> c int (- (dec (int \a))))))

(def input
  (->> (ui/day-input 3)
       (into [] (map #(->> % (map priority))))))

(defn part-1
  []
  (let [xf (comp
             (map (fn [coll] 
                    (let [half (-> coll count (/ 2))]
                      (->> coll (partition half)))))
             (map (fn [[a b]] (->> (set a) (set/intersection (set b)) first))))]
    (->> input
         (transduce xf +))))

(defn part-2
  []
  (let [xf (comp
             (x/partition 3)
             (map #(->> %
                        (map set)
                        (apply set/intersection)
                        first)))]
    (->> input
         (transduce xf +))))
