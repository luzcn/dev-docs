### build maven without test
```bash
mvn -Dmaven.test.skip=true install // skip running and compiling tests

mvn install -DskipTests // skip running tests 
```

### disable the checkstyle
```bash
mvn clean install -Dcheckstyle.skip
```
