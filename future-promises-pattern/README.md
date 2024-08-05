### Future Promises Pattern

![Future Promises Pattern](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/zqqnj0oyhudjjqbrrev8.jpg)

#### Explanation

The Future Promise Pattern is used for handling asynchronous computations, allowing tasks to run in parallel without blocking the main thread. In this Spring Boot application, we demonstrated the pattern by simulating multiple asynchronous tasks and combining their results. This approach is useful in scenarios like asynchronous web requests, concurrent task execution, and long-running computations, ensuring non-blocking and efficient handling of tasks.

The Future Promise Pattern is used for asynchronous programming to handle the result of a computation that may not be immediately available. It involves two main components:

1. **Future**: Represents the result of an asynchronous computation. It provides methods to check if the computation is complete, to wait for its completion, and to retrieve the result.
2. **Promise**: Represents a proxy for a value that is not yet known. It acts as a placeholder for the result and allows the computation to be done asynchronously.

#### Use Cases

1. **Asynchronous Web Requests**: Making non-blocking HTTP requests where the response is processed once it becomes available.
2. **Concurrent Task Execution**: Running multiple tasks in parallel and processing their results once all tasks are completed.
3. **Long-Running Computations**: Handling computations that take a long time to complete without blocking the main thread.


#### Real-Life Example

In an e-commerce application, processing a large number of orders simultaneously without blocking the main thread. Each order is processed asynchronously, and once all orders are processed, the results are combined and sent to the user.


### Implementing Future Promises Pattern in Spring Boot

We'll create a simple Spring Boot application demonstrating the Future Promise Pattern. The application will simulate processing multiple tasks asynchronously and combining their results.

#### Step-by-Step Implementation

1. **Setup Spring Boot Project**

   Create a new Spring Boot project using Spring Initializr or your preferred method. Include the following dependencies:
    - Spring Web
    - Spring Boot DevTools

2. **Define the Task Service**

   Create a service to simulate tasks being performed asynchronously.

   ```java
   public interface TaskService {
       CompletableFuture<String> performTask(int taskId);
   }
   ```

3. **Implement the Task Service**

   Create a class that implements the TaskService using CompletableFuture.

   ```java
   @Service
   public class TaskServiceImpl implements TaskService {
       
       @Override
       public CompletableFuture<String> performTask(int taskId) {
           return CompletableFuture.supplyAsync(() -> {
               try {
                   // Simulate a long-running task
                   Thread.sleep((long) (Math.random() * 1000));
                   return "Task " + taskId + " completed";
               } catch (InterruptedException e) {
                   throw new IllegalStateException(e);
               }
           });
       }
   }
   ```

4. **Create the Controller**

   Create a controller to initiate tasks and demonstrate the Future Promise Pattern.

   ```java
   @RestController
   @RequestMapping("/tasks")
   public class TaskController {

       @Autowired
       private TaskService taskService;

       @GetMapping("/execute")
       public CompletableFuture<ResponseEntity<String>> executeTasks() {
           List<CompletableFuture<String>> futures = new ArrayList<>();

           for (int i = 1; i <= 5; i++) {
               futures.add(taskService.performTask(i));
           }

           return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
               .thenApply(v -> futures.stream()
                   .map(CompletableFuture::join)
                   .collect(Collectors.joining(", ")))
               .thenApply(result -> ResponseEntity.ok("All tasks completed: " + result));
       }
   }
   ```

5. **Run the Application**

   Run the Spring Boot application and test the endpoint by invoking `/tasks/execute`.

### Full Project Structure

```
src
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── futurepromise
│   │               ├── FuturePromisesPatternApplication.java
│   │               ├── controller
│   │               │   └── TaskController.java
│   │               ├── service
│   │               │   ├── TaskService.java
│   │               │   └── TaskServiceImpl.java
│   └── resources
│       └── application.properties
```
