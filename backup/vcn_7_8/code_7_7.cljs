(ns hg.client.pages.vcn.create-vcn
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require
    [cats.monad.either :as m-either]
    [cljs.core.async :as async :refer [<! timeout]]
    [hg.client.components.viewstack :refer [push-view! splice-view!
                                            get-state set-state!
                                            set-pending-state! set-error-state!]]
    [hg.client.components.viewstack.events :as e :refer [get-val]]
    [hg.client.components.viewstack.protocol :refer [component]]
    [hg.client.ds :as ds]
    [hg.client.error :refer [service-error]]
    [hg.client.events :as events]
    [hg.client.i18n :refer [t]]
    [hg.client.re-frame :refer [subscribe dispatch]]
    [hg.client.system :as system]
    [hg.client.transactor :as txr]
    [plumbing.core :refer-macros [letk]]
    [taoensso.timbre :as log]))

(defn create-auto-dialog[view-id state]
  (fn [view-id state]
    (letk [availability-domains (subscribe [:transacted {:resource [:list :availability-domains]
                                                                           :space :vcn}])
           ad-list (map #(hash-map :name (:name %) :value (:name %)) (txr/unwrap @availability-domains))]
    [:div
     [:p (t :vcns.auto-create.details )]
     [:br]
     [:div.subfieldset-container
      [:h3.subfieldset-title (t :vcns.create-title)]
       [:div.field-pair
        [:ul
       [:li (t :labels.name) ": " (t :vcns.auto-create.name-label)]
       [:li (t :vcns.auto-create.default-cidrBlock)]]]]

     [:div.subfieldset-container
      [:h3.subfieldset-title (t :gateways.create-title)]
      [:div.field-pair
       [:p (t :labels.name) ": " (t :gateways.default-name)]]]

     [:div.subfieldset-container
      [:h3.subfieldset-title (t :route-rules.update-default-route-table)]
      [:div.field-pair
       [:p (t :labels.name) ": " (t :route-rules.default-route-table-rule (str "- " (t :gateways.default-name)))]]]

     (for [ad (map-indexed (fn[idx item] (merge {:index idx} item)) ad-list)]
       ^{:key (:name  ad)}
       [:div.subfieldset-container
          [:h3.subfieldset-title (t :subnets.create-title)]
          [:div.field-pair
           [:ul
           [:li (t :labels.name) ": " (t :subnets.default-name (:name  ad))]
           [:li (t :subnets.default-security-list)]
           [:li (t :subnets.default-cidr (:index ad) (:index ad) (:index ad))]
           [:li (t :subnets.default-route-table)]]]])])))

(defn create-manual-dialog [view-id state]
  (fn [view-id state]
     [:div
      [:p (t :vcns.manual-create.details)]
     [:div.field-pair
      [:label {:for "cidr"} (t :labels.cidrBlock)]
      [:input {:type "text"
               :id "cidr"
               :value (get-in @state [:form :cidr])
               :on-change #(set-state! view-id [:form :cidr] (get-val %1))}]]]))

;; create the subnets for each AD
(defn auto-create-subnets [vcn-res result ad-list]
  (letk [[subnet-store] @system/system
         rt-id (:defaultRouteTableId (:body @vcn-res))
         compartment-id (:compartmentId (:body @vcn-res))]
      (for [ad (map-indexed (fn [idx item] (merge {:index idx} item)) ad-list)]
        (go
          (ds/create-item subnet-store
                                  {:vcnId (:id (:body @vcn-res))
                                   :cidrBlock (str "10.0." (:index ad) ".0/24")
                                   :displayName (t :subnets.default-name (:name  ad))
                                   :routeTableId rt-id :availabilityDomain (:value ad)
                                   :compartmentId compartment-id})))))

(defmethod component :create-vcn-confirmation [_ params]
  {:config   {:container :form-container}
   :state    (merge-with merge
                         {:title      (t :vcns.create-title)
                          :cancelable true}
                         params)
   :view     (fn [view-id]
               (letk [availability-domains (subscribe [:transacted {:resource [:list :availability-domains]
                                                                    :space    :vcn}])
                      ad-list (map #(hash-map :name (:name %) :value (:name %)) (txr/unwrap @availability-domains))
                      state @(get-state view-id)]
                 (fn [view-id]
                   [:div
                    [:form {:on-submit (partial e/dispatch-and-cancel
                                                (e/view-event :create-vcn-confirmation-submit view-id))}
                     [:div.subfieldset-container
                      [:h3.subfieldset-title (t :vcns.create-title)]
                      [:div.field-pair
                       (if (nil? (:vcn-error state))
                         [:p (t :vcns.auto-create.confirmation (:vcn-name state))]
                         [:p.general-error (:vcn-error state)])]]

                     [:div.subfieldset-container
                      [:h3.subfieldset-title (t :gateways.create-title)]
                      [:div.field-pair
                       (if (nil? (:gateway-error state))
                         [:p (t :gateways.confirmation (:gateway-name state))]
                         [:p.general-error (:gateway-error state)])]]

                     [:div.subfieldset-container
                      [:h3.subfieldset-title (t :route-rules.update-default-route-table)]
                      [:div.field-pair
                       (if (nil? (:rt-error state))
                         [:p (t :route-rules.confirmation (:rt-name state))]
                         [:p.general-error (:rt-error state)])]]
                     (log/debug "err  " (println-str state))
                     (for [subnet (:subnet state)]
                       ^{:key (:name subnet)}
                       [:div.subfieldset-container
                        [:h3.subfieldset-title (t :subnets.create-title)]
                        [:div.field-pair
                         (if (nil? (:error subnet))
                           [:p (t :subnets.confirmation (:name subnet))]
                           [:p.general-error (:error subnet)])]])
                     [:input.action {:type  "submit"
                                     :value (t :actions.close)}]]])))
   :handlers {:create-vcn-confirmation-submit
              (fn [db [_ evt]]
                (letk [[view-id] evt]
                  (splice-view! view-id))
                db)}})

(defmethod component :create-vcn [_ params]
  {:config {:container :form-container}
   :state (merge-with merge {:form {:name nil
                                    :cidr "10.0.0.0/16"}
                             :title (t :vcns.create-title)
                             :pending-label (t :vcns.creating-title)
                             :cancelable true
                             :expanded true}
                      params)
   :view (fn [view-id]
           (letk [state (get-state view-id)]
             (fn [view-id]
              (dispatch (events/transact [{:resource [:list :availability-domains]:space :vcn}]))
               [:form {:on-submit
                       (fn [e]
                         (e/dispatch-and-cancel
                           (e/view-event :on-new-vcn view-id (:form @state)) e))}
                [:fieldset
                 [:div.field-pair
                  [:label {:for "name"} (t :labels.name)]
                  [:input {:type "text" :id "name"
                          :value (get-in @state [:form :name])
                          :on-change #(set-state! view-id [:form :name] (get-val %1))}]]
                 [:ul
                  [:li [:label [:input {:type :radio
                                      :name "choice"
                                      :id "auto"
                                      :default-checked true
                                      :on-change #(set-state! view-id {:expanded true
                                                                       :error nil
                                                                       :form {:cidr "10.0.0.0/16"}})}]
                             (t :vcns.auto-create.label)]]
                  [:li [:label [:input {:type :radio
                                      :name "choice"
                                      :id "manual"
                                      :on-change #(set-state! view-id {:expanded false
                                                                       :error nil
                                                                       :form {:cidr nil}})}]
                             (t :vcns.manual-create.label)]]]
                (if (:expanded @state)
                  [create-auto-dialog view-id state]
                  [create-manual-dialog view-id state])]
                (if-let [err (:error @state)]
                  [:p.general-error err])
                [:input.action {:type "submit"
                                :value (t :actions.submit)}]])))
   :handlers {:on-new-vcn
              (fn [db [_ evt]]
                (letk [[params view-id] evt
                       [name cidr] params
                       state (get-state view-id)
                       args {:displayName name :cidrBlock cidr}
                       [resource-transactor] @system/system
                       [vcn-store internet-gateway-store route-table-store] @system/system
                       availability-domains (subscribe [:transacted {:resource [:list :availability-domains]
                                                                     :space :vcn}])
                       ad-list (map #(hash-map :name (:name %) :value (:name %) ) (txr/unwrap @availability-domains))]
                  (set-pending-state! view-id)
                  (if (:expanded @state)
                    (go
                      (let [vcn-res (<! (ds/create-item vcn-store args)) result (atom {:rt-error nil
                                                                                       :gateway-error nil
                                                                                       :subnet []})]
                        (if (m-either/right? vcn-res)
                          (do
                            (when (:expanded @state)
                              (let [gateway-res (<! (ds/create-item
                                                      internet-gateway-store {:vcnId (:id (:body @vcn-res))
                                                                              :displayName (str "Internet Gateway " (:displayName (:body @vcn-res)))
                                                                              :isEnabled true}))]
                                (if (m-either/right? gateway-res)
                                  (let [rt-res (<! (ds/update-item
                                                     route-table-store {:rt-id (:defaultRouteTableId (:body @vcn-res))
                                                                        :routeRules [{:cidrBlock "0.0.0.0/0"
                                                                                      :networkEntityType "INTERNET_GATEWAY"
                                                                                      :networkEntityId (:id (:body @gateway-res))}]}))]
                                    (if (m-either/left? rt-res)
                                      (swap! result assoc :rt-error (:message (:body @rt-res)))))
                                  (swap! result assoc :gateway-error (:message (:body @gateway-res)) :rt-error "")))
                              (let [ch (async/merge (auto-create-subnets vcn-res result ad-list))]
                                (doseq [ad ad-list]
                                    (let [subnet-res (<! (<! ch))]
                                      (if (m-either/left? subnet-res)
                                        (swap! result assoc :subnet  (conj (:subnet @result) {:error (:message (:body  @subnet-res))
                                                                                              :name  (t :subnets.default-name (:name ad))}))
                                        (swap! result assoc :subnet  (conj (:subnet @result) {:error nil
                                                                                              :name  (t :subnets.default-name (:name ad))})))))
                              (push-view! :create-vcn-confirmation (merge @result {:vcn-name (:displayName (:body @vcn-res))
                                                                                   :gateway-name (str (t :gateways.default-name) " " (:displayName (:body @vcn-res)))
                                                                                   :rt-name (:defaultRouteTableId (:body @vcn-res))}))) )
                            (splice-view! view-id)
                            (dispatch (events/transact [{:resource [:list :vcns]
                                                         :space :vcns}])))
                          (set-error-state! view-id (service-error (:body @vcn-res))))))
                    (txr/transact!
                      resource-transactor
                      [{:resource [:create :vcns args]}]
                      {:success (fn []
                                  (dispatch (events/transact [{:resource [:list :vcns]
                                                               :space    :vcns}]))
                                  (splice-view! view-id))
                       :error   (fn [result]
                                  (let [err (txr/any-error result)]
                                    (set-error-state! view-id (adapt-service-error (:body @err)))))}))
                  )
                db)}})
