### Final on method parameter
Java makes a copy when passing a parameter, so if you pass a reference of an object, it simply passess a copy of the referece.
With `final` declared parameter, you can still change the properties of the object, but you cannot reassign the reference.

There are some situations where you have to put `final`, otherwise, it cannot compile.
```java
public void foo(final String a) {
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            System.out.print(a);
        }
    }); 
}
```

Because the `Runnable` instance would outlive the method, this wouldn't compile without the final keyword -- final tells the compiler that it's safe to take a copy of the reference (to refer to it later). Thus, it's the reference that's considered final, not the value. In other words: As a caller, you can't mess anything up...



### ArrayList initialization
```java
List<String> places = Arrays.asList("Buenos Aires", "Córdoba", "La Plata");
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

### check if a char is didit
```java
Character.isDigit(c);
```

### Using lambda
#### sort ArrayList
```java
ArrayList<Integer> result = new ArrayList<Integer>(); 
result.sort((v1, v2)-> v1 - v2);
```

#### Check if array contains certain value
```java
boolean find(int[] nums, int target){
  return Arrays.stream(nums).anyMatch(x -> x == target);
}
```

