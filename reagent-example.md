# reagent example
## jQuery autocomplet example

In the `index.html` file, we add the jQuery files.
```html
<!DOCTYPE html>
<html lang="en">
  <body>
    <div id="app"></div>
    <!-- ATTENTION \/ -->
    <script src="https://code.jquery.com/jquery-1.11.2.min.js"></script>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.min.css">
    <script src="http://code.jquery.com/ui/1.11.2/jquery-ui.min.js"></script>
    <!-- ATTENTION /\ -->
    <script src="js/compiled/app.js"></script>
    <script>rcproject.core.main();</script>
  </body>
</html>
```

In the *core.cljs* file, we create two functions
```clj
(ns rcproject.core
    (:require [reagent.core :as reagent]))

;; typeahead example
(def tags
  ["ActionScript"
   "AppleScript"
   "Asp"
   "BASIC"
   "C"
   "C++"
   "Clojure"
   "COBOL"
   "ColdFusion"
   "Erlang"
   "Fortran"
   "Groovy"
   "Haskell"
   "Java"
   "JavaScript"
   "Lisp"
   "Perl"
   "PHP"
   "Python"
   "Ruby"
   "Scala"
   "Scheme"])

(defn home-render []
  [:div.ui-widget
   [:label {:for "tags"} "Programming Languages: "]
   [:input#tags]])
   
   (defn home-did-mount []
  (js/$ (fn []
          (.autocomplete (js/$ "#tags") 
                         (clj->js {:source tags})))))

(defn home []
  (reagent/create-class {:reagent-render home-render
                         :component-did-mount home-did-mount}))
                         
(defn ^:export main []
  (reagent/render [home]
                  (.getElementById js/document "app")))
```
