### Java simpleDateTime

#### Format current milliseconds time to ISO 8601 Timezone
1. Use this pattern and do not set timezone, will generate 3 digit timezone offset 
```java
 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
// dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
String time = dateFormat.format((new Date()).getTime());
System.out.println(time);
```
the output time would be "2018-12-07T11:53:29.598-08:00"

2. Set the timezone, to UTC or GMT, the format will generate 'Z' afterwards, indicate zero-offset timezone
```java
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
String time = dateFormat.format((new Date()).getTime());
System.out.println(time);
```
This will output time "2018-12-07T19:58:42.102Z"


#### parse the time String to Date
```java
 Date date = dateFormat.parse("2018-06-22T10:30:06.000Z");
 System.out.println(date);
```
This will output "Fri Jun 22 03:30:06 PDT 2018"

---
### Java String

#### Convert `List<String>` to `String[]`
```java
List<String> s = new ArrayList<>();
String[] res = s.toArray(String[]::new);
```
 

#### convert "List of Character" to String
```java
List<Character> res = new ArrayList<>();

res.add('a');
res.add('b');

return String.valueOf(res);

```

#### check substring
```java
String s = "apple";
System.out.println(s.indexOf("p"));
```

### convert char-to-int and int-to-char
```java
char c = 'c';

int index = c - 'a';

char x = (char) (c + 2);
```


### Java Collection Interface
![alt text](https://www.geeksforgeeks.org/wp-content/uploads/SortedSetJava.png)

#### HashSet vs. TreeSet vs. LinkedHashSet

HashSet is Implemented using a hash table. Elements are not ordered. The add, remove, and contains methods have constant time complexity O(1).

TreeSet is implemented using a tree structure(red-black tree in algorithm book). The elements in a set are sorted, but the add, remove, and contains methods has time complexity of O(log (n)). It offers several methods to deal with the ordered set like first(), last(), headSet(), tailSet(), etc.

LinkedHashSet is between HashSet and TreeSet. It is implemented as a hash table with a linked list running through it, so it provides the order of insertion. The time complexity of basic methods is O(1).

#### minHdap and maxHeap
```java
import java.util.Comparator;

public class MyComparator implements Comparator<Integer>
{
    public int compare( Integer x, Integer y )
    {
        return y - x;
    }
}

PriorityQueue minHeap=new PriorityQueue();
PriorityQueue maxHeap=new PriorityQueue(size, new MyComparator());

// use lambda
PriorityQueue<ListNode> maxHeap = new PriorityQueue<>((x, y) -> y.val - x.val);

```


----
### Using lambda
#### find max value from an array
```java
int[] nums = new int[]{1, 24, 33, 54, 5, 56, 123, 1, 24, 12};
Arrays.stream(nums).max().ifPresent(System.out::println);

IntStream.of(1, 2, 3, 4).max().ifPresent(System.out::println);
```

#### Java various length 2D array
```java
int[][] nums = {{1, 2, 3}, null, {3, 4, 51}, {1}, {12, 23, 324, 4356, 123445}};

for (int[] v : nums) {
  if (v == null) {
    continue;
  }

  Arrays.stream(v).forEach(x -> System.out.print(x + " "));
  System.out.println();
}
```


#### use lambda to print 2d array
```java
int[][] board = new int[][]{
       {0, 0, 1, 0, 0},
       {0, 0, 1, 0, 0},
       {0, 0, 1, 0, 0},
       {0, 0, 1, 0, 0},};

for (int[] cs : board) {
   Arrays.stream(cs).mapToObj(i -> i + " ").forEach(System.out::print);
   System.out.println();
}

// or 
System.out.println(String.join(" ", Arrays.stream(cs).mapToObj(String::valueOf).collect(Collectors.toList())));
```
### sort int[] array in descending order
```java
int[] sortedArray = Arrays.stream(nums).boxed().sorted(Comparator.reverseOrder()).mapToInt(x->x).toArray();
```

#### sort ArrayList
```java
ArrayList<Integer> result = new ArrayList<Integer>(); 
result.sort((v1, v2)-> v1 - v2);
```

#### Check if array contains certain value
```java
boolean find(int[] nums, int target){
  return Arrays.stream(nums).anyMatch(x -> x == target);
  
  // or use noneMatch
  // Arrays.stream(nums).noneMatch(x -> x == target);
}
```

#### Convert `List<Integer>` to `int[]`
```java
List<Integer> nums = new ArrayList<>();
nums.stream().mapToInt(x->x).toArray();
```

#### Java8 method reference
```java
List<Integer> nums = Arrays.asList(1, 22, 13, 84, 19);
nums.ForEach(System.out::println);
```

The use of double colon here is *method reference*. It is equivalent to `nums.ForEach(x->System.out.println(x))`, it tells the function that I will call you with a parameter, but I don't care the name fo this parameter.


------
### Java Sorted Set
```java
SortedSet<String> set = new TreeSet<>();
```

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

// Array initialization
int[] nums = new int[]{1,2,3};

// initialize ArrayList with size and default value
// the size is 10, all values are 1
List<Integer> data = new ArrayList<>(Collections.nCopies(10, 1));
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

### Uninstall JDK
```
sudo rm -rf "/Library/Internet Plug-Ins/JavaAppletPlugin.plugin"
sudo rm -rf "/Library/PreferencePanes/JavaControlPanel.prefPane"
sudo rm -rf "~/Library/Application Support/Java"

// remove the JDK folder
sudo rm -rf /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk
```
