### Convert a lazy sequence
`dorun`, `doall`, and `doseq` are all for **forcing lazy sequences**, presumably to get side effects.

`dorun` - don't hold whole seq in memory while forcing, return nil

`doall` - hold whole seq in memory while forcing (i.e. all of it) and return the seq

`doseq` - same as dorun, but gives you chance to do something with each element as it's forced; returns nil

`for` is different in that it's a list comprehension, and isn't related to forcing effects. `doseq` and `for` have the same binding syntax, which may be a source of confusion, but `doseq` always returns nil, and `for` returns a lazy seq.


### Clojure destructuring
- use `:as all` to bind the entire input vector to the symbol `all`
```clojure
(def name [:v1 :v2 :v3 :v4])
(defn test-func [name]
	(let [[s1 :as all] name]
		(println "the first name of " all "is " s1)))
```
similar, we cau use `defnk` 
```clojure
(defnk test-func [s1 :as all]
  (println "the first name of " all "is " s1))
```

### comp
Takes a set of functions and returns a fn that is the composition
of those fns.
```clojure
(def negative-quotient (comp - /))
```

## map examples
### mapv
```clojure
;; A useful idiom to pull "columns" out of a vector of vecotr (2D array). 
;; Note, it is equivalent to:
;; (mapv vector [:a :b :c] [:d :e :f] [:g :h :i])

(apply mapv vector [[:a :b :c]
                    [:d :e :f]
                    [:g :h :i]])
;;=> [[:a :d :g] [:b :e :h] [:c :f :i]]
```

### assoc-in
Associates a value in a nested associative structure.
```clojure
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
```clojure
;; example to use map-indexed
(map-indexed (fn[idx item] (merge {:id idx} item)) data)
```
### remove items from a set/list
```clojure
(remove #{:a} #{:b :c :d :a :e})
;;=> (:e :c :b :d)

(remove #{:a} [:b :c :d :a :e :a :f])
;;=> (:b :c :d :e :f)
```

### example to use loop
```clojure
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
```


### Polymorphism
```clojure

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

```

### clojure protocol simple example

- define a protocol
```cljs
(defprotocol IGreet
  (say-hello [this]))
```
similar to create a Java Interface 
```java
Interface Greet{
    public sayHello();
}
```
- use clojure `record` to implement this protocol
```cljs
(defrecord person [name title]
  IGreet
  (say-hello [this]  (str "Hello " name)))

;; clojure record provides this map-> build factory
;; it construct a "Person" object
(defn client []
  (map->person {:name "Test Name" :title "TT"}))

;; reagent component
(defn component []
  [:div
   [:p (say-hello (client))]])
```

### example from the book
```clojure
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
      
(defn replace-with
  "replace all the 'left' string to 'right' string"
  [body-part]
  {:name (clojure.string/replace (:name body-part) #"left-" "right-")
   :size (:size body-part)})

(defn make-symetric [list]
  (vec (for [item list]
             (replace-with item))))
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
### reduce vs apply example
```clojure
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



### map implementation example
```clojure
(defn mymap
  ([myfunc dataList] (mymap myfunc dataList []))
  ([myfunc dataList result]
    (if (empty? dataList)
      result
      (mymap myfunc (rest dataList) (conj result (myfunc (first dataList)))))))
```

### create 2D arrays
```clojure
(defn create-matrix [x y]
  (vec (repeat x (vec (repeat y -1)))))
```

### Combine a vector of collections into a single collection 
```clojure
;; the return type is the type of the first collection in the vector.
(reduce into [[1 2 3] [:a :b :c] '([4 5] 6)])
;;=> [1 2 3 :a :b :c [4 5] 6]

;; The flatten function can be used to completely fuse 
;; all of the items of a nested tree into a single sequence.
;; Sometimes all that is needed is to fuse the first level
;; of a tree. This can be done with 'reduce' and 'into'.
(reduce into [] '([] [[10 18]] [[8 18]] [[10 12]] [[0 -6]] [[2 6]]))
;;=> [[10 18] [8 18] [10 12] [0 -6] [2 6]]
```
