package multithreading.lucasnscr.monitor_object_pattern.service;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Service
public class PrintQueueServiceImpl implements PrintQueueService {

    private final Queue<String> printQueue = new LinkedList<>();

    @Override
    public synchronized void printJob(String job) throws InterruptedException {
        System.out.println("Adding job to queue: " + job);
        printQueue.add(job);
        while (!printQueue.isEmpty() && !printQueue.peek().equals(job)) {
            wait();
        }
        System.out.println("Printing job: " + job);
        Thread.sleep(2000); // Simulate printing time
        printQueue.poll();
        System.out.println("Job completed: " + job);
        notifyAll();
    }
}
