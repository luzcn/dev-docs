# re-frame

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
