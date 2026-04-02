package com.problems.learning.designpatterns.behavioral.observer.observers;

import com.problems.learning.designpatterns.behavioral.observer.event.OrderEvent;
import com.problems.learning.designpatterns.behavioral.observer.subject.OrderObserver;

public class LoyaltyService implements OrderObserver {

    private static final double POINTS_RATE = 0.10; // 10 points per $1

    @Override
    public void onOrderEvent(OrderEvent event) {
        if (event.getStatus() == OrderEvent.OrderStatus.DELIVERED) {
            long points = Math.round(event.getTotalAmount() * POINTS_RATE);
            System.out.printf("[Loyalty]   Credited %d points to customer %s%n",
                    points, event.getCustomerId());
        }
    }
}