package multithreading.lucasnscr.producer_consumer_pattern.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
