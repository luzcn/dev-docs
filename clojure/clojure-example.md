
## map examples
### assoc-in
Associates a value in a nested associative structure.
```clj
(def data {:name {:first "Z" :second "L"}})
assoc-in data [:name :second] "kkk")

=> {:name {:first "Z", :second "kkk"}}
```
