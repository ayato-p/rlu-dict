(ns rlu-dict.util.time
  (:require [clj-time.coerce :as cc]
            [clj-time.format :as cf]))

(def japanese-style-format (cf/formatter "yyyy/MM/dd"))

(defprotocol Humanizer
  (humanize [x]))

(extend-protocol Humanizer
  java.util.Date
  (humanize [x]
    (humanize (cc/to-date-time x)))

  org.joda.time.DateTime
  (humanize [x]
    (cf/unparse japanese-style-format x)))
