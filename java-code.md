### Convert a String to Character array
```java

// Character[] chars = new Character[s.length()];
// for (int i = 0; i < chars.length; i++)
//     chars[i] = s.charAt(i);

// in java 8 lambda
Character[] chars = s.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
```


### static block 
Static block is used for initializing the static variables.This block gets executed when the class is loaded in the memory.
```java
class JavaExample {
   static int num;
   static String mystr;

   // Initialize the static members
   static {
      num = 97;
      mystr = "Static keyword in Java";
   }
   public static void main(String args[])
   {
      System.out.println("Value of num: "+num);
      System.out.println("Value of mystr: "+mystr);
   }
}
```


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
List<String> places = Arrays.asList("1", "2", "name");
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

### check if a char is digit
```java
Character.isDigit(c);
```
----
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
#### Java8 method reference
```java
List<Integer> nums = Arrays.asList(1, 22, 13, 84, 19);
nums.ForEach(System.out::println);
```

The use of double colon here is *method reference*. It is equivalent to `nums.ForEach(x->System.out.println(x))`, it tells the function that I will call you with a parameter, but I don't care the name fo this parameter.


-----

### Google Guava Rate Limiter example
```java
import com.google.common.util.concurrent.RateLimiter;

import java.util.Date;

public class Main {
    public static void main(String args[]){

        RateLimiter ratelimiter = RateLimiter.create(0.1);

        for (int i = 0; i < 10; i++){
            ratelimiter.acquire();
            System.out.println(new Date());
        }
    }
}
```
