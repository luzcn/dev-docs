(ns hg.client.transactor
  (:require-macros [cljs.core.async.macros :refer [go go-loop alt!]]
                   [cljs.core.match :refer [match]]
                   [plumbing.core :refer [letk]]
                   [reagent.ratom :refer [reaction]])
  (:require [quile.component :as component]
            [re-frame.db :as re-db]
            [cljs.core.async :as async :refer [<! chan >! pub put!]]
            [cats.core :as m]
            [cats.monad.either :as m-either]
            [cats.monad.maybe :as m-maybe]
            [taoensso.timbre :as log]
            [taoensso.encore :refer [merge-keywords]]
            [hg.client.ds :as ds]
            [hg.client.re-frame :as rf]
            [hg.client.events :as events]
            [hg.client.error :as error]
            [hg.swg.core :as swg]
            [hg.client.compartment :as c]))

(def ^:dynamic *ingress-buffer-size* 10)
(def ^:dynamic *egress-buffer-size* 10)

(def default-space :default)

(defn gen-space
  ([resource]
   (gen-space resource {}))
  ([resource opts]
   (letk [key (or (:id resource)
                 (:key resource)
                 (throw (error/error :txr-error
                                     resource
                                     "Could not generate space given resource")))
          [{namespace :default}] opts]
     (merge-keywords [:gen-space namespace (keyword key)]))))

(defprotocol IResourceTransactor
  (gate! [this])
  (ungate! [this])
  (transact! [this actions] [this actions options]))

(defn unwrap [variant]
  "de-nest a data payoad (list) from a list whose first item is a status
  keyword, tolerant of non-wrapped lists"
  (case (first variant)
    (:success :refreshing) (second variant)
    (:pending :unknown :error :pending-related) nil
    variant))


(defn fmap [fun item]
  "apply a function to the data payload of a populated, wrapped list"
  (case (first item)
    (:success :refreshing) (vector (first item) (fun (second item)))
    item))

(defn valued-result? [item]
  (case (first item)
    (:success :refreshing) true
    false))

(defn pending-result [] [:pending])

(defn pending-result? [result]
  (= (first result)
     :pending))

(defn pending-related-result [] [:pending-related])

(defn pending-related-result? [data]
  (= (first data)
     :pending-related))

(defn refreshing-result [data] [:refreshing data])

(defn refreshing-result? [data]
  (= (first data)
     :refreshing))

(defn success-result [data] [:success data])

(defn success-result? [result]
  (= (first result)
     :success))

(defn error-result [err] [:error nil err])

(defn error-result? [data]
  (= (first data)
     :error))

(defn unknown-result [] [:unknown])

(defn unknown-result? [data]
  (= (first data)
     :unknown))

