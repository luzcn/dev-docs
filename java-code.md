### Check if array contains certain value
```java
boolean find(int[] nums, int target){
  return Arrays.stream(nums).anyMatch(x -> x == target);
}
```

### Iterate througe a HashMap
```java
Iterator it = mp.entrySet().iterator();
while (it.hasNext()) {
    Map.Entry pair = (Map.Entry)it.next();
    System.out.println(pair.getKey() + " / " + pair.getValue());
}
```

Or another similar but succinct code
```java
Map<String, String> map = new HashMap<>();
for (Map.Entry<String, String> entry : map.entrySet())
{
    System.out.println(entry.getKey() + "/" + entry.getValue());
}
```

### sort a string
in java, string is immutable. We have to create a new string.
```java
String sort(String str){
  char[] c = str.toCharArray();
  Arrays.sort(c);
  
  return new String(c);
}
```
### char to int
```java
int num = Integer.parseInt(c);
```

### Using lambda in java

