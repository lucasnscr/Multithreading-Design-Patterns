package multithreading.lucasnscr.barrier_pattern.service;

import java.util.concurrent.BrokenBarrierException;

public interface TaskService {
    void performTask(int taskId) throws InterruptedException, BrokenBarrierException;
}