(defn vary-with
  "Takes a variant, which is a transacted datastore vector, and two functions
  for both success and pending transactor states. These functions should return
  valid Re-frame component. Optionally takes a map of functions to deal with
  error, refreshing, as well as a default states in the transacted resource."
  [variant success pending & [additionals]]
  (letk [[{error #(log/error "Unhandled error variant" %)}
          {refreshing nil}
          {default nil}
          {pending-related pending}] (or additionals {})]
    (log/debug "Variant" variant)
    (case (first variant)
      :unknown (pending) ; treat resources without an entry as pending for now
      :pending (pending)
      :pending-related (pending-related nil)
      :success (success (unwrap variant))
      :refreshing (if refreshing
                    (refreshing (unwrap variant))
                    ;; If we are originally an error result, do not run the
                    ;; success-fn when refreshing.
                    (if (error-result? variant)
                      (pending nil)
                      (success (unwrap variant))))
      :error (error (unwrap variant))
      (if default
        (do
          (log/warn "Unhandled variant " variant)
          (default variant))
        (log/error "Unhandled variant " variant)))))

(defrecord ResourceTransactor [a-state
                               a-gate-state
                               latch-ch
                               ingress-ch
                               egress-ch
                               egress-pub
                               identity-client
                               compute-client
                               vcn-client
                               block-storage-client
                               dbaas-client
                               compartment-delegate
                               user-store
                               compartment-store
                               availability-domain-store
                               group-store
                               group-membership-store
                               policy-store
                               instance-store
                               instance-image-store
                               internet-gateway-store
                               ipsec-connection-store
                               ipsec-connection-device-config-store
                               ipsec-connection-device-status-store
                               instance-shapes-store
                               vnic-attachment-store
                               vnic-store
                               volume-attachment-store
                               vcn-store
                               drg-store
                               drg-attachment-store
                               object-store
                               bucket-store
                               volume-store
                               subnet-store
                               api-key-store
                               route-table-store
                               cpe-store
                               load-balancer-store
                               load-balancer-listener-store
                               load-balancer-backend-set-store
                               load-balancer-backend-store
                               db-system-store
                               db-system-shape-store]

  component/Lifecycle
  (start [this]
    (let [next (as-> this $
                 (assoc $ :latch-ch (chan))
                 (assoc $ :ingress-ch (chan (async/buffer *ingress-buffer-size*)))
                 (assoc $ :egress-ch (chan (async/sliding-buffer *egress-buffer-size*)))
                 (assoc $ :egress-pub (pub (:egress-ch $) :type)))

          process-action (fn [{:keys [space resource]}]
                           (log/debug "Processing action" resource)
                           ;; TODO: open/closed principle...
                           (match
                             resource

                             [:list :availability-domains]
                             (ds/list-items availability-domain-store)

                             [:list :compartments]
                             (ds/list-items compartment-store)

                             [:get :compartments params]
                             (ds/get-item compartment-store params)

                             [:list :ipsec-connections params]
                             (ds/list-items ipsec-connection-store params)

                             [:get :ipsec-connection-device-status params]
                             (ds/get-item ipsec-connection-device-status-store params)

                             [:get :ipsec-connection-device-config params]
                             (ds/get-item ipsec-connection-device-config-store params)

                             [:list :users]
                             (ds/list-items user-store)

                             [:get :users params]
                             (ds/get-item user-store params)

                             [:list :user-api-keys params]
                             (ds/list-items api-key-store params)

                             [:list :groups]
                             (ds/list-items group-store)

                             [:get :groups params]
                             (ds/get-item group-store params)

                             [:list :group-memberships params]
                             (ds/list-items group-membership-store params)

                             [:list :policies]
                             (ds/list-items policy-store)

                             [:create :policies params]
                             (ds/create-item policy-store params)

                             [:get :policies params]
                             (ds/get-item policy-store params)

                             [:update :policies params]
                             (ds/update-item policy-store params)

                             [:list :instances]
                             (ds/list-items instance-store)

                             [:get :instances params]
                             (ds/get-item instance-store params)

                             [:list :instance-images]
                             (ds/list-items instance-image-store)

                             [:get :instance-images params]
                             (ds/get-item instance-image-store params)

                             [:create :instance-images params]
                             (ds/create-item instance-image-store params)

                             [:update :instance-images params]
                             (ds/update-item instance-image-store params)

                             [:list :instance-shapes]
                             (ds/list-items instance-shapes-store)

                             [:list :vnic-attachments params]
                             (ds/list-items vnic-attachment-store params)

                             [:get :vnic params]
                             (ds/get-item vnic-store params)

                             [:list :volume-attachments]
                             (ds/list-items volume-attachment-store)

                             [:list :volume-attachments params]
                             (ds/list-items volume-attachment-store params)

                             [:get :volume-attachments params]
                             (ds/get-item volume-attachment-store params)

                             [:list :drgs]
                             (ds/list-items drg-store)

                             [:get :drg params]
                             (ds/get-item drg-store params)

                             [:list :buckets]
                             (ds/list-items bucket-store)

                             [:create :buckets params]
                             (ds/create-item bucket-store params)

                             [:delete :buckets params]
                             (ds/delete-item bucket-store params)

                             [:list :objects params]
                             (ds/list-items object-store params)

                             [:create :volumes params]
                             (ds/create-item volume-store params)

                             [:list :volumes]
                             (ds/list-items volume-store)

                             [:list :volumes params]
                             (ds/list-items volume-store params)

                             [:get :volumes params]
                             (ds/get-item volume-store params)

                             [:create :route-rule params]
                             (ds/create-item route-table-store params)

                             [:update :route-rule params]
                             (go
                               (letk [[rt-id
                                       route-rules] params
                                      table (as-> route-table-store $
                                              (ds/get-item $ {:rt-id rt-id})
                                              (<! $))]
                                 (if (m-either/right? table)
                                   (as-> route-table-store $
                                     (ds/update-item $ (-> params
                                                           (dissoc :route-rules)
                                                           (merge {:rt-id rt-id
                                                                   :routeRules (concat route-rules
                                                                                       (:routeRules @table))})))
                                     (<! $))
                                   table)))

                             [:delete :route-rule params]
                             (go
                               (letk [[rt-id
                                       route-rule] params
                                      table (as-> route-table-store $
                                              (ds/get-item $ {:rt-id rt-id})
                                              (<! $))]
                                 (if (m-either/right? table)
                                   (as-> route-table-store $
                                     (ds/update-item $ {:rt-id rt-id
                                                        :routeRules
                                                        (filterv (partial not= route-rule)
                                                                 (:routeRules @table))})
                                     (<! $))
                                   table)))

                             [:list :cpes]
                             (ds/list-items cpe-store)

                             [:get :cpe params]
                             (ds/get-item cpe-store params)

                             [:list :db-systems]
                             (ds/list-items db-system-store)

                             [:list :db-systems params]
                             (ds/list-items db-system-store params)

                             [:create :db-systems params]
                             (ds/create-item db-system-store params)

                             [:delete :db-systems params]
                             (ds/delete-item db-system-store params)

                             [:get :db-systems params]
                             (ds/get-item db-system-store params)

                             [:list :db-system-shapes params]
                             (ds/list-items db-system-shape-store params)

                             [:list :db-versions]
                             (go (as-> dbaas-client $
                                   (swg/new-request $ :ListDbVersions)
                                   (assoc-in $ [:query-params :compartmentId] (c/get-compartment compartment-delegate))
                                   (swg/execute! $)
                                   (<! $)
                                   (m/fmap :body $)))

                             [:list :load-balancers]
                             (ds/list-items load-balancer-store)

                             [:get :load-balancer params]
                             (ds/get-item load-balancer-store params)

                             [:delete :load-balancer params]
                             (ds/delete-item load-balancer-store params)

                             [:create :lb-listener params]
                             (ds/create-item load-balancer-listener-store params)

                             [:delete :lb-listener params]
                             (ds/delete-item load-balancer-listener-store params)

                             [:get :lb-backend-set params]
                             (ds/get-item load-balancer-backend-set-store params)

                             [:create :lb-backend-set params]
                             (ds/create-item load-balancer-backend-set-store params)

                             [:update :lb-backend-set params]
                             (ds/update-item load-balancer-backend-set-store params)

                             [:delete :lb-backend-set params]
                             (ds/delete-item load-balancer-backend-set-store params)

                             [:list :lb-backends params]
                             (ds/list-items load-balancer-backend-store params)

                             [:create :vcn-quick-luanch params]
                             ;; TODO need to get rid of the atom
                             (go
                               (let [vcn-res (<! (ds/create-item vcn-store {:displayName (:displayName params) :cidrBlock (:cidrBlock params)}))
                                     result (atom {:rt-error "" :gateway-error "" :subnet []})
                                     ad-list (:ad-list params)]
                                 (if (m-either/right? vcn-res)
                                   (do
                                     (swap! result assoc :vcn-name (:displayName (:body @vcn-res)) :vcn-id (:id (:body @vcn-res)))
                                     (let [gateway-res (<! (ds/create-item
                                                           internet-gateway-store
                                                           {:vcnId (:id (:body @vcn-res))
                                                            :displayName (str "Internet Gateway " (:displayName (:body @vcn-res)))
                                                            :isEnabled true}))]
                                     (if (m-either/right? gateway-res)
                                       (do
                                         (swap! result assoc :gateway-name (str "Internet Gateway " (:displayName (:body @vcn-res)))
                                                :gateway-error nil
                                                :gateway-id (:id (:body @vcn-res)))
                                         (let [rt-res (<! (ds/update-item
                                                          route-table-store
                                                          {:rt-id (:defaultRouteTableId (:body @vcn-res))
                                                           :routeRules [{:cidrBlock "0.0.0.0/0"
                                                                         :networkEntityType "INTERNET_GATEWAY"
                                                                         :networkEntityId (:id (:body @gateway-res))}]}))]
                                         (if (m-either/right? rt-res)
                                           (swap! result assoc :rt-name (:defaultRouteTableId (:body @vcn-res))
                                                  :rt-error nil
                                                  :rt-id (:defaultRouteTableId (:body @vcn-res)))
                                           (swap! result assoc :rt-error (:message (:body @rt-res))))))
                                       (swap! result assoc :gateway-error (:message (:body @gateway-res)))))
                                     (doseq [ad (map-indexed (fn [idx item] (merge {:index idx} item)) ad-list)]
                                       (let [subnet-res (<! (ds/create-item subnet-store
                                                                            {:vcnId (:id (:body @vcn-res))
                                                                             :cidrBlock (str "10.0." (:index ad) ".0/24")
                                                                             :displayName (str "Subnet " (:name ad))
                                                                             :routeTableId (:defaultRouteTableId (:body @vcn-res))
                                                                             :availabilityDomain (:value ad)
                                                                             :compartmentId (:compartmentId (:body @vcn-res))}))]
                                         (if (m-either/right? subnet-res)
                                           (swap! result assoc :subnet (conj (:subnet @result) {:error nil :name  (str "Subnet " (:name ad)) :id (:id (:body @subnet-res))}))
                                           (swap! result assoc :subnet (conj (:subnet @result) {:error (:message (:body @subnet-res)) :name  (str "Subnet " (:name ad))}))))))
                                   (swap! result assoc :vcn-error (:message (:body @vcn-res))))
                                 result))

                             [:create :lb-backend params]
                             (ds/create-item load-balancer-backend-store params)))

          verb-map {:list ds/list-items
                    :create ds/create-item
                    :get ds/get-item
                    :update ds/update-item
                    :delete ds/delete-item}

          ;; eventually break this into a multimethod so each datastore can register itself
          store-map {:internet-gateways internet-gateway-store
                     :drg-attachments drg-attachment-store
                     :subnets subnet-store
                     :vcn-route-tables route-table-store
                     :vcn-subnets subnet-store
                     :vcns vcn-store}

          look-up (fn [{verb-key 0 store-key 1 params 2}]
                    (if-let [store (store-key store-map)]
                      (if-let [verb (verb-key verb-map)]
                        (verb store params))))]

      (go-loop []
        (let [op (alt!
                   (:ingress-ch next) ([v] v)
                   (:latch-ch next) :quit)]
          (if-not (= :quit op)
            (let [[actions options res-ch] op
                  {:keys [success error]} options]
              (go
                (as-> actions $
                  (map (fn [action]
                         (go
                           (let [res (<! (or (look-up (:resource action))
                                             (process-action action)))
                                 {:keys [space resource]} action
                                 space (or space default-space)
                                 wrap (if (m-either/right? res)
                                        success-result
                                        error-result)]
                             ;; Want to make the data available to the UI as it comes in
                             ;; so we swap the results as they come in.
                             (swap! a-state assoc-in [space resource] (wrap @res))
                             (vector action res)))) $)
                  (async/merge $)
                  (async/take (count actions) $)
                  (async/into [] $)
                  (let [res (<! $)]
                    ;; Inform callbacks
                    (if (some (comp m-either/left? second) res)
                      (do
                        (log/trace "Calling provided error callback")
                        (error res))
                      (do
                        (log/trace "Calling provided success callback")
                        (success res)))

                    (let [msg {:type :result
                               :actions actions
                               :options options
                               :result res}]

                      ;; Notify anyone interested in exactly this result
                      (>! res-ch msg)

                      ;; Lastly, shove result onto egress chan for easier debugging
                      (>! (:egress-ch next) msg)))))
              (recur)))))
      next))

  (stop [this]
    (async/put! (:latch-ch this) true)
    (async/close! (:latch-ch this))
    (async/close! (:egress-ch this))
    (-> this
        (assoc :latch-ch nil)
        (assoc :ingress-ch nil)
        (assoc :egress-ch nil)
        (assoc :egress-pub nil)))

  IResourceTransactor
  (gate! [this]
    (swap! a-gate-state assoc :gated? true)
    nil)

  (ungate! [this]
    (let [gated-requests (:gated-requests @a-gate-state)]
      (log/trace "Gated transactor requests" gated-requests)
      (reset! a-gate-state {:gated? false :gated-requests []})
      (go-loop [remaining gated-requests]
        (if (seq remaining)
          (let [job (first remaining)]
            (>! (:ingress-ch this) job)
            (recur (rest remaining)))))
      nil))

  (transact! [this actions]
    (transact! this actions {}))

  (transact! [this actions options]
    (let [{:keys [success error]} options
          success (or success identity)
          error (or error identity)
          res-ch (async/promise-chan)
          gated? (get @a-gate-state :gated?)]
      (doseq [{:keys [space resource]} actions]
        (let [space (or space default-space)
              [op entity params] resource]
          (log/trace "Making assertions on resource spec: " resource)
          (assert op "Operation is defined/present in action spec")
          (assert (#{:get :list :delete :update :create} op) "valid operation")
          (assert space "space present in action spec")
          (assert resource "resource present in action spec")

          ;; Let observers of the state atom know that we're grabbing the item
          (swap! a-state update-in [space resource] (fn [v]
                                                      (if-let [existing (unwrap v)]
                                                        (refreshing-result existing)
                                                        (pending-result))))))

      (let [job [actions
                 {:success success
                  :error error}
                 res-ch]]

        (log/trace "Checking gate state" gated? job)
        (if gated?
          (swap! a-gate-state update :gated-requests conj job)
          (put! (:ingress-ch this) job))

        res-ch))))


(defn new-resource-transactor [state-atom]
  (map->ResourceTransactor {:a-state state-atom
                            :a-gate-state (atom {:gated? true
                                                 :gated-requests []})}))

(defn any-error [transactor-result]
  (some (fn [[_ e]]
          (and (m-either/left? e)
               e)) transactor-result))

(defn one!
  "Returns a subscription to the provided resource, and
  dispatches a transact request to fetch that resources.
  This is useful for the typical case, where you're
  interested in one resource and its result."
  ([spec]
   (one! spec {}))

  ([spec opts]
   (letk [db re-db/app-db
          [resource
           {space default-space}] spec
          [{xform identity}] opts
          opts (dissoc opts :xform)]
     (rf/dispatch (events/transact [spec] opts))
     (reaction
       (->> (get-in @db
                    [:experimental :workspace space resource]
                    (unknown-result))
            (xform))))))


;; FIXME - return a ratom that we swap values into, after watching provided
;; ratoms for changes
(defn related! [spec-fun opts]
  (letk [[resources {xform (fn [_ item] item)}] opts
         opts (dissoc opts :resources :xform)
         db re-db/app-db
         local-state (atom {:last-ctx nil
                            :current-ctx {}
                            :last-spec nil
                            :current-spec {}})]
    (reaction
     (let [results (for [[resource-ratom xform] resources]
                     (do
                       (log/trace "Evaluating related resources" resource-ratom)
                       (xform (deref resource-ratom))))]
       (if (every? #(or (m-either/right? %)         ; Check to see if all results return
                        (m-maybe/just? %)) results) ; right/just values - this indicated "readiness"
         (letk [ctx (reduce (fn [s v] (merge s (deref v))) ; merge all maps into one ctx map
                           {}
                           results)
               spec (spec-fun ctx) ; calculated spec, given all the related resources
               [resource
                {space default-space}] spec
                [last-spec
                 current-spec
                 last-ctx
                 current-ctx] @local-state]
           (log/debug "Computed context and spec" ctx spec)
           (when (and (not= last-ctx ctx)
                      (not= last-spec spec))
             (log/debug "Novel values detected; dispatching transact event and stashing values")
             (reset! local-state {:last-ctx current-ctx
                                  :current-ctx ctx
                                  :last-spec current-spec
                                  :current-spec spec})
             (rf/dispatch (events/transact [spec] opts)))
           (->> (get-in @db
                       [:experimental :workspace space resource]
                       (unknown-result))
                (xform ctx)))
         (do
           (log/debug "Related resources aren't ready")
           (pending-related-result)))))))
