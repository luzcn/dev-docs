### Check if array contains certain value
```java
boolean find(int[] nums, int target){
  return Arrays.stream(nums).anyMatch(x -> x == target);
}
```

### Using lambda in java

