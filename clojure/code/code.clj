
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


## map examples
### assoc-in
Associates a value in a nested associative structure.
```clj
(def data {:name {:first "Z" :second "L"}})
(assoc-in data [:name :second] "kkk")

=> {:name {:first "Z", :second "kkk"}}

;; assoc/assoc-in example
(-> {:name "T" :value 1} (assoc :debug false))
=> {:name "T", :value 1, :debug false}

(-> {:name "T" :value {:value 1}} (assoc-in [:value :debug] false))
=> {:name "T", :value {:value 1, :debug false}}
```

## remove items from a set/list
```clj
(remove #{:a} #{:b :c :d :a :e})
;;=> (:e :c :b :d)

(remove #{:a} [:b :c :d :a :e :a :f])
;;=> (:b :c :d :e :f)
```
