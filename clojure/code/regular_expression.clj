;; the string replace function takes 3 parameters
;; the input string 
;; the regular expression match patter
;; the replace string
(clojure.string/replace "The input string" #"input" "output")
=> "The output string"