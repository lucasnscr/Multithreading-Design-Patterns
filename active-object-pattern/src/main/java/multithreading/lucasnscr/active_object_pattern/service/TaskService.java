package multithreading.lucasnscr.active_object_pattern.service;

import java.util.concurrent.Future;

public interface TaskService {
    Future<String> executeTask(String taskName);
}
