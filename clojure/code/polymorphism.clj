

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
