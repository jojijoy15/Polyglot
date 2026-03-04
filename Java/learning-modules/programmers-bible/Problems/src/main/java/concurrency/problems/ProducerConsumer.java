package concurrency.problems;

import concurrency.util.ThreadUtils;
import net.datafaker.Faker;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {

    public static void main(String[] args) {

        //Shared Resource
        Queue<String> queue = new LinkedList<>();

        //Actors
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
        producerThread.start();
    }
}

class Producer implements Runnable {

    private final Queue<String> queue;
    private static final Faker faker = new Faker();

    public Producer(Queue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (queue) {
                int counter = 10;
                do {
                    String exchanges = faker.stock().exchanges();
                    queue.add(exchanges);
                    System.out.println("Producer has processed value : " + exchanges);
                } while (counter-- > 0);
                queue.notifyAll();
                try {
                    queue.wait();
                    ThreadUtils.printThreadState(Thread.currentThread(), "Producer thread waiting ...");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }

    }
}

class Consumer implements Runnable {

    private final Queue<String> queue;

    public Consumer(Queue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            synchronized(queue) {
                do {
                    if(queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    String take = queue.poll();
                    System.out.println("Consumer has processed value : " + take);
                } while (!queue.isEmpty());
                queue.notifyAll();
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
