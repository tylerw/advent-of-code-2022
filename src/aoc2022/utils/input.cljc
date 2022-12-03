(ns aoc2022.utils.input
  (:require [clojure.java.io :as io]
            [cljc.java-time.local-date :as ld]
            [net.cgrand.xforms.io :as xio]))

(defn day-input-resource
  "A day's input file."
  ([day year]
   (io/resource (format "aoc%s/day%02d.txt" year day)))
  ([day]
   (day-input-resource day (-> (ld/now) ld/get-year))))


(defn day-input
  "A reducible view of a day's input (suitable for a transducer source).
  Note: one must use a method with an IReduce interface to extract values. So
  sequence, for example, will not work. But {re,trans}duce, into, etc. will."
  ([day] (-> day day-input-resource xio/lines-in))
  ([day year] (-> (day-input-resource day year) xio/lines-in)))
