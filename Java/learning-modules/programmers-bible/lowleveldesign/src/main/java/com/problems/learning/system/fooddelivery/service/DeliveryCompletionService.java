package com.problems.learning.system.fooddelivery.service;

import com.problems.learning.system.fooddelivery.entities.Order;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Queue;
import java.util.Random;

@RequiredArgsConstructor
public class DeliveryCompletionService implements Runnable {

    private final Queue<Order> inProgressOrders;
    private final Queue<Order> deliveredOrders;


    @Override
    public void run() {
        Random random = new Random();
        while(true) {
            synchronized (inProgressOrders) {
                if (inProgressOrders.isEmpty()) {
                    try {
                        inProgressOrders.notifyAll();
                        inProgressOrders.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                int randomDelay = random.nextInt(5000, 10000);
                try {
                    System.out.println("Delivery is about to complete ...");
                    Thread.sleep(randomDelay);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Optional<Order> polledOrder = Optional.ofNullable(inProgressOrders.poll());
                polledOrder.ifPresent( (order) -> {
                    order.markDelivered();
                    deliveredOrders.add(order);
                });
                try {
                    inProgressOrders.notifyAll();
                    inProgressOrders.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Delivery is completed...");

            }
        }
    }

}