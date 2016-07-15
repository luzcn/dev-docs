(ns hg.client.components.cards.create-vcn
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require
    [cats.monad.either :as m-either]
    [cljs.core.async :refer [<! timeout]]
    [hg.client.i18n :refer [t]]
    [hg.client.components.viewstack :refer [push-view! splice-view!
                                            get-state set-state!
                                            set-pending-state! set-error-state!]]
    [hg.client.components.viewstack.events :as e :refer [get-val]]
    [hg.client.components.viewstack.protocol :refer [component]]
    [hg.client.ds :as ds]
    [hg.client.components.views :as v]
    [hg.client.error :as error :refer [service-error]]
    [hg.client.events :as events]
    [hg.client.re-frame :refer [dispatch]]
    [hg.client.system :as system]
    [plumbing.core :refer-macros [letk]]
    [reagent.core :as r]
    [re-frame.core :as re-frame]
    [taoensso.timbre :as log]
    [hg.client.transactor :as txr]))

;; create the auto create vcn dialog page
(defn create-auto-dialog[view-id state]
  (fn [view-id state]
    (letk [availability-domains (re-frame/subscribe [:transacted {:resource [:list :availability-domains]
                                                                           :space :vcn}])
           ad-list (map #(hash-map :name (:name %) :value (:name %)) (txr/unwrap @availability-domains))]
    [:div
     [:div.row [:text (t :vcns.auto-create.details )]]
     [:br]
     [:div.subfieldset-container
      [:h3.subfieldset-title (t :vcns.create-vcn)]
       [:div.field-pair
        [:ul
       [:li {:style {:paddingBottom "10px"}} (t :labels.name) ": " (get-in @state [:displayName])]
       [:li (t :vcns.auto-create.default-cidrBlock)]]]]

     [:div.subfieldset-container
      [:h3.subfieldset-title (t :gateways.create-gateway)]
      [:div.field-pair
       [:text (t :labels.name) ": " (t :gateways.default-name)]]]

     [:div.subfieldset-container
      [:h3.subfieldset-title (t :route-table-rules.update-default-route-table)]
      [:div.field-pair
       [:text (t :labels.name) ": " (t :route-table-rules.default-route-table-rule (str "- " (t :gateways.default-name)))]]]
     (for [ad (map-indexed (fn[idx item] (merge {:index idx} item)) ad-list)]
       ^{:key (:name  ad)}
       [:div.subfieldset-container
          [:h3.subfieldset-title (t :subnets.create-subnet)]
          [:div.field-pair
           [:ul
           [:li {:style {:paddingBottom "10px"}} (t :labels.name) ": " (t :subnets.default-name (:name  ad))]
           [:li {:style {:paddingBottom "10px"}} (t :subnets.default-security-list)]
           [:li {:style {:paddingBottom "10px"}} (t :subnets.default-cidr (:index ad) (:index ad) (:index ad))]
           [:li (t :subnets.default-route-table)]]]])])))

;; create the manually create vcn dialog page
(defn create-manual-dialog [view-id state]
  (fn [view-id state]
    [:div
     [:div.row [:text (t :vcns.manual-create.details)]]
     [:div.field-pair
      [:label {:for "new-vcn-cidr-block"} (t :forms.new-vcn.labels.cidrBlock)]
      [:input {:type "text"
               :id "new-vcn-cidr-block"
               :value (get-in @state [:cidrBlock])
               :on-change #(set-state! view-id [:cidrBlock] (get-val %1))}]]]))


;; the vcn auto creation confirmation page
(defmethod component :create-vcn-confirmation [_ params]
  {:config {:container :form-container}
   :state  (merge-with merge
                       {:title (t :vcns.create-vcn)
                        :cancelable true}
                       params)
   :view (fn [view-id]
           (letk [state @(get-state view-id)
                  availability-domains (re-frame/subscribe [:transacted {:resource [:list :availability-domains]
                                                                                :space :vcn}])
                  ad-list (map #(hash-map :name (:name %) :value (:name %)) (txr/unwrap @availability-domains))]
             (fn [view-id]
                 [:div
                  [:form {:on-submit (partial e/dispatch-and-cancel
                                              (e/view-event :create-vcn-confirmation-submit view-id))}
                   [:div.subfieldset-container
                    [:h3.subfieldset-title (t :vcns.create-vcn)]
                    [:div.field-pair
                     (if (nil? (:vcn-error state))
                       [:text (t :vcns.auto-create.confirmation (:vcn-name state))]
                       [:text {:style {:color "red"}} (:vcn-error state)])]]

                   [:div.subfieldset-container
                    [:h3.subfieldset-title (t :gateways.create-gateway)]
                    [:div.field-pair
                     (if (nil? (:gateway-error state))
                       [:text (t :gateways.confirmation (:gateway-name state))]
                       [:text {:style {:color "red"}} (:gateway-error state)])]]

                   [:div.subfieldset-container
                    [:h3.subfieldset-title (t :route-table-rules.update-default-route-table)]
                    [:div.field-pair
                     (if (nil? (:rt-error state))
                       [:text (t :route-table-rules.confirmation (:rt-name state))]
                       [:text {:style {:color "red"}} (:rt-error state)])]]
                   (for [ad ad-list]
                     ^{:key (:name  ad)}
                     [:div.subfieldset-container
                      [:h3.subfieldset-title (t :subnets.create-subnet)]
                      [:div.field-pair
                       (if (nil? (:subnet-error state))
                         [:text (t :subnets.confirmation (t :subnets.default-name (:name  ad)))]
                         [:text {:style {:color "red"}} (:subnet-error state)])]])
                   [:hr]
                   [:input.action {:type "submit"
                                   :value (t :actions.close)}]]])))
   :handlers {:create-vcn-confirmation-submit
              (fn [db [_ evt]]
                (letk [[view-id] evt]
                  (splice-view! view-id))
                db)}})

;; create the subnets for each AD
(defn auto-create-subnets [vcn-res result]
  (letk [[subnet-store] @system/system
         rt-id (:defaultRouteTableId (:body @vcn-res))
         compartment-id (:compartmentId (:body @vcn-res))
         availability-domains (re-frame/subscribe [:transacted {:resource [:list :availability-domains]
                                                                :space :vcn}])
         ad-list (map #(hash-map :name (:name %) :value (:name %) ) (txr/unwrap @availability-domains))]
    (go-loop [ad ad-list index 0]
             (if (seq ad)
               (let [subnet-res (<! (ds/create-item subnet-store
                                                    {:vcnId (:id (:body @vcn-res))
                                                     :cidrBlock (str "10.0." index ".0/24")
                                                     :displayName (t :subnets.default-name (:name  ad))
                                                     :routeTableId rt-id
                                                     :availabilityDomain (:value (first ad))
                                                     :compartmentId compartment-id}))]
                 (if (m-either/left? subnet-res)
                   (swap! result assoc :subnet-error (:message (:body @subnet-res)))
                   (recur (rest ad) (inc index))))))))

;; create-vcn view and handlers
(defmethod component :create-vcn [_ params]
  {:config {:container :form-container}
   :state (merge-with merge
                      {:title (t :vcns.create-vcn)
                       :pending-label (t :vcns.create-pending)
                       :cancelable true
                       :expanded true
                       :cidrBlock "10.0.0.0/16"}
                      params)
   :view (fn [view-id]
           (let [state (get-state view-id)]
             (fn [view-id]
               (dispatch (events/transact [{:resource [:list :availability-domains]
                                            :space :vcn}]))
               (if (:pending @state)
               [:div
                [:label "pending..."]]
               [:div
                [:form {:on-submit (partial e/dispatch-and-cancel
                                            (e/view-event :create-vcn-submit view-id {:cidrBlock (:cidrBlock @state)
                                                                                      :displayName (:displayName @state)}))}
                 [:fieldset
                  [:div.field-pair
                   [:label {:for "new-vcn-display-name"} (t :labels.name)]
                   [:input {:type "text"
                            :id "new-vcn-display-name"
                            :value (get-in @state [:displayName])
                            :on-change #(set-state! view-id [:displayName] (get-val %1))}]]
                  [:div.row [:label
                             [:input {:type :radio
                                      :name "choice"
                                      :id "auto"
                                      :default-checked true
                                      :on-change #(set-state! view-id {:expanded true
                                                                       :error nil
                                                                       :cidrBlock "10.0.0.0/16"})}]
                             (t :vcns.auto-create.label)]]
                  [:div.row [:label
                             [:input {:type :radio
                                      :name "choice"
                                      :id "manual"
                                      :on-change #(set-state! view-id {:expanded false
                                                                       :error nil
                                                                       :cidrBlock nil})}]
                             (t :vcns.manual-create.label)]]
                  [:br]
                  (if (:expanded @state)
                      [create-auto-dialog view-id state]
                      [create-manual-dialog view-id state])]
                (if-let [err (:error @state)]
                  [:div {:style {:color "red"}} err])
                [:hr]
                [:input.action {:type "submit"
                                :value (t :actions.create)}]]]))))
   :handlers {:create-vcn-submit
              (fn [db [_ evt]]
                (letk [[vcn-store internet-gateway-store route-table-store ] @system/system
                       [view-id params] evt
                       state (get-state view-id)]
                  (set-pending-state! view-id)
                  (go
                    (let [vcn-res (<! (ds/create-item vcn-store params)) result (atom {})]
                      (if (m-either/right? vcn-res)
                        (do
                          (when (:expanded @state)
                            (let [gateway-res (<! (ds/create-item
                                                    internet-gateway-store {:vcnId (:id (:body @vcn-res))
                                                                            :displayName (str (t :gateways.default-name) " " (:displayName (:body @vcn-res)))
                                                                            :isEnabled true}))]
                              (if (m-either/right? gateway-res)
                                (let [rt-res (<! (ds/update-item
                                                   route-table-store {:rt-id (:defaultRouteTableId (:body @vcn-res))
                                                                      :routeRules [{:cidrBlock (t :route-table-rules.default-cidrBlock)
                                                                                    :networkEntityType (t :route-table-rules.default-networkEntityType)
                                                                                    :networkEntityId (:id (:body @gateway-res))}]}))]
                                  (if (m-either/left? rt-res)
                                    (swap! result assoc :rt-error (:message (:body @rt-res)))))
                                (swap! result assoc :gateway-error (:message (:body @gateway-res)) :rt-error "")))
                            ;(auto-create-subnets vcn-res result)
                            (push-view! :create-vcn-confirmation (merge @result {:vcn-name (:displayName (:body @vcn-res))
                                                                                 :gateway-name (str (t :gateways.default-name) " " (:displayName (:body @vcn-res)))
                                                                                 :rt-name (:defaultRouteTableId (:body @vcn-res))})))
                            (splice-view! view-id)
                            (dispatch (events/transact [{:resource [:list :vcns]
                                                         :space :vcns}])))
                          (set-error-state! view-id (service-error (:body @vcn-res)))))))
                db)}})