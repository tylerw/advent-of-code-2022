(ns aoc2022.day07
  (:require [aoc2022.utils.input :as ui]
            [net.cgrand.xforms :as x]))

(def input
  (->> (ui/day-input 7)
       (into [] (keep #(let [[match cmd path filesize filename]
                             (re-matches #"\$ (cd) (.+)|(\d+) (.+)" %)]
                         (cond 
                           (nil? match) nil
                           (= cmd "cd") [:cd path]
                           :else [:file filename (parse-long filesize)]))))))

(defn process
  [input]
  (let [safe-+ (fnil + 0)
        f (fn [state [cmd & args]]
            (condp = cmd
              :cd (let [[path] args] 
                    (if (= path "..")
                      (update state :path pop)
                      (update state :path conj args)))
              :file (let [[_ size] args
                          file-f (fn [state p]
                                   (if (seq p)
                                     (update-in state
                                                [:size p]
                                                #(safe-+ % size))
                                     state))
                          parents (reductions conj [] (:path state))]
                      (reduce file-f state parents))))]
    (->> input
         (reduce f {:path []})
         :size)))

(defn part-1
  [input]
  (let [xf (filter #(<= % 100000))]
    (->> input
         process
         vals
         (transduce xf +))))

(defn part-2
  [input]
  (let [sizes (->> input process)
        free (+ -70000000 30000000 (get sizes [["/"]]))
        xf (comp (filter #(<= free %)) x/min)]
    (->> sizes
         vals
         (x/some xf))))

(comment
  (part-1 input) ; 1490523
  (part-2 input) ; 12390492
  )
