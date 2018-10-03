https://javarevisited.blogspot.com/2012/03/add-list-certficates-java-keystore.html

### list the certificates in your jre keystore
```
$ cd /Library/Java/JavaVirtualMachines/jdk-10.0.2.jdk/Contents/Home/lib/security
$ keytool -list -keystore cacerts
```

### load certificates into keystore
```
keytool -import -keystore cacerts -file test.cer

```


### Important point about SSL, KeyStore and keyTool in Java
1. Certificates are required to access secure sites using SSL protocol or making a secure connection from the client to the server.

2. JRE stores certificates inside keystore named as "cacerts" in folder ${JAVA_HOME}/lib/security.

3. Common password of keystore is "Changeit".

4. Keytool is used to access keystore in Java and by using keytool you can list, add certificates from keystore.
