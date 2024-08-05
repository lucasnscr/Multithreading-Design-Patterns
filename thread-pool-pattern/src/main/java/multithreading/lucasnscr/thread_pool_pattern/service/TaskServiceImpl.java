package multithreading.lucasnscr.thread_pool_pattern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public void executeTask(int taskId) {
        taskExecutor.execute(() -> {
            try {
                // Simulate task execution time
                Thread.sleep((long) (Math.random() * 1000));
                System.out.println("Task " + taskId + " completed by " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}
