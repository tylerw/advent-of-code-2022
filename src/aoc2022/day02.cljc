(ns aoc2022.day02
  (:require [aoc2022.utils.input :as ui]
            [clojure.string :as str]))

(def input
  (->> (ui/day-input 2)
       (into [] (map #(str/split % #" ")))))

(def play->point
  {"X" 1, "Y" 2, "Z" 3})

(def score-p1
  {;rock
   ["A" "X"] 3
   ["A" "Y"] 6
   ["A" "Z"] 0
   ;paper
   ["B" "X"] 0
   ["B" "Y"] 3
   ["B" "Z"] 6
   ;scissors
   ["C" "X"] 6
   ["C" "Y"] 0
   ["C" "Z"] 3
   })

(defn part-1
  []
  (->> input
       (transduce (mapcat (juxt score-p1 (comp play->point second))) +)))

(def beat
  {"A" "Y"    ; paper(Y) beats rock(A)
   "B" "Z"    ; scissors(Z) beats paper(B)
   "C" "X"})  ; rock(X) beats scissors(C)

(def draw
  {"A" "X"
   "B" "Y"
   "C" "Z"})

(def lose-to 
  {"A" "Z"    ; scissors(Z) loses to rock(A)
   "B" "X"    ; rock(X) loses to paper(B)
   "C" "Y"})  ; paper(Y) loses to scissors(C)

(defn score-p2
  [[opp action]]
  (condp = action
    "X" ;lose
    [(score-p1 [opp (lose-to opp)]) (play->point (lose-to opp))]
    "Y" ;draw
    [(score-p1 [opp (draw opp)]) (play->point (draw opp))]
    "Z" ;win
    [(score-p1 [opp (beat opp)]) (play->point (beat opp))]))

(defn part-2
  []
  (->> input
       (transduce (mapcat score-p2) +)))
