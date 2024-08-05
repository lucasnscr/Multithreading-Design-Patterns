package multithreading.lucasnscr.monitor_object_pattern.service;

public interface PrintQueueService {
    void printJob(String job) throws InterruptedException;
}
