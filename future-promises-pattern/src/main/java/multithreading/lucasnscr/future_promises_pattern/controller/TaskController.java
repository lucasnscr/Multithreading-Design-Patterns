package multithreading.lucasnscr.future_promises_pattern.controller;

import multithreading.lucasnscr.future_promises_pattern.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
