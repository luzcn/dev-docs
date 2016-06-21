# Create a simple cljs project

- create a new cljs project
```
lein new app <project name>

```
- update the `project.clj`
```clj
(defproject code "0.1.0-SNAPSHOT"
 :description "FIXME: write description"
 :url "http://example.com/FIXME"
 :license {:name "Eclipse Public License"
           :url "http://www.eclipse.org/legal/epl-v10.html"}
 :dependencies [[org.clojure/clojure "1.8.0"]
                [org.clojure/clojurescript "1.9.36"]
                [reagent "0.6.0-rc"]]
 :plugins [[lein-cljsbuild  "1.1.3"]]
 :cljsbuild {:builds [{:source-paths ["src/cljs"]
                       :compiler {
                                  :output-to "resources/core.js"
                                  :optimizations :whitespace
                                  :pretty-print true
                                  :verbose true}}]})

```

- create the `.cljs` and '.html' files
the command in terminal is:
```
mkdir cljs
touch cljs/example.cljs
touch resources/index.html
```
- update the `example.cljs`
```cljs
(defn simple-component []
      [:div
       [:p "I am a component!"]
       [:p.someclass
        "I have " [:strong "bold"]
        [:span {:style {:color "red"}} " and red "] "text."]])

(defn ^:export run[]
      (r/render [to-do] (js/document.getElementById "app")))
```

- update `index.html` as:
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Example</title>
</head>
<body>
<div id="app">
    <script type="text/javascript" src="./core.js"></script>
    <script>cljs.example.run()</script>
</div>
</body>
</html>
```
