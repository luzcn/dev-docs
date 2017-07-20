## Threads and Executors

### Thread and Runnable
Processes are instances of programs which typically run independent to each other, e.g. if you start a java program the operating system spawns a new process which runs in parallel to other programs. 
Inside those processes we can utilize threads to execute code concurrently, so we can make the most out of the available cores of the CPU.

Before start a new thread, you need to specify the code (behavior) to be executed by this thread. The code, called *task* is implementing the `Runnable` interface, which has only a single function `Run` and returns `void`. 

```java
 public static void main(String[] args) {

        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();

            System.out.println(threadName);
        };

        // run the task in main thread
        task.run();

        // create a new thread and run the task in this thread
        Thread newThread = new Thread(task, "ThreadName1");
        newThread.start();
        
        System.out.println("Done!");
    }
}

```
In this example, we have 2 threads running: the `main` and `ThreadName1` threads. Due to concurrency, we cannot predicate if the runnable will be invoked before printing the `Done!` message. The order is non-deterministic. 


Threads can be put to sleep for a certain duration. This is quite handy to simulate long running tasks in the subsequent code samples:
```java
public static void main(String[] args) {

        Runnable runnable = () -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.println("First Message " + name);

                // delay 1 second, we can use Thread.sleep(1000) alternatively. 
                TimeUnit.SECONDS.sleep(1);

                System.out.println("Second Message " + name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }
```

Directly using raw thread is tedious and error-prone. The Java Concurrency API, located in `java.util.concurrent` contains many useful classes to handle multi-thread programming. 

### Executors
The Concurrency API introduces the concept of an ExecutorService as a higher level replacement for working with threads directly. Executors are capable of running asynchronous tasks and typically manage a pool of threads, so we don't have to create new threads manually. All threads of the internal pool will be reused under the hood for revenant tasks, so we can run as many concurrent tasks as we want throughout the life-cycle of our application with a single executor service.

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        // the factory method to creat new executor.
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        });

    }
}
```
The class `Executors` provides convenient factory methods for creating different kinds of executor services. In this sample we use an executor with a thread pool of size one.

The result looks similar to the above sample but when running the code you'll notice an important difference: **the java process never stops! Executors have to be stopped explicitly - otherwise they keep listening for new tasks.**

An `ExecutorService` provides two methods for that purpose: `shutdown()` waits for currently running tasks to finish, `shutdownNow()` interrupts all running tasks and shut the executor down immediately.
The following code sample shuts down the `Executor` softly.

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                System.out.println("attempt to shutdown executor");
                executor.shutdown();
                executor.awaitTermination(5, TimeUnit.SECONDS);
            }
            catch (InterruptedException e) {
                System.err.println("tasks interrupted");
            }
            finally {
                if (!executor.isTerminated()) {
                    System.err.println("cancel non-finished tasks");
                }
                executor.shutdownNow();
                System.out.println("shutdown finished");
            }
        });

    }
}
```

<<<<<<< HEAD
The executor also supports another kind of task named `Callable`.  Callables are functional interface like `Runnable` but return a value.

```java
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        Callable<String> task = () -> {
            try {
                Thread.sleep(1000);
                return Thread.currentThread().getName();
            }
            catch (InterruptedException e) {
                throw new IllegalStateException("Task Interrupted", e);
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<String> future = executor.submit(task);

        try {
             // executor.shutdownNow();  /*This will cause the interrupted exception*/
            String result = future.get();
            System.out.print(result);
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
         finally {
            executor.shutdown();
        }
    }
}

```

Again, the `executor.submit` function invokes a thread to run the task, but not waits for the task complete. It returns a `Future` object, call the `get()` method will block the current thread and wait for the task finish. When call the `get()` method, we can set the time out parameter `future.get(1, TimeUnit.SECONDS)`. 

Executors support batch submitting of multiple `Callable` tasks at once via invokeAll().
This method accepts a collection of callables and returns a list of futures.
```java
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newWorkStealingPool();
        List<Callable<String>> taskList = Arrays.asList(() -> "task1", () -> "task2", () -> "task3");

        try {
            executor.invokeAll(taskList)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        }
                        catch (InterruptedException | ExecutionException e) {
                            throw new IllegalStateException(e);
                        }
                    })
                    .forEach(System.out::println);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            executor.shutdown();
        }


    }
}
```
=======
The executor also supports another kind of task named `Callable`.  Callables are functional interface like `Runnable` but return a value.
>>>>>>> aa894c457466726c469f60b791023483e8ed9a28
