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


;; example from the book
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



;; map implementation example
(defn mymap
  ([myfunc dataList] (mymap myfunc dataList []))
  ([myfunc dataList result]
    (if (empty? dataList)
      result
      (mymap myfunc (rest dataList) (conj result (myfunc (first dataList)))))))


;; accumulate sum recursive example
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
