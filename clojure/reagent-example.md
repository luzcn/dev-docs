# reagent example
## Binding the form to a document
```clj
(defn row [label input]
  [:div.row
   [:div.col-md-2 [:label label]]
   [:div.col-md-5 input]])

(def form-template
  [:div
   (row "first name" [:input {:field :text :id :first-name}])
   (row "last name" [:input {:field :text :id :last-name}])
   (row "age" [:input {:field :numeric :id :age}])
   (row "email" [:input {:field :email :id :email}])
   (row "comments" [:textarea {:field :textarea :id :comments}])])

(defn test-page []
  (let [doc (r/atom {})]
    (fn []
      [:div
       [:div.page-header [:h1 "Reagent Form"]]
       [bind-fields form-template doc]
       [:label (str (:first-name @doc))]])))
```

## Radio button example
```cljs
(defn form []
  (let [val (r/atom "Test ")]
    (fn []
      [:div
       [:div.page-header "The binding value will be changed here:" @val]
       [:label
        [:input {:type :radio
                 :value :v1
                 :name :foo
                 :id :rd1
                 :on-change #(reset! val (-> % .-target .-value))}] "foo"]
       [:label
        [:input {:type :radio
                 :value :v2 :name
                 :foo :id :rd2
                 :on-change #(reset! val (-> % .-target .-value))}] "bar"]
       [:label
        [:input {:type :radio
                 :value :v3
                 :name :foo
                 :id :rd3
                 :on-change #(reset! val (-> % .-target .-value))}] "baz"]])))

```


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
