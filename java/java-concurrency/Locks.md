## Synchronization and Locks

Concurrent programming is not easy to write correct code. Race Condition is the most popular concurrent code problem.
Java has some build in package to help write the correct code.

### Synchronized
Consider the following example:
```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizeExample {
    private int count = 0;

    private void increment() {
        count = count + 1;
    }

    public void RunInThread1() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 10000; i++) {
            executor.submit(this::increment);
        }

        ConcurrentUtils.stop(executor);
        System.out.println(count);  // 9996, race condition
    }
}

```

Instead of seeing a constant result count of 10000 the actual result varies with every execution of the above code. The reason is that we share a mutable variable upon different threads without synchronizing the access to this variable which results in a race condition.

Three steps have to be performed in order to increment the number: 
-  read the current value, 
- increase this value by one 
- write the new value to the variable. 
 
If two threads perform these steps in parallel it's possible that both threads perform step 1 simultaneously thus reading the same current value. This results in lost writes so the actual result is lower. In the above sample 35 increments got lost due to concurrent unsynchronized access to count but you may see different results when executing the code by yourself.

Java supports thread-synchronization since the early days via the `synchronized` keyword. We can utilize `synchronized` to fix the above race conditions when incrementing the count:
```java
private synchronized void increment() {
        count = count + 1;
    }
```

The synchronized keyword is also available as a block statement.

```java
private void increment() {
        synchronized (this) {
            count = count + 1;
        }
    }
```

Internally Java uses a so called *monitor* also known as *monitor lock* or *intrinsic lock* in order to manage synchronization. This monitor is bound to an object, e.g. when using synchronized methods each method share the same monitor of the corresponding object.

### Locks
Instead of using implicit locking via the synchronized keyword the Concurrency API supports various explicit locks specified by the Lock interface. Locks support various methods for finer grained lock control thus are more expressive than implicit monitors.

Multiple lock implementations are available in the standard JDK which will be demonstrated in the following sections.

#### ReentrantLock

The class `ReentrantLock` is a mutual exclusion lock with the same basic behavior as the implicit monitors accessed via the `synchronized` keyword but with extended capabilities. As the name suggests this lock implements *reentrant* characteristics just as implicit monitors.

```java
 private ReentrantLock lock = new ReentrantLock();

    private void increment() {
        lock.lock();
        try {
            ++count;
        }
        finally {
            lock.unlock();
        }
    }
```

A lock is acquired via `lock()` and released via `unlock()`. It's important to wrap your code into a `try/finally` block to ensure unlocking in case of exceptions. This method is **thread-safe** just like the `synchronized` counterpart. If another thread has already acquired the lock subsequent calls to `lock()` pause the current thread until the lock has been unlocked. **Only one thread can hold the lock at any given time**.