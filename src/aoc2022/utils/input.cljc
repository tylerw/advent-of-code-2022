(ns aoc2022.utils.input
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [cljc.java-time.local-date :as ld]
            [net.cgrand.xforms.io :as xio]))

(defn- current-year
  []
  (-> (ld/now) ld/get-year))

(defn day-input-resource
  "A day's input file."
  ([day year] (io/resource (format "aoc%s/day%02d.txt" year day)))
  ([day] (day-input-resource day (current-year))))

(defn day-input
  "A reducible view of a day's input (suitable for a transducer source).
  Note: one must use a method with an IReduce interface to extract values. So
  sequence, for example, will not work. But {re,trans}duce, into, etc. will."
  ([day year] (-> (day-input-resource day year) xio/lines-in))
  ([day] (day-input day (current-year))))

(defn day-input-string
  ([day year] (-> (day-input-resource day year) slurp str/trim))
  ([day] (day-input-string day (current-year))))

(defn day-input-intmatrix
  "Read a block of numbers (from a day's input) as a matrix."
  ([day year] (let [xf (comp
                         (map seq)
                         (map (fn [coll] (map #(Character/digit % 10) coll))))]
                (->> (day-input day year)
                     (into [] xf)
                     m/matrix)))
  ([day] (day-input-intmatrix day (current-year))))
