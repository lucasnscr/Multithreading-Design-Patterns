### Producer-Consumer Pattern

![Producer-Consumer Pattern](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/sv59ze94xlrt35wd4kru.png)

#### Explanation

The Producer-Consumer Pattern is used to manage concurrent access to a shared buffer by multiple producer and consumer threads. This Spring Boot application demonstrates the pattern by simulating an order processing system where multiple orders are produced and consumed. This approach is useful in scenarios like logging systems, web servers, and task queues, ensuring efficient and synchronized handling of tasks without data loss or corruption.

#### Key Concepts

1. **Producers**: Threads that create data and put it into the buffer.
2. **Consumers**: Threads that take data from the buffer and process it.
3. **Buffer**: A shared resource where produced data is stored before being consumed. This can be implemented as a queue.
4. **Synchronization**: Ensures that producers and consumers do not access the buffer concurrently in a way that leads to data corruption or loss.

#### Use Cases

1. **Logging Systems**: Log messages are produced by various parts of an application and consumed by a logging thread that writes them to a file.
2. **Web Servers**: Handling incoming HTTP requests (produced by clients) and processing them (consumed by worker threads).
3. **Task Queues**: Tasks generated by one part of an application and processed by worker threads in the background.

#### Real-Life Example

In a food ordering system, multiple customers (producers) place orders which are added to a queue. Chefs (consumers) take orders from the queue and prepare the food. The queue ensures that orders are handled in the order they are received, and no orders are lost or duplicated.

### Implementing Producer-Consumer Pattern in Spring Boot

We'll create a simple Spring Boot application demonstrating the Producer-Consumer Pattern. The application will simulate an order processing system where multiple orders are produced and consumed.

#### Step-by-Step Implementation

1. **Setup Spring Boot Project**

   Create a new Spring Boot project using Spring Initializr or your preferred method. Include the following dependencies:
    - Spring Web
    - Spring Boot DevTools

2. **Define the Order Service**

   Create a service to manage the order queue and simulate order processing.

   ```java
   public interface OrderService {
       void produceOrder(String order);
       void consumeOrder() throws InterruptedException;
   }
   ```

3. **Implement the Order Service**

   Create a class that implements the OrderService using a BlockingQueue to handle synchronization between producers and consumers.

   ```java
   @Service
   public class OrderServiceImpl implements OrderService {
       
       private final BlockingQueue<String> orderQueue = new LinkedBlockingQueue<>();

       @Override
       public void produceOrder(String order) {
           try {
               orderQueue.put(order);
               System.out.println("Produced order: " + order);
           } catch (InterruptedException e) {
               Thread.currentThread().interrupt();
           }
       }

       @Override
       public void consumeOrder() throws InterruptedException {
           while (true) {
               String order = orderQueue.take();
               System.out.println("Consumed order: " + order);
               // Simulate order processing time
               Thread.sleep(1000);
           }
       }
   }
   ```

4. **Create the Controller**

   Create a controller to submit orders and demonstrate the Producer-Consumer Pattern.

   ```java
   @RestController
   @RequestMapping("/orders")
   public class OrderController {

       @Autowired
       private OrderService orderService;

       @GetMapping("/produce")
       public ResponseEntity<String> produceOrder(@RequestParam String order) {
           new Thread(() -> orderService.produceOrder(order)).start();
           return ResponseEntity.ok("Order produced: " + order);
       }

       @GetMapping("/start-consuming")
       public ResponseEntity<String> startConsuming() {
           new Thread(() -> {
               try {
                   orderService.consumeOrder();
               } catch (InterruptedException e) {
                   Thread.currentThread().interrupt();
               }
           }).start();
           return ResponseEntity.ok("Started consuming orders");
       }
   }
   ```

5. **Run the Application**

   Run the Spring Boot application and test the endpoints by invoking `/orders/produce?order=Order1`, `/orders/produce?order=Order2`, etc., and `/orders/start-consuming` to start the consumer thread.

### Full Project Structure

```
src
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── producerconsumer
│   │               ├── ProducerConsumerApplication.java
│   │               ├── controller
│   │               │   └── OrderController.java
│   │               ├── service
│   │               │   ├── OrderService.java
│   │               │   └── OrderServiceImpl.java
│   └── resources
│       └── application.properties
└── test
    └── java
        └── com
            └── example
                └── producerconsumer
                    └── ProducerConsumerApplicationTests.java
```
