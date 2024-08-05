package multithreading.lucasnscr.active_object_pattern.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class TaskServiceImpl implements TaskService {

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
