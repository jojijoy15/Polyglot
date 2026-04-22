package com.problems.learning.system.fooddelivery;


import com.problems.learning.system.fooddelivery.entities.Order;
import com.problems.learning.system.fooddelivery.entities.Rider;
import com.problems.learning.system.fooddelivery.repository.DataStoreRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class RideDispatcherSystem {

    public static void main(String[] args) {
        /*
            1. Create a repository of Riders, Customers
            2. Create a queue to buffer order as per urgency
            3. Write a dispatcher mechanism to assign riders to pendingOrders
            4. Once a order dispatch began,
                a. mark rider as unavailable
                b. begin delivery timer
                c. mark order as in progress & remove the order from queue
            5. Once order is delivered,
                a. mark order as delivered
                b. stop delivery timer
                c. mark rider as available
        */

        Queue<Order> pendingOrders = new PriorityQueue<>(Comparator.<Order>comparingInt(a -> a.getUrgency().getPriority()).reversed());
        Queue<Order> inProgressOrders = new LinkedList<>();
        Queue<Order> deliveredOrders = new LinkedList<>();

        Queue<Rider> riders = new LinkedList<>(DataStoreRepository.fetchAllRiders());
        pendingOrders.addAll(DataStoreRepository.fetchAllOrders());


        Thread t1 = new Thread(() -> {
            while (true) {
                Optional<Order> orderTaken = Optional.ofNullable(pendingOrders.poll());
                synchronized (riders) {

                    if (riders.isEmpty()) {
                        riders.notifyAll();
                        try {
                            riders.wait(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (orderTaken.isPresent()) {
                        Order order = orderTaken.get();
                        Rider rider = riders.poll();
                        rider.assignOrder(order);
                        inProgressOrders.add(order);
                        riders.notifyAll();
                        try {
                            riders.wait(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        printOrder(order);
                    }
                    if(pendingOrders.isEmpty()) {
                        break;
                    }
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            while (true) {
                synchronized (riders) {
                    if (riders.isEmpty()) {
                        riders.notifyAll();
                        try {
                            riders.wait(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    inProgressOrders.forEach(order -> {
                        order.markDelivered();
                        deliveredOrders.add(inProgressOrders.poll());
                        riders.offer(order.getRider());
                        inProgressOrders.remove(order);
                    });
                    riders.notifyAll();
                    try {
                        riders.wait(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if(inProgressOrders.isEmpty()){
                        riders.notifyAll();
                        break;
                    }
                }

            }
        });
        t2.start();

        try {
            t2.join();
            t1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        riders.stream()
            .sorted(Comparator.<Rider>comparingInt(rider -> rider.getOrders().size()).reversed())
            .forEach(rider -> {
                List<String> orderIds = rider.getOrders().stream()
                                .map(Order::getOrderId).toList();
                System.out.println(rider.getFirstName() + "'s deliveries : "+ orderIds);

            });
        System.out.println("Pending orders: " + pendingOrders.size());
    }

    private static void printOrder(Order order) {
        String logStatement = String.format("Dispatched ➡️ %s |  %s | %s | %s | %s | %d min",
            order.getRider().getFirstName(), order.getOrderId(), order.getCustomer().getFirstName(),
            order.getRestaurant().getRestaurantName(), order.getUrgency(),
            calculateOrderDeliverTime(order));
        System.out.println(logStatement);
    }

    private static long calculateOrderDeliverTime(Order order) {
        LocalDateTime startTime = order.getDeliveryInitiationTime();
        LocalDateTime endTime = order.getDeliveryCompletionTime();
        Duration duration = Duration.between(startTime, endTime);
        return duration.toMinutes();
    }
}