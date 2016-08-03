
;;
;;  reduce vs apply example
;;
(apply hash-map [:a 5 :b 6])
;= {:a 5, :b 6}
(reduce hash-map [:a 5 :b 6])
;= {{{:a 5} :b} 6}

;; the clojure function returns the last value
((fn [x] 
    (cond  (< x 10)
           (do (println "x is a small number")
               :small)
           (< 100 x 1000)
           (do (println "x is a big number")
               :big)
           (>= x 10000)
           (do (println "x is a REALLY big number")
               :huge) :else
           (do (println  "x is just a medium-sized number")
               :medium))) 1000)

(def data [{:name "n" :value "n"} {:name "m" :value "m"}])

;; example to use map-indexed
(map-indexed (fn[idx item] (merge {:id idx} item)) data)

;; assoc/assoc-in example
(-> {:name "T" :value 1} (assoc :debug false))
=> {:name "T", :value 1, :debug false}

(-> {:name "T" :value {:value 1}} (assoc-in [:value :debug] false))
=> {:name "T", :value {:value 1, :debug false}}
