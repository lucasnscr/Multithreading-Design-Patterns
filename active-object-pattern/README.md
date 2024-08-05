### Active Object Pattern

![Active Object Pattern](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/af40j3v81fy6pue3udxz.png)

#### Explanation

The Active Object Pattern helps manage concurrency by separating method invocation from execution. In this Spring Boot application, we demonstrated how to implement this pattern to process tasks asynchronously, ensuring that the server remains responsive even when handling long-running tasks. This approach can be extended to more complex scenarios such as handling multiple types of tasks or integrating with other services.

The Active Object Pattern decouples method execution from method invocation to enhance concurrency and simplify synchronized object behavior. It consists of the following key components:

1. **Proxy**: Provides an interface for clients to send requests.
2. **Method Request**: Defines a request as an object that implements a method to be executed.
3. **Scheduler**: Responsible for queuing and executing Method Requests on a separate thread.
4. **Servant**: Implements the methods exposed by the Proxy.
5. **Activation Queue**: Holds the Method Requests until they are executed by the Scheduler.
6. **Future**: Represents the result of an asynchronous computation.

#### Use Cases

1. **GUI Applications**: Ensuring that the UI remains responsive by handling time-consuming tasks asynchronously.
2. **Real-Time Systems**: Managing tasks in robotics or real-time monitoring systems where operations need to be queued and executed asynchronously.
3. **Server Applications**: Handling multiple client requests simultaneously without blocking.

#### Real-Life Example

Imagine a web server handling multiple client requests to fetch data from a database. Using the Active Object Pattern, each client request is processed asynchronously, improving throughput and ensuring that the server remains responsive.

### Implementing Active Object Pattern in Spring Boot

We'll create a simple Spring Boot application demonstrating the Active Object Pattern. The application will simulate processing client requests asynchronously.

#### Step-by-Step Implementation

1. **Setup Spring Boot Project**

   Create a new Spring Boot project using Spring Initializr or your preferred method. Include the following dependencies:
    - Spring Web
    - Spring Boot DevTools

2. **Define the Proxy Interface**

   Create an interface that defines the methods to be executed asynchronously.

   ```java
   public interface TaskService {
       Future<String> executeTask(String taskName);
   }
   ```

3. **Implement the Servant**

   Create a class that implements the methods defined in the Proxy interface.

   ```java
   @Service
   public class TaskServiceImpl implements TaskService {
       
       @Override
       public Future<String> executeTask(String taskName) {
           CompletableFuture<String> future = new CompletableFuture<>();
           new Thread(() -> {
               // Simulate a long-running task
               try {
                   Thread.sleep(2000);
                   future.complete("Task " + taskName + " completed");
               } catch (InterruptedException e) {
                   future.completeExceptionally(e);
               }
           }).start();
           return future;
       }
   }
   ```

4. **Create the Scheduler and Activation Queue**

   Implement the Scheduler to handle the execution of Method Requests. This can be done using a simple task executor in Spring.

   ```java
   @Configuration
   public class SchedulerConfig {
       
       @Bean
       public TaskExecutor taskExecutor() {
           ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
           executor.setCorePoolSize(5);
           executor.setMaxPoolSize(10);
           executor.setQueueCapacity(25);
           executor.initialize();
           return executor;
       }
   }
   ```

5. **Create the Controller**

   Create a controller to handle client requests and invoke the methods on the Proxy.

   ```java
   @RestController
   @RequestMapping("/tasks")
   public class TaskController {

       @Autowired
       private TaskService taskService;

       @GetMapping("/execute")
       public ResponseEntity<String> executeTask(@RequestParam String taskName) {
           Future<String> future = taskService.executeTask(taskName);
           try {
               return ResponseEntity.ok(future.get());
           } catch (InterruptedException | ExecutionException e) {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error executing task");
           }
       }
   }
   ```

6. **Run the Application**

   Run the Spring Boot application and test the endpoint by invoking `/tasks/execute?taskName=sampleTask`.

### Full Project Structure

```
src
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── activeobject
│   │               ├── ActiveObjectPatternApplication.java
│   │               ├── config
│   │               │   └── SchedulerConfig.java
│   │               ├── controller
│   │               │   └── TaskController.java
│   │               ├── service
│   │               │   ├── TaskService.java
│   │               │   └── TaskServiceImpl.java
│   └── resources
│       └── application.properties
```
