(ns hg.client.pages.vcn.create-vcn
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require
    [cljs.core.async :refer [<!]]
    [hg.client.components.viewstack :refer [push-view! splice-view!
                                            get-state set-state!
                                            set-pending-state! set-error-state!]]
    [hg.client.components.viewstack.events :as e :refer [get-val]]
    [hg.client.components.viewstack.protocol :refer [component]]
    [hg.client.error :refer [adapt-service-error]]
    [hg.client.events :as events]
    [hg.client.i18n :refer [t]]
    [hg.client.re-frame :refer [subscribe dispatch]]
    [hg.client.system :as system]
    [hg.client.transactor :as txr]
    [plumbing.core :refer-macros [letk]]
    [hg.client.components.views :as v]
    [hg.client.routes :as routes]))

(def availability-domains-spec {:resource [:list :availability-domains] :space :vcn})

(defn create-auto-dialog [view-id state]
  (fn [view-id state]
      (letk [availability-domains (subscribe [:transacted {:resource [:list :availability-domains]
                                                               :space    :vcn}])
             ad-list (map #(hash-map :name (:name %) :value (:name %)) (txr/unwrap @availability-domains))]
            [:div
             [:p (t :vcns.auto-create.details)]
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

             (for [ad (map-indexed (fn [idx item] (merge {:index idx} item)) ad-list)]
                  ^{:key (:name ad)}
                  [:div.subfieldset-container
                   [:h3.subfieldset-title (t :subnets.create-title)]
                   [:div.field-pair
                    [:ul
                     [:li (t :labels.name) ": " (t :subnets.default-name (:name ad))]
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


(defmethod component :create-vcn-confirmation [_ params]
   {:config {:container :form-container}
    :state (merge-with merge
                          {:title (t :vcns.create-title)
                           :cancelable true}
                          params)
    :view (fn [view-id]
            (letk [state @(get-state view-id)]
                (fn [view-id]
                [:div
                 [:form {:on-submit (partial e/dispatch-and-cancel
                                             (e/view-event :create-vcn-confirmation-submit view-id))}
                  [:div.subfieldset-container
                   [:h3.subfieldset-title (t :vcns.create-title)]
                   [:div.field-pair
                    (if (nil? (:vcn-error state))
                      [:p (t :vcns.auto-create.confirmation) (v/link-to (routes/named [:networking :vcn] {:vcn-id (:vcn-id state)}) (:vcn-name state))]
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
                      [:p (t :route-rules.confirmation) (v/link-to (routes/named [:networking :route-table] {:rt-id (:rt-id state) :vcn-id (:vcn-id state)})  (:rt-name state))]
                      [:p.general-error (:rt-error state)])]]
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
    :state (merge-with merge {:form {:name nil :cidr "10.0.0.0/16"}
                              :title  (t :vcns.create-title)
                              :pending-label (t :vcns.auto-create.pending)
                              :pending false
                              :cancelable true
                              :vcn-quick-launch true}
                          params)
    :view (fn [view-id]
            (letk [state (get-state view-id)]
              (fn [view-id]
                  (dispatch (events/transact [availability-domains-spec]))
                (if (:pending @state)
                  [:p]
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
                                             :on-change #(set-state! view-id {:vcn-quick-launch true
                                                                              :error nil
                                                                              :pending-label (t :vcns.auto-create.pending)
                                                                              :form {:cidr "10.0.0.0/16"}})}]
                             (t :vcns.auto-create.label)]]
                       [:li [:label [:input {:type :radio
                                             :name "choice"
                                             :id "manual"
                                             :on-change #(set-state! view-id {:vcn-quick-launch false
                                                                              :error nil
                                                                              :pending-label (t :vcns.create-pending)
                                                                              :form {:cidr nil}})}]
                             (t :vcns.manual-create.label)]]]
                      (if (:vcn-quick-launch @state)
                        [create-auto-dialog view-id state]
                        [create-manual-dialog view-id state])]
                     (if-let [err (:error @state)]
                             [:p.general-error err])
                     [:input.action {:type  "submit"
                                     :value (t :actions.submit)}]]))))
    :handlers {:on-new-vcn
               (fn [db [_ evt]]
                 (letk [[params view-id] evt
                        [name cidr] params
                        state (get-state view-id)
                        args {:displayName name :cidrBlock cidr}
                        [resource-transactor] @system/system
                        availability-domains (subscribe [:transacted availability-domains-spec])
                        ad-list (map #(hash-map :name (:name %) :value (:name %)) (txr/unwrap @availability-domains))]
                       (if (:vcn-quick-launch @state)
                         (do
                           (set-pending-state! view-id {:closable true :close-button true})
                           (txr/transact!
                             resource-transactor
                             [{:resource [:create :vcn-quick-luanch (assoc args :ad-list ad-list)]}]
                             {:success (fn [[[_ result]]]
                                         (splice-view! view-id)
                                         (push-view! :create-vcn-confirmation @result)
                                         (dispatch (events/transact [{:resource [:list :vcns]
                                                                      :space :vcns}])))
                              :error (fn [result]
                                         (let [err (txr/any-error result)]
                                           (set-error-state! view-id (adapt-service-error @err))))}))
                         (do
                           (set-pending-state! view-id)
                           (txr/transact!
                             resource-transactor
                             [{:resource [:create :vcns args]}]
                             {:success (fn []
                                           (dispatch (events/transact [{:resource [:list :vcns]
                                                                        :space :vcns}]))
                                           (splice-view! view-id))
                              :error (fn [result]
                                       (let [err (txr/any-error result)]
                                            (set-error-state! view-id (adapt-service-error @err))))}))))
                 db)}})