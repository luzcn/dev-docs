### setup maven dependency
```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.7.5</version>
</dependency>
```

### Write Java object to JSON
- create a class to represent the data model
```java
public class car {
    private String color;
    private String model;
}
```

- using `ObjectMapper` to write a Java object to JSON
```java
ObjectMapper objectMapper = new ObjectMapper();
Car car = new Car("yellow", "renault");

objectMapper.writeValue(new File("target/car.json"), car);
```
The output of the above can be seen in the file as follows:
`{"color":"yellow","type":"renault"}`

the method `writeValueAsString` write the Java object to JSON and returns the generated JSON as string.
```Java
String carAsString = objectMapper.writeValueAsString(car);
```


### Read JSON to Java Object
Similary, we can use `ObjectMapper` method `readValue` to read a JSON to a Java object
```java
String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
ObjectMapper objectMapper = new ObjectMapper();

Car car = objectMapper.readValue(json, Car.class);  
```

### Parse JSON to JsonNode object
We can parse a JSON  into a `JsonNode` object and used to retrieve data from a given specific node.
```java
ObjectMapper objectMapper = new ObjectMapper();
String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";

JsonNode node = objectMapper.readTree(json);
String color = node.get("color").asText();
// Output: color -> Black
```

### Convert JSON Array to Java List
A JSON in the form of an array can be parsed into a Java object list
```java
String jsonCarArray = "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";

List<Car> carList = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>(){});
```

### Convert JSON to Java Map
A JSON in the form of string can be parsed into a Java Map object
```java
String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";

Map<String, Object> map = objectMapper.readValue(json, new TypeReference<Map<String,Object>
```

