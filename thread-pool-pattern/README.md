### Thread Pool Pattern

![Thread Pool Pattern](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/iyzv012op7vj94b0o9u3.png)

#### Explanation

The Thread Pool Pattern is used to manage and reuse a pool of threads to perform tasks concurrently, improving performance and resource management. This Spring Boot application demonstrates the pattern by simulating the handling of multiple tasks using a thread pool. This approach is useful in scenarios like web servers, database connection pools, and background processing, ensuring efficient and scalable handling of concurrent tasks.

#### Key Concepts

1. **Thread Pool**: A collection of pre-initialized threads that stand by to perform tasks.
2. **Task Queue**: A queue where tasks are submitted for execution.
3. **Worker Threads**: Threads from the thread pool that pick up tasks from the task queue and execute them.

#### Use Cases

1. **Web Servers**: Handling multiple incoming HTTP requests concurrently.
2. **Database Connection Pools**: Managing a pool of database connections for efficient reuse.
3. **Background Processing**: Performing background tasks such as logging, data processing, etc.

#### Real-Life Example

A web server handles multiple incoming HTTP requests. Instead of creating a new thread for each request, the server uses a thread pool to handle the requests concurrently, ensuring efficient resource usage and reducing the overhead of thread management.

### Implementing Thread Pool Pattern in Spring Boot

We'll create a simple Spring Boot application demonstrating the Thread Pool Pattern. The application will simulate handling multiple tasks using a thread pool.

#### Step-by-Step Implementation

1. **Setup Spring Boot Project**

   Create a new Spring Boot project using Spring Initializr or your preferred method. Include the following dependencies:
    - Spring Web
    - Spring Boot DevTools

2. **Configure Thread Pool**

   Configure the thread pool using a `ThreadPoolTaskExecutor`.

   ```java
   @Configuration
   public class ThreadPoolConfig {

       @Bean
       public ThreadPoolTaskExecutor taskExecutor() {
           ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
           executor.setCorePoolSize(5);
           executor.setMaxPoolSize(10);
           executor.setQueueCapacity(25);
           executor.initialize();
           return executor;
       }
   }
   ```

3. **Define the Task Service**

   Create a service to simulate tasks being performed.

   ```java
   public interface TaskService {
       void executeTask(int taskId);
   }
   ```

4. **Implement the Task Service**

   Create a class that implements the TaskService using the thread pool to execute tasks.

   ```java
   @Service
   public class TaskServiceImpl implements TaskService {
       
       @Autowired
       private ThreadPoolTaskExecutor taskExecutor;

       @Override
       public void executeTask(int taskId) {
           taskExecutor.execute(() -> {
               try {
                   // Simulate task execution time
                   Thread.sleep((long) (Math.random() * 1000));
                   System.out.println("Task " + taskId + " completed by " + Thread.currentThread().getName());
               } catch (InterruptedException e) {
                   Thread.currentThread().interrupt();
               }
           });
       }
   }
   ```

5. **Create the Controller**

   Create a controller to initiate tasks and demonstrate the Thread Pool Pattern.

   ```java
   @RestController
   @RequestMapping("/tasks")
   public class TaskController {

       @Autowired
       private TaskService taskService;

       @GetMapping("/execute")
       public ResponseEntity<String> executeTasks() {
           for (int i = 1; i <= 10; i++) {
               int taskId = i;
               taskService.executeTask(taskId);
           }
           return ResponseEntity.ok("Tasks submitted");
       }
   }
   ```

6. **Run the Application**

   Run the Spring Boot application and test the endpoint by invoking `/tasks/execute`.

### Full Project Structure

```
src
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── threadpool
│   │               ├── ThreadPoolPatternApplication.java
│   │               ├── config
│   │               │   └── ThreadPoolConfig.java
│   │               ├── controller
│   │               │   └── TaskController.java
│   │               ├── service
│   │               │   ├── TaskService.java
│   │               │   └── TaskServiceImpl.java
│   └── resources
│       └── application.properties
```
