package multithreading.lucasnscr.producer_consumer_pattern.service;

public interface OrderService {
    void produceOrder(String order);
    void consumeOrder() throws InterruptedException;
}
