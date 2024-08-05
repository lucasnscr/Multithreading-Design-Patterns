### Monitor Object Pattern

![Monitor Object Pattern](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/pwn4461fjht0c8scnt47.jpg)

#### Explanation

The Monitor Object Pattern is used to achieve mutual exclusion and synchronization in concurrent programming. This Spring Boot application demonstrates the pattern by simulating a print queue system where multiple print jobs are handled sequentially. This approach is useful in scenarios like resource management, producer-consumer problems, and thread-safe caching, ensuring thread-safe and synchronized access to shared resources.

1. **Mutual Exclusion**: Ensures that only one thread can access the critical section of code at a time.
2. **Condition Variables**: Used to allow threads to wait for certain conditions to be met before continuing execution.


#### Use Cases

1. **Resource Management**: Ensuring that multiple threads can access a limited resource without conflict.
2. **Producer-Consumer Problem**: Managing synchronization between producer and consumer threads.
3. **Thread-Safe Caching**: Ensuring thread-safe access to a cache or shared resource.


#### Real-Life Example

Consider a print queue system where multiple print jobs are submitted from different computers to a single printer. The Monitor Object Pattern can ensure that only one print job is processed by the printer at a time, while other jobs wait their turn.

### Implementing Monitor Object Pattern in Spring Boot

We'll create a simple Spring Boot application demonstrating the Monitor Object Pattern. The application will simulate a print queue system where multiple print jobs are handled sequentially.

#### Step-by-Step Implementation

1. **Setup Spring Boot Project**

   Create a new Spring Boot project using Spring Initializr or your preferred method. Include the following dependencies:
    - Spring Web
    - Spring Boot DevTools

2. **Define the Print Queue Service**

   Create a service to manage the print queue.

   ```java
   public interface PrintQueueService {
       void printJob(String job) throws InterruptedException;
   }
   ```

3. **Implement the Print Queue Service**

   Create a class that implements the PrintQueueService using synchronized methods to ensure mutual exclusion.

   ```java
   @Service
   public class PrintQueueServiceImpl implements PrintQueueService {
       
       private final Queue<String> printQueue = new LinkedList<>();

       @Override
       public synchronized void printJob(String job) throws InterruptedException {
           System.out.println("Adding job to queue: " + job);
           printQueue.add(job);
           while (!printQueue.isEmpty() && !printQueue.peek().equals(job)) {
               wait();
           }
           System.out.println("Printing job: " + job);
           Thread.sleep(2000); // Simulate printing time
           printQueue.poll();
           System.out.println("Job completed: " + job);
           notifyAll();
       }
   }
   ```

4. **Create the Controller**

   Create a controller to submit print jobs and demonstrate the Monitor Object Pattern.

   ```java
   @RestController
   @RequestMapping("/print")
   public class PrintController {

       @Autowired
       private PrintQueueService printQueueService;

       @GetMapping("/submit")
       public ResponseEntity<String> submitJob(@RequestParam String job) {
           new Thread(() -> {
               try {
                   printQueueService.printJob(job);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }).start();
           return ResponseEntity.ok("Job submitted: " + job);
       }
   }
   ```

5. **Run the Application**

   Run the Spring Boot application and test the endpoint by invoking `/print/submit?job=Job1`, `/print/submit?job=Job2`, etc.

### Full Project Structure

```
src
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── monitorobject
│   │               ├── MonitorObjectPatternApplication.java
│   │               ├── controller
│   │               │   └── PrintController.java
│   │               ├── service
│   │               │   ├── PrintQueueService.java
│   │               │   └── PrintQueueServiceImpl.java
│   └── resources
│       └── application.properties
```
