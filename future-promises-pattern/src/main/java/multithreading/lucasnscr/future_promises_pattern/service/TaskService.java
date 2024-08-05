package multithreading.lucasnscr.future_promises_pattern.service;

import java.util.concurrent.CompletableFuture;

public interface TaskService {
    CompletableFuture<String> performTask(int taskId);
}
