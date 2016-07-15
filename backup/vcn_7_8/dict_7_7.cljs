{:en {:header {:brand-name "Bare Metal IaaS"
               :menu "menu"
               :compartment-menu {:compartment "Compartment"
                                  :tenancy "Tenancy"}
                :username-menu {:sign-out "Sign Out"
                                :access "User Settings"}}

  :footer {:tenancy-id "Tenancy ID"
           :about "About Oracle"
           :contact "Contact Us"
           :legal "Legal Notices"
           :terms "Terms of Use"
           :privacy "Privacy"
           :copyright "© Copyright 2016 Oracle. All rights reserved."}

  :dialogs {:confirm {:title "Confirm"
                      :default "Are you sure?"
                      :delete "Are you sure you want to delete this %s?"
                      :groups {:delete "Are you sure you want to delete this group?"
                               :remove-member "Are you sure you want to remove this member?"}
                      :policies {:delete "Are you sure you want to delete this policy?"
                                 :delete-statement "Are you sure you want to delete this policy statement?"}
                      :user {:delete "Are you sure you want to delete this user?"
                             :reset-password "This will create a new one-time password for the user."
                             :reset-password-result "New password: %s"}
                      :user-api-key {:delete "Are you sure you want to delete this API key?"}
                      :volumes {:delete "Are you sure you want to terminate this volume?"}}
            :errors {:title "There was an unexpected error"
                     :default "An unexpected error occurred."
                     :network "A network error occurred."
                     :gateway "A gateway error occurred."
                     :service "A service error occurred."
                     :service-error {:title "Error Making the Request"
                                     :description "Message from the service: "}
                     :compartments {:list "The compartments could not be loaded."
                                    :create "The compartment could not be created."}
                     :groups {:create "The group could not be created."
                              :delete "The group could not be deleted."
                              :add "The user could not be added to this group."
                              :remove "The user could not be removed from this group."}
                     :instances {:new-instance "The instance could not be launched."
                                 :new-volume "The volume could not be attached."
                                 :detach-volume "The volume could not be detached."}
                     :db-systems {:new-instance "The database system could not be launched."
                                  :terminate-db-system "The database system could not be terminated."}
                     :policies {:list "The policies could not be loaded."
                                :create "The policy could not be created."
                                :create-statement "The policy statement could not be created."
                                :delete "The policy could not be deleted."
                                :delete-statement "The policy statement could not be deleted."}
                     :user {:list "The users could not be loaded."
                            :create "The user could not be created."
                            :read "The user could not be loaded."
                            :update "Your change could not be saved."
                            :delete "The user could not be deleted."
                            :change-password "Your password could not be changed."
                            :reset-password "Your password could not be reset."
                            :add-api-key "This API key could not be added."
                            :delete-api-key "This API key could not be deleted."}
                     :vcn {:list "The VCNs could not be loaded."
                           :create-gateway "The gateway could not be created."
                           :create-route-rule "There was an unexpected error creating the route rule."
                           :create-route-table "There was an unexpected error creating the route table."
                           :create-subnet "The subnet could not be created."
                           :read "The VCN could not be loaded."
                           :delete "The VCN could not be deleted."
                           :delete-gateway "The gateway could not be deleted."
                           :delete-route-rule "There was an unexpected error deleting this route rule."
                           :delete-route-table "There was an unexpected error deleting this route table."
                           :delete-subnet "The subnet could not be deleted."}
                     :vcns {:create "The VCN could not be created."}
                     :volumes {:create "The volume could not be created."
                               :delete "The volume could not be terminated."}
                     :buckets {:create "The bucket could not be created."
                               :delete "The bucket could not be deleted."}
                     :drg-attachments {:create "The gateway could not be attached to this cloud network."
                                       :delete "The gateway could not be detached from this cloud network."}}
            :session-expired {:title "Your session has expired"
                              :description "You will be redirected to the sign-in page."}}

  :actions {:add "Add"
            :adding "Adding"
            :attach_s "Attach %s"
            :attaching_s "Attaching %s"
            :cancel "Cancel"
            :close "Close"
            :create "Create"
            :create-s "Create %s"
            :creating "Creating"
            :detach "Detach"
            :delete "Delete"
            :new-s "New %s"
            :remove "Remove"
            :terminate "Terminate"
            :iscsi-info "iSCSI Information"
            :ok "OK"
            :plus "+"
            :power-off "Power Off"
            :power-on "Power On"
            :power-cycle "Power Cycle"
            :submit "Submit"}

  :labels {:attached-instance "Attached Instance"
           :availabilityDomain "Availability Domain" ;; deprecated
           :availability-domain "Availability Domain"
           :attachment-id "Attachment ID"
           :by "By"
           :cidrBlock "CIDR Block" ;; deprecated
           :cidr-block "CIDR Block"
           :compartment "Compartment"
           :created "Created"
           :date-attached "Date Attached"
           :description "Description"
           :displayName "Name" ;; deprecated
           :display-name "Name"
           :dynamic-gateway "Dynamic Gateway"
           :etag "eTag"
           :id "OCID"
           :image "Image"
           :internet-gateway "Internet Gateway"
           :ipAddress "IP Address"
           :keys "Keys"
           :launched "Launched"
           :load-balancer "Load Balancer"
           :metadata "Metadata"
           :modified "Modified"
           :name "Name"
           :namespace "Namespace"
           :network-entity-id "Network Entity ID"
           :network-entity-type "Network Entity Type"
           :not-applicable "Not applicable"
           :optional "Optional"
           :protocol "Protocol"
           :policy "Policy"
           :port "Port"
           :region "Region"
           :route-table "Route Table"
           :rules "Rules"
           :shape "Shape"
           :status "Status"
           :target "Target"
           :virtual-router-mac "Virtual Router MAC Address"}

  :status {:available "Available"
           :provisioning "Provisioning..."
           :terminating "Terminating..."
           :terminated "Terminated"
           :attaching "Attaching..."
           :attached "Attached"
           :detaching "Detaching..."
           :detached "Detached"
           :down "Down"
           :down_for_maintenance "Down for Maintenance"
           :faulty "Faulty"
           :stopping "Stopping..."
           :stopped "Stopped"
           :running "Running"
           :unknown "Unknown"
           :enabled "Enabled"
           :disabled "Disabled"
           :up "Up"}

  :navigation {:home {:main "Home"}
               :identity {:main "Identity"
                          :users {:main "Users"}
                          :groups {:main "Groups"}
                          :policies {:main "Policies"}
                          :compartments {:main "Compartments"}}
               :databases {:main "Databases"
                           :db-systems {:main "DB Systems"}}
               :compute {:main "Compute"
                         :instances {:main "Instances"}
                         :snapshots {:main "Snapshots"}}
               :storage {:main "Storage"
                         :volumes {:main "Block Volumes"}
                         :volume-backups {:main "Backups"}
                         :objects {:main "Object Storage"}}
               :networking {:main "Networking"
                            :vcns {:main "Cloud Networks"}
                            :drgs {:main "Dynamic Gateways"}
                            :cpes {:main "Customer Equipment"}
                            :load-balancers {:main "Load Balancers"}}}

  :compartments {:list-count-title "compartments"
                 :no-compartments "There are no compartments."
                 :create-compartment "Create Compartment"}

  :cpes {:header "Customer Equipment (CPEs)"
         :list-count-title "customer equipment"
         :no-cpes "There is no customer equipment."
         :create-cpe "Create Customer Equipment"
         :confirm-delete "Are you sure you want to delete this customer equipment?"
         :displayName "Name"
         :ipAddress "IP Address"
         :timeCreated "Created"
         :badge-label "CPE"}

  :drgs {:header "Dynamic Gateways (DRGs)"
         :list-count-title "dynamic gateways"
         :tunnel-information "Tunnel Information"
         :tunnel-information-explanation "To complete the IPsec connection, a network administrator must configure your on-premise router with this information for the two IPsec tunnels:"
         :tunnel-information-error "There was an unexpected error loading tunnel information."
         :shared-secret "Shared Secret: "
         :ip-address "IP Address: "
         :state "State: "
         :ipsec-connection-device-status "IPsec Connection Status"
         :no-drgs "There are no dynamic gateways."
         :create-drg "Create Dynamic Gateway"
         :displayName "Name"
         :confirm-delete "Are you sure you want to delete the dynamic gateway %s?"
         :timeCreated "Created"
         :badge-label "DRG"
         :tunnel-badge-label "T"}

  :attached-vcns {:header "Cloud Networks (VCNs)"
                  :list-count-title "attached cloud networks"
                  :no-attached-vcns "There are no attached cloud networks"
                  :create-attached-vcn "Attach to Cloud Network"
                  :vcnId "Cloud Network"
                  :drgAttachmentId "Attachment ID"
                  :confirm-delete "Are you sure you want to detach this dynamic gateway from this cloud network?"}

  :attached-drgs {:header "Dynamic Gateways (DRGs)"
                  :singular "dynamic gateway"
                  :plural "dynamic gateways"
                  :list-count-title "dynamic gateways"
                  :no-attached-drgs "There are no associated dynamic gateways"
                  :confirm-delete "Are you sure you want to detach this dynamic gateway from this cloud network?"}

  :gateways {:header "Internet Gateways"
             :singular "internet gateway"
             :list-count-title "internet gateways"
             :no-gateways "There are no internet gateways in this cloud network."
             :create-title "Create Internet Gateway"
             :creating-title "Creating Internet Gateway"
             :badge-label "IG"
             :default-name "Internet Gateway"
             :confirmation "The %s was created"}

  :group-members {:list-count-title "members"
                  :no-group-members "There are no members in this group."
                  :create-group-member "Add User to Group"}

  :groups {:list-count-title "groups"
           :header "Groups"
           :no-groups "No groups."
           :badge-label "G"
           :create-group "Create Group"}

  :instances {:header "Instances"
              :list-count-title "instances"
              :no-instances "There are no instances."
              :create-instance "Launch Instance"
              :badge-label "I"}

  :db-systems {:header "DB Systems"
               :list-count-title "database systems"
               :no-db-systems "There are no database systems"
               :create-db-system "Launch System"
               :create-pending "Creating DB System"
               :displayName "Display Name"
               :shape "Shape"
               :cpuCoreCount "CPU Core Count"
               :availabilityDomain "Availability Domain"
               :vcnId "Cloud Network"
               :subnetId "Subnet"
               :sshKeyPairName "SSH Key Pair Name"
               :sshPublicKey "SSH Public Key"
               :overlayIp "Overlay IP"
               :publicIp "Public IP"
               :timeCreated "Created"
               :timeModified "Last Modified"
               :compartment "Compartment"}

  :ipsec-connections {:no-ipsec-connections "No IPsec Connections."
                      :header "IPsec Connections"
                      :create-ipsec-connection "Create IPsec Connection"
                      :static-routes "Static Routes"
                      :list-count-title "IPsec connections"
                      :cpe "Customer Equipment"
                      :confirm-delete "Are you sure you want to delete the IPsec connection %s?"}

  :buckets {:name "Bucket"
            :list-count-title "buckets"
            :bucket-name "Bucket Name"
            :create-title "Create Bucket"
            :no-buckets "No buckets."
            :confirm-delete "Delete Bucket?"
            :badge-label "B"}

  :objects {:name "Object"
            :list-count-title "objects"
            :no-object "No objects."
            :create-title "Create Object"
            :confirm-delete "Delete Object?"
            :badge-label "O"}

  :policies {:list-count-title "policies"
             :no-policies "There are no policies."
             :create-title "Create Policy"
             :creating-title "Creating Policy"}

  :policy-statements {:list-count-title "statements"
                      :header "Statements"
                      :no-policy-statements "No statements."
                      :create-policy-statement "Add Statement"
                      :create-title "Add Policy Statement"
                      :creating-title "Adding Policy Statements"
                      :statements "Statement"
                      :statement-n "Statement %s"
                      :add-statement "Add Statement"}

  :route-tables {:singular "route table"
                 :plural "route tables"
                 :header "Route Tables"
                 :list-count-title "route tables"
                 :no-route-tables "There are no route tables in this cloud network."
                 :create-title "Create Route Table"
                 :creating-title "Creating Route Table"
                 :route-rule-n "Route Rule %s"
                 :confirm-delete-s "Are you sure you want to delete the route table %s?"
                 :badge-label "RT"}

  :route-rules {:singular "route rule"
                :plural "route rules"
                :header "Route Rules"
                :list-count-title "rules"
                :no-route-table-rules "There are no route rules for this route table."
                :types {:vnic "VNIC"
                        :internet_gateway "Internet Gateway"
                        :dynamically_routing_gateway "Dynamic Gateway"
                        :dynamic_gateway "Dynamic Gateway"}
                :update-default-route-table "Update Default Route Table"
                :default-route-table-rule "Add Route Rule: 0.0.0.0/0 %s"
                :confirmation "The %s was updated"}

  :load-balancers {:header "Load Balancers"
                   :list-count-title "load balancers"
                   :no-load-balancers "There are no load balancers."
                   :create-title "Create Load Balancer"
                   :create-pending "Creating load balancer"
                   :confirm-delete "Are you sure you want to delete the load balancer %s?"
                   :internalVip "Internal VIP"
                   :externalVip "Public VIP"
                   :badge-label "LB"}

  :lb-backend-sets {:header "Backend Sets"
                    :list-count-title "backend sets"
                    :no-lb-backend-sets "There are no backend sets for this load balancer."
                    :create-lb-backend-set "Create Backend Set"
                    :create-pending "Creating Backend Set"
                    :confirm-delete "Are you sure you want to delete the backend set named %s?"
                    :badge-label "BS"
                    :backends "Backends"
                    :ip-hash "IP Hash"
                    :round-robin "Round Robin"
                    :health-check "Health Check"
                    :type "Type"
                    :urlPath "URL Path (URI)"
                    :retCode "Status Code"
                    :timeout "Timeout In MS"
                    :interval "Number of Retries"
                    :responseBodyRegex "Response Body Regex"
                    :script "Health Check Script"
                    :least-connected "Least Connected"
                    :weighted "Weighted"
                    :policy "Policy"}

  :lb-backends {:header "Backends"
                :list-count-title "backends"
                :no-lb-backends "There are no backends for this backend set."
                :create-title "Create Backend"
                :create-pending "Creating Backend"
                :confirm-delete "Are you sure you want to delete the backend %s?"
                :badge-label "B"}

  :lb-listeners {:header "Listeners"
                 :list-count-title "listeners"
                 :no-lb-listeners "There are no listeners for this load balancer."
                 :create-title "Create Listener"
                 :create-pending "Creating listener"
                 :confirm-delete "Are you sure you want to delete the listener named %s?"
                 :badge-label "L"
                 :vip "VIP"
                 :vipId "Load Balancer VIP"
                 :protocol "Protocol"
                 :port "Port"
                 :defaultBackendSetId "Backend Set"
                 :loading-backend-sets "Loading backend sets..."
                 :loading-vips "Loading VIPs..."}

  :lb-certificates {:header "Certificates"
                    :list-count-title "certificates"
                    :no-lb-certificates "There are no certificates for this load balancer."
                    :create-lb-certificate "Add Certificate"
                    :badge-label "C"
                    :public-cert "Public Cert"}

  :snapshots {:header "Snapshots"
              :badge-label "S"
              :original-image "Original Image"
              :possible-machines "Possible Machines"
              :shape "Shape"}

  :subnets {:singular "subnet"
            :plural "subnets"
            :header "Subnets"
            :badge-label "S"
            :list-count-title "subnets"
            :no-subnets "There are no subnets in this cloud network."
            :create-title "Create Subnet"
            :creating-title "Creating Subnet"
            :default-name "Subnet %s"
            :default-security-list "Security List: Default Security List"
            :default-route-table "Route Table: Default Route Table"
            :default-cidr "CIDR: 10.0.%s.0/24; 10.0.%s.0 - 10.0.%s.255 (256 IP addresses)"
            :confirmation "The %s was created"}

  :users {:header "Users"
          :list-count-title "users"
          :username "Username"
          :create-user "Create User"
          :create-pending "Creating User..."
          :no-users "There are no users."}

  :user-api-keys {:name "Public Key"
                  :header "API Keys"
                  :list-count-title "API keys"
                  :no-user-api-keys "No API keys."
                  :public-key "Public Key"}

  :vcns {:header "Cloud Networks (VCNs)"
         :list-count-title "cloud networks"
         :no-vcns "There are no cloud networks."
         :no-matches "No cloud networks match your search."
         :create-title "Create Cloud Network"
         :creating-title "Creating Cloud Network"
         :confirm-delete "Are you sure you want to delete this VCN?"
         :badge-label "VCN"
         :defaultRouteTable "Default Route Table"
         :create-pending "Creating VCN..."
         :auto-create {:label "Create VCN plus related resources"
                       :details "Automatically creates a working cloud network in the current compartment by using these actions:"
                       :default-cidrBlock "CIDR: 10.0.0.0/16"
                       :confirmation "The cloud network %s was created."
                       :pending "Creating VCN and related resources"
                       :name-label "Auto-generated if you don’t provide one"}
         :manual-create {:label "Create VCN only"
                         :details "Creates only a VCN. You’ll still need to set up at least one subnet, gateway, and route rule to have a working cloud network."}}

  :volumes {:header "Block Volumes"
            :list-count-title "block volumes"
            :no-volumes "There are no block volumes"
            :create-volume "Create Block Volume"
            :create-pending "Creating Block Volume"
            :attachmentType "Attachment Type"
            :attachmentState "Attachment State"
            :badge-label "BV"
            :size "Size"
            :item-load-error "Volume details could not be loaded."}

  :volume-attachments {:header "Volume Attachments"
                       :list-count-title "volumes"
                       :no-volume-attachments "No volumes are attached to this instance."
                       :create-volume-attachment "Attach Volume"}

  :volume-backups {:header "Backups"
                   :list-count-title "volume backups"
                   :no-volumes "There are no backups"
                   :create-title "Create Backup"
                   :badge-label "B"
                   :size "Size"
                   :item-load-error "Backup details could not be loaded."}


  :panels {:errors {:404 {:header "404 – Page Not Found"
                          :explaination "There is no page at this URL."}}

           :group-detail {:meta-labels {:description "Description"
                                        :timeCreated "Created"
                                        :timeModified "Last Modified"}}

           :group-members {:header "Members"}

           :policies {:header "Policies"}

           :compartments {:header "Compartments"
                          :list {:compartmentId "Parent Compartment"
                                 :description "Description"
                                 :name "Name"
                                 :state "State"
                                 :friendly-timeCreated "Created"
                                 :friendly-timeModified "Last Modified"}}

           :home {:header "Welcome!"
                  :intro-sentence-part-1 "Before you launch your first instance, you’ll need to set up some basic access controls and a cloud network as described in "
                  :intro-sentence-part-2 "Getting Started with Oracle Bare Metal IaaS." ; This is the text in a link.
                  :dive-in "If you’d rather dive right in, click the service tabs on the upper right:"
                  :identity-sentence " to manage users, groups, policies, and compartments to control access."
                  :compute-sentence " to launch, start, stop, and terminate instances."
                  :networking-sentence " to create and manage the network components for your cloud resources."
                  :storage-sentence " to create block volumes to provide persistent data storage for your instances."
                  :help-sentence-part-1 "For details about each service, see "
                  :help-sentence-part-2 "Oracle Bare Metal IaaS Help."}

           :instance-detail {:iscsi-info {:title "iSCSI Information"
                                          :ip-port "IP Address and Port"
                                          :iqn "Volume IQN"
                                          :username "CHAP Username"
                                          :secret "CHAP Password"
                                          :persistent-conn "Configure iSCSI to maintain a persistent connection between the instance and volume between reboots:"
                                          :authenticate "Authenticate the iSCSI connection:"
                                          :log-in "Log on to iSCSI:"}
                             :meta-labels {:timeCreated "Created"
                                           :availabilityDomain "Availability Domain"
                                           :displayName "Display Name"
                                           :state "State"
                                           :region "Region"
                                           :public-ip "Public IP Address"
                                           :overlay-ip "Overlay IP Address"
                                           :shape "Shape"
                                           :image "Image"
                                           :compartment "Compartment"}}

           :user-detail {:meta-labels {:name "Name"
                                       :description "Description"
                                       :timeCreated "Created"
                                       :timeModified "Modified"}
                         :api-keys {:header "API Keys"}
                         :actions {:reset-password "Create/Reset Password"
                                   :disable "Disable"
                                   :delete "Delete"}}

           :policy-detail {:meta-labels {:compartment "Compartment"
                                         :description "Description"
                                         :timeCreated "Created"
                                         :timeModified "Last Modified"}}

           :volumes {:header "Block Volumes"}}

  :forms {:buttons {:create "Create"}
          :new-volume {:title "Create Block Volume"
                       :default-values {:type {:ssd "SSD"
                                               :standard "Standard"}}
                       :labels {:domain "Domain"
                                :displayName "Name"
                                :surrogate-key "Surrogate Key"
                                :type "Type"
                                :size "Size"}
                       :buttons {:submit "Create"}}

          :new-compartment {:title "Create Compartment"
                            :labels {:name "Name"
                                     :description "Description"}
                            :buttons {:submit "Create Compartment"}}

          :new-drg-ipsec-connection {:title "IPsec Connection"
                                     :loading-cpes "Loading CPEs..."
                                     :no-cpes "No CPEs have been created..."
                                     :labels {:displayName "Name"
                                              :cpeId "Customer Equipment"
                                              :staticRouteDescription "The IPsec connection requires at least one static route for your on-premise device."
                                              :staticRoute-n "Static Route CIDR %s"}
                                     :buttons {:submit "Create IPsec Connection"
                                               :add-static-route "Add Static Route"}}

          :new-group-user {:title "Add User to Group"
                           :labels {:userId "User"}
                           :buttons {:submit "Add User"}}
          :new-group {:title "Create Group"
                      :labels {:name "Name"
                               :description "Description"}
                      :buttons {:submit "Create Group"}}
          :launch-instance {:title "Launch Instance"
                            :labels {:displayName "Name"
                                     :image "Image"
                                     :shape "Shape"
                                     :availabilityDomain "Availability Domain"
                                     :subnetId "Subnet"
                                     :vcnId "Cloud Network"
                                     :sshKeys "SSH Keys"
                                     :targetHost "Target Host"}
                            :shapes {:loading "Loading shapes..."}
                            :subnets {:loading "Loading subnets..."
                                      :no-subnets "There are no available subnets in this cloud network."
                                      :select-vcn "First select an availability domain and cloud network above..."}
                            :buttons {:submit "Launch Instance"}}
          :instance-volume-attachment {:title "Attach Volume"
                                       :labels {:volume "Volume"
                                                :type "Type"}
                                       :buttons {:submit "Attach"}}

          :change-password {:labels {:password "Password"
                                     :password-confirmation "Confirm Password"}
                            :buttons {:submit "Change Password"}}

          :reset-password {:buttons {:submit "Create/Reset Password"}}}

      :lists {:general {:top-count "Displaying %d of %d total %s"
                        :none-of "No %s"}

              :actions {:search {:users "Filter users..."
                                 :groups "Filter groups..."
                                 :instances "Filter instances..."
                                 :volumes "Filter volumes..."}}

              :users {:no-user-match "No users match your search."
                      :name "Name"
                      :description "Description"
                      :friendly-timeCreated "Created"
                      :friendly-timeModified "Last Modified"}

              :groups {:no-groups-match "No groups match your search."
                       :name "Name"
                       :description "Description"
                       :friendly-timeCreated "Created"
                       :friendly-timeModified "Last Modified"
                       :view-members "Members"}

              :group-members {:header "Members"
                              :remove "Remove"}

              :policies {:displayName "Name"
                         :friendly-timeCreated "Created"
                         :friendly-timeModified "Last Modified"
                         :description "Description"
                         :statements "Statements"}

              :public-keys {:badge-abbreviation "PK"}

              :user-api-keys {:header "API Keys"}}

      :measurements {:abbreviations {:hundred ""
                                     :thousand "k"
                                     :million "m"
                                     :billion "b"
                                     :trillion "t"
                                     :quadrillion "q"}}

      :data {:loading "Loading"
             :processing "Processing"
             :no-data "–"}

      :symbols {:number-seperator ","
                :decimal "."}}}
