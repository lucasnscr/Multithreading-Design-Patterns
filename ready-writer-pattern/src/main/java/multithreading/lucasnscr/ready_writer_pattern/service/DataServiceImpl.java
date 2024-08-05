package multithreading.lucasnscr.ready_writer_pattern.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class DataServiceImpl implements DataService {

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private String data = "Initial Data";

    @Override
    public String readData() {
        lock.readLock().lock();
        try {
            System.out.println("Reading data: " + data);
            return data;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void writeData(String newData) {
        lock.writeLock().lock();
        try {
            System.out.println("Writing data: " + newData);
            data = newData;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
