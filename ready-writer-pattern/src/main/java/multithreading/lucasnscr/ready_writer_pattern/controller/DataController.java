package multithreading.lucasnscr.ready_writer_pattern.controller;

import multithreading.lucasnscr.ready_writer_pattern.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
