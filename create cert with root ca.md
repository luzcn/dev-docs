# Create Root CA (Done once)

## Create Root Key
In this step, we generate the root ca key to sign the certificate requests, we can remove the `-des3` option if we don't need a password protected key
```bash
openssl genrsa -des3 -out rootCA.key 4096

# or 
openssl genrsa  -out rootCA.key 4096
```

## Create and self sign the Root Certificate
Here, we use the root key to create certificate that needs to distributed to all servers.
```bash
openssl req -x509 -new -nodes -key rootCA.key -sha256 -days 1024 -out rootCA.crt
```



# Create a certificate (Done for each server)
This procedure needs to be followed for each server/appliance that needs a trusted certificate from our CA

## Create the certificate key
```
openssl genrsa -out splunk_hec.key 2048
```

## Create the certificate signing request (csr)

The certificate signing request is where you specify the details for the certificate you want to generate. This request will be processed by the owner of the Root key (you in this case since you create it earlier) to generate the certificate.

**Important**: while creating the signign request, it is important to specify the Common Name providing the IP address or domain name for the service, otherwise the certificate cannot be verified.


```bash
openssl req -new -key splunk_hec.key -out splunk_hec.csr

```

When prompt, use the following info
```
Country Name (2 letter code) []:US
State or Province Name (full name) []:California
Locality Name (eg, city) []:San Francisco
Organization Name (eg, company) []:salesforce.com, Inc.
Organizational Unit Name (eg, section) []:Security
Common Name (eg, fully qualified host name) []:*.devdnrnirvana.sfdcint.net
Email Address []:dnr-data-storage-and-access@salesforce.com
```

## Verify the csr's content
```
openssl req -in splunk_hec.csr -noout -text
```

## Generate the certificate using the new created `csr` and `key` along with the `CA Root key`
```
openssl x509 -req -in splunk_hec.csr -CA rootCA.crt -CAkey rootCA.key -CAcreateserial -out splunk_hec.crt -days 365 -sha256
```


# Upload cert to AWS
1. directly upload in AWS certificate manager

2. upload as IAM 
```
aws iam upload-server-certificate --server-certificate-name dev_splunk_self_signed_hec_elb --certificate-body file://splunk_hec.pem --private-key file://splunk_hec.pem

```
