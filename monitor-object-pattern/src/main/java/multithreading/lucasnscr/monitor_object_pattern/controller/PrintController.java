package multithreading.lucasnscr.monitor_object_pattern.controller;

import multithreading.lucasnscr.monitor_object_pattern.service.PrintQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/print")
public class PrintController {

    @Autowired
    private PrintQueueService printQueueService;

    @GetMapping("/submit")
    public ResponseEntity<String> submitJob(@RequestParam String job) {
        new Thread(() -> {
            try {
                printQueueService.printJob(job);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return ResponseEntity.ok("Job submitted: " + job);
    }
}
