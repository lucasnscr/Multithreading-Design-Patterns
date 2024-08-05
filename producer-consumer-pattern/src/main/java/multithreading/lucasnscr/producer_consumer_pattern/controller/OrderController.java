package multithreading.lucasnscr.producer_consumer_pattern.controller;

import multithreading.lucasnscr.producer_consumer_pattern.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
