(ns aoc2022.day05
  (:require [aoc2022.utils.input :as ui]))

(defn transpose [m]
  (apply mapv vector m))

(def stacks
  (->> (ui/day-input 5)
       (into [] (take 9))
       transpose
       (filter #(Character/isDigit (last %)))
       (map #(->> (remove #{\space} %) vec))
       (reduce (fn [m stack]
                 (assoc m (str (last stack)) (reverse (pop stack))))
               {})))

(def directives
  (->> (ui/day-input 5)
       (into []
             (comp (drop 10)
                   (map #(let [[_ n from to]
                               (re-matches #"move (\d+) from (\d) to (\d)" %)]
                           [(parse-long n) from to]))))))

(defn move
  [m n from to f]
  (assert (<= n (count (m from))))
  (-> m
      (update from #(drop-last n %))
      (update to #(concat % (->> (m from) reverse (take n) f)))))

(defn print-stacks
  [stacks]
  (-> stacks (update-vals #(apply str %))))

(defn get-answer
  [stacks]
  (->> stacks 
       sort
       (map (fn [[_ v]] (last v)))
       (apply str)))

(defn base
  [stacks directives f]
  (-> (fn [m [n from to]]
        (move m n from to f))
      (reduce stacks directives)
      get-answer))

(defn part-1
  []
  (base stacks directives identity))

(defn part-2
  []
  (base stacks directives reverse))
