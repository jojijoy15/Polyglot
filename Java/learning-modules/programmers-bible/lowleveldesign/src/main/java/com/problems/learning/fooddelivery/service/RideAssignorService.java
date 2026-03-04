package com.problems.learning.fooddelivery.service;

import com.problems.learning.fooddelivery.entities.Order;
import com.problems.learning.fooddelivery.entities.Rider;
import com.problems.learning.fooddelivery.repository.DataStoreRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Queue;

@RequiredArgsConstructor
public class RideAssignorService implements Runnable {

    private final Queue<Order> pendingOrders;
    private final Queue<Order> inProgressOrders;

    @Override
    public void run() {
        while (true) {
            synchronized (inProgressOrders) {
                if (inProgressOrders.isEmpty()) {
                    try {
                        inProgressOrders.notifyAll();
                        inProgressOrders.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("Rider Assignor Service :: Rider Assignment In Progress ...");
                Optional<Order> orderTaken = Optional.ofNullable(pendingOrders.poll());
                if(orderTaken.isPresent()) {
                    Order order = orderTaken.get();
                    Rider rider = DataStoreRepository.fetchAllRiders().stream()
                            .filter(Rider::isAvailable).findFirst()
                            .orElseThrow(() -> new RuntimeException("Rider not available"));
                    order.markOrderInProgress(rider);
                    inProgressOrders.add(order);
                }
                if(pendingOrders.isEmpty()) {
                    try {
                        inProgressOrders.notifyAll();
                        inProgressOrders.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Thread.currentThread().interrupt();
                }
                System.out.println("Rider Assignor Service :: Rider Assignment Completed ...");
                try {
                    inProgressOrders.notifyAll();
                    inProgressOrders.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }


}
