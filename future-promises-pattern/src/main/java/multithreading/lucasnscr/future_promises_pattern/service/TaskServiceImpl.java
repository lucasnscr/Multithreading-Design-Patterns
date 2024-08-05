package multithreading.lucasnscr.future_promises_pattern.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

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
