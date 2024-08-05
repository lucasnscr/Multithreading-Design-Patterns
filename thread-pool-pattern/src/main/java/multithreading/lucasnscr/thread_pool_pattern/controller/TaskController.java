package multithreading.lucasnscr.thread_pool_pattern.controller;

import multithreading.lucasnscr.thread_pool_pattern.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
