package multithreading.lucasnscr.barrier_pattern.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

@Service
public class TaskServiceImpl implements TaskService {

    private final CyclicBarrier barrier;

    public TaskServiceImpl() {
        int numberOfThreads = 5; // Number of threads to synchronize
        this.barrier = new CyclicBarrier(numberOfThreads, () -> {
            System.out.println("All tasks completed. Proceeding to the next step...");
        });
    }

    @Override
    public void performTask(int taskId) throws InterruptedException, BrokenBarrierException {
        System.out.println("Task " + taskId + " is performing...");
        Thread.sleep((long) (Math.random() * 1000)); // Simulate task execution time
        System.out.println("Task " + taskId + " reached the barrier.");
        barrier.await(); // Synchronize at the barrier
    }
}
