(ns aoc2022.day10
  (:require [aoc2022.utils.input :as ui]
            [clojure.string :as str]
            [net.cgrand.xforms :as x]))

(def input
  (->> (ui/day-input 10)
       (into [] (comp (map #(str/split % #" "))
                      (map (fn [[x y]] [(keyword x) (some-> y parse-long)]))))))

(defn process
  [input]
  (let [f (fn [state [instr v]]
            (condp = instr
              :noop (-> state (update :cycle inc))
              :addx (-> state (update :cycle #(+ % 2)) (update :x #(+ % v)))))
        init-state {:x 1 :cycle 1}]
    (reductions f init-state input)))

(defn base
  [input f]
  (let [xf (comp (x/partition 2 1) (keep f))]
    (->> input process (eduction xf))))

(defn part-1
  [input]
  (let [marks (range 20 221 40)
        f (fn [[a b]]
            (let [i (:cycle a) j (:cycle b)]
              (if-let [mark (some #(when (= % j) %) marks)]
                (* mark (:x b))
                (when-let [mark (some #(when (< i % j) %) marks)]
                  (* mark (:x a))))))]
    (->> (base input f) (reduce +))))

(defn part-2
  [input]
  (let [f (fn [[a b]]
            (let [i (:cycle a) j (:cycle b)]
              (for [k (range i j)
                    :let [v (if (= k j) (:x b) (:x a))
                          pos (mod (dec k) 40)]]
                (if (some #{pos} (->> (dec v) (iterate inc) (take 3)))
                  \#
                  \.))))]
    (->> (base input f)
         (into [] (comp cat (x/partition 40) (map str/join)))
         (run! println))))

(comment
  (part-1 input) ; 14920
  (part-2 input) ; BUCACBUZ
    ; (out) ###..#..#..##...##...##..###..#..#.####.
    ; (out) #..#.#..#.#..#.#..#.#..#.#..#.#..#....#.
    ; (out) ###..#..#.#....#..#.#....###..#..#...#..
    ; (out) #..#.#..#.#....####.#....#..#.#..#..#...
    ; (out) #..#.#..#.#..#.#..#.#..#.#..#.#..#.#....
    ; (out) ###...##...##..#..#..##..###...##..####.
  )
