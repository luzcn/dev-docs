
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
                   (recur (rest ad) (inc index))))))
