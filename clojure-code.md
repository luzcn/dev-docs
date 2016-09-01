### Polymorphism
```clj


;; multimethod example 
;; 
;; clojure provides polymorphism
;; use "defmulti" to create a dispatching function
;; define the "defmethod" with the same function name followed immediately
;; with the dispatching function evaluation result
;; use ":default" to define the default method call
(ns clojure.code.multimethod
    :requires 
    [clojure.code])
(defmulti full-moon-behavior (fn [were-creature] (:creature-tpye were-creature)))

(defmethod full-moon-behavior :wolf
    [were-creature]
    (str (:name were-creature) " will howl and murder"))

(defmethod full-moon-behavior :human
    [were-creature]
    (str (:name were-creature) " will be normal"))

(defmethod full-moon-behavior :default
    [were-creature]
    (str (:name were-creature) " will take the default behavior"))



;; defprotocl example
(defprotocol Psychodynamics
  (thoughts [x] "The data type's innermost thoughts")
  (feelings-about [x] [x y] "Feelings about self or other"))

;; extend-type the followed java type indicate the funcion arguments types
(extend-type java.lang.Object
  Psychodynamics
  (thoughts [x] "Maybe the Internet is just a vector for toxoplasmosis")
  (feelings-about
    ([x] "meh")
    ([x y] (str "meh about " y))))


;; similar to extend-type
(extend-protocol Psychodynamics
  java.lang.String
  (thoughts [x] "Truly, the character defines the data type")
  (feelings-about
    ([x] "longing for a simpler way of life")
    ([x y] (str "envious of " y "'s simpler way of life")))

  java.lang.Object
  (thoughts [x] "Maybe the Internet is just a vector for toxoplasmosis")
  (feelings-about
    ([x] "meh")
    ([x y] (str "meh about " y))))

```

### reduce vs apply example
```clj
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


```

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
### map-indexed
```clj
;; example to use map-indexed
(map-indexed (fn[idx item] (merge {:id idx} item)) data)
```
### remove items from a set/list
```clj
(remove #{:a} #{:b :c :d :a :e})
;;=> (:e :c :b :d)

(remove #{:a} [:b :c :d :a :e :a :f])
;;=> (:b :c :d :e :f)
```

;; example to use loop
(loop [i 0]
  (println (str "Iteration " i))
  (if (< i 3)
    (recur (inc iteration))))

;; A simple example to compute factorial
(defn computeFactorial
  [number]
  (loop [n number, result 1]
    (if (= n 0) 
      result
      (recur (dec n) (* n result)))))

;; print out all the data in a given list
((fn [data]
   (loop [n data]
     (if (not-empty n)
       (do
         (println (first n))
         (recur (rest n)))
       ))) [1,2,35,67,23])

;; similar to this recursive function
(defn func [data]
  (if (not-empty data)
    (do
      (println (first data))
      (func (rest data)))))

;; use let instead of first/rest functions call
(defn printList [dataList]
  (if (not-empty dataList)
      (let [[p & remains] dataList]
        (do
          (println p)
          (printList remains)))))


### example from the book
```clj
(def mylist [{:name "head" :size 3}
             {:name "left-eye" :size 1}
             {:name "left-ear" :size 1}
             {:name "mouth" :size 1}
             {:name "nose" :size 1}
             {:name "neck" :size 2}
             {:name "left-shoulder" :size 3}
             {:name "left-upper-arm" :size 3}
             {:name "chest" :size 10}
             {:name "back" :size 10}
             {:name "left-forearm" :size 3}
             {:name "abdomen" :size 6}
             {:name "left-kidney" :size 1}
             {:name "left-hand" :size 2}
             {:name "left-knee" :size 2}
             {:name "left-thigh" :size 4}
             {:name "left-lower-leg" :size 3}
             {:name "left-achilles" :size 1}
             {:name "left-foot" :size 2}])

(defn replace_with [s]
  ;(println s)
  {:name (clojure.string/replace (:name s) #"^left-" "right-")
   :size (:size s)})

(defn transform [data]
  (loop [n data, result []] ; create a local variable n, init as input data
    (if (empty? n)  ; check if the sequence n is empty now
      result
      (let [[p & remains] n] (recur remains (into result (set [p (replace_with p)])))))))
```


### map implementation example
```clj
(defn mymap
  ([myfunc dataList] (mymap myfunc dataList []))
  ([myfunc dataList result]
    (if (empty? dataList)
      result
      (mymap myfunc (rest dataList) (conj result (myfunc (first dataList)))))))
```

### recursive
```clj
;;accumulate sum recursive example
(defn sum
  ([nums] (sum nums 0))
  ([nums result]
    (if (empty? nums)
      result
      (sum (rest nums) (+ (first nums) result)))))

;; factorial example
(defn fac
  ([n] (fac n 1))
  ([n result]
    (if (zero? n)
      result
      (fac (dec n) (* result n)))))

;; again, factorial recursive example
(def factorial
  (fn [n]
    ((fn fac [n result]
       (if (zero? n)
         result
         (fac (dec n) (* result n)))) n 1)))

;; factorial iterative (loop/recur) example
(def factorial
  (fn [n]
    (loop [cnt n acc 1]
      (if (pos? cnt)
        (recur (dec cnt) (* acc cnt))
        acc))))
```
