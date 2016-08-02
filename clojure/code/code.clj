
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

(keep-indexed (fn[idx item] (merge {:id idx} item)) data)