# Multithreading-Design-Patterns
Implementing most used multithreading design patterns use cases and examples in real life

### Reader-Writer Pattern

#### Explanation

The Reader-Writer Pattern is a synchronization pattern that allows multiple readers to read from a shared resource concurrently while ensuring exclusive access for writers. This Spring Boot application demonstrates the pattern by simulating a data store that supports concurrent reads and exclusive writes. This approach is useful in scenarios like database systems, caching, and file systems, where read operations are frequent, and write operations must be done safely.

#### Key Concepts

1. **Readers**: Threads that read data from the shared resource. Multiple readers can read concurrently as long as no writer is writing.
2. **Writers**: Threads that write data to the shared resource. Writers require exclusive access, meaning no other readers or writers can access the resource while a writer is writing.
3. **Read-Write Lock**: A synchronization mechanism that allows concurrent read access or exclusive write access to a shared resource.

#### Use Cases

1. **Database Systems**: Allowing multiple clients to read data concurrently while ensuring that data modifications are done exclusively.
2. **Caching**: Ensuring that cached data can be read by multiple threads simultaneously while updates to the cache are done exclusively.
3. **File Systems**: Allowing multiple processes to read from a file while ensuring that write operations are exclusive.

#### Real-Life Example

In a news website, multiple users can read articles concurrently, but when an article is updated, the update operation must be exclusive to avoid data corruption.

### Implementing Reader-Writer Pattern in Spring Boot

We'll create a simple Spring Boot application demonstrating the Reader-Writer Pattern. The application will simulate a shared resource (e.g., a data store) that can be read by multiple readers concurrently and written by writers exclusively.

#### Step-by-Step Implementation

1. **Setup Spring Boot Project**

   Create a new Spring Boot project using Spring Initializr or your preferred method. Include the following dependencies:
    - Spring Web
    - Spring Boot DevTools

2. **Define the Data Service**

   Create a service to manage the shared resource with read and write operations.

   ```java
   public interface DataService {
       String readData();
       void writeData(String data);
   }
   ```

3. **Implement the Data Service with Read-Write Lock**

   Create a class that implements the DataService using a ReentrantReadWriteLock to manage synchronization.

   ```java
   @Service
   public class DataServiceImpl implements DataService {
       
       private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
       private String data = "Initial Data";

       @Override
       public String readData() {
           lock.readLock().lock();
           try {
               System.out.println("Reading data: " + data);
               return data;
           } finally {
               lock.readLock().unlock();
           }
       }

       @Override
       public void writeData(String newData) {
           lock.writeLock().lock();
           try {
               System.out.println("Writing data: " + newData);
               data = newData;
           } finally {
               lock.writeLock().unlock();
           }
       }
   }
   ```

4. **Create the Controller**

   Create a controller to handle read and write requests.

   ```java
   @RestController
   @RequestMapping("/data")
   public class DataController {

       @Autowired
       private DataService dataService;

       @GetMapping("/read")
       public ResponseEntity<String> readData() {
           String data = dataService.readData();
           return ResponseEntity.ok(data);
       }

       @PostMapping("/write")
       public ResponseEntity<String> writeData(@RequestBody String newData) {
           new Thread(() -> dataService.writeData(newData)).start();
           return ResponseEntity.ok("Data write initiated: " + newData);
       }
   }
   ```

5. **Run the Application**

   Run the Spring Boot application and test the endpoints by invoking `/data/read` for reading and `/data/write` for writing.

### Full Project Structure

```
src
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── readerwriter
│   │               ├── ReadyWriterPatternApplication.java
│   │               ├── controller
│   │               │   └── DataController.java
│   │               ├── service
│   │               │   ├── DataService.java
│   │               │   └── DataServiceImpl.java
│   └── resources
│       └── application.properties
```