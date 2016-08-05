# re-frame
Impelemnt some reagent example with re-frame. The original codes are https://reagent-project.github.io/

### Update the textbox value example

This simple example demonstrated how to use re-frame:
* register handlers `reg-event` to initialize and update the `db` value. To call this handler, you can use `dispatch` with the corresponding keyword.
* register subs `reg-sub`, which defines some operations to fetch data from `db`. Use `subscribe` to actually call the corresponding subs.

```cljs
(ns cljsproject.views.subs
  (:require [reagent.core :as r]
            [re-frame.core :refer [reg-event
                                   path
                                   reg-sub
                                   dispatch
                                   dispatch-sync
                                   subscribe]]))


;; re-frame register handler
(reg-event
  :initialize
  (fn [db env]
    (merge db {:text "some data"})))

;; usage: (dispatch [:update [:text "some new values"]])
(reg-event
  :update
  (fn [db [_ [path value]]]
    (assoc db path value)))


;; usage: (subscribe [:text-value])
(reg-sub
  :text-value
  (fn [db]
    (:text db)))
    
(defn text-component []
  (let [value (subscribe [:text-value])]
    (fn []
      [:div
       [:p "The value is now: " @value]
       [:input {:type "text"
                :value @value
                :on-change #(dispatch [:update [:text (.-target.value %)]])}]])))
```


```cljs
(ns cljsproject.views.test-page
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [reagent.core :as r]
            [re-frame.core :as re-frame]))


;; register sub
;; usage (subscribe [:data])
(re-frame/register-sub
  :phones
  (fn [db]
    (reaction (:phones @db))))


;; re-frame handler
(re-frame/register-handler
  :initialize-db
 (fn [_ _]                   ;; Ignore both params (db and v).
  {:phones [{:name "Nexus S" :snippet "THe phone details"}
            {:name "Nexus S 2" :snippet "THe phone details sdf"}]}))


(defn phones-component
  []
  (let [phones (re-frame/subscribe [:phones])] ; subscribe to the phones value in our db
    (fn []
      (for [phone @phones]
        ^{:key phone}
        [:ul 
          [:li
            [:span (:name phone)]
            [:p (:snippet phone)]]]))))

(defn test-page []
  [:div
   [:h3 "Test"]
   [phones-component]])
   
   ;; ----------
   (defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (re-frame/dispatch [:initialize-db])
  (mount-root))

```
