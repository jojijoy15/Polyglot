package com.problems.learning.designpatterns.behavioral.observer.observers;

import com.problems.learning.designpatterns.behavioral.observer.event.OrderEvent;
import com.problems.learning.designpatterns.behavioral.observer.subject.OrderObserver;

public class AnalyticsService implements OrderObserver {

    @Override
    public void onOrderEvent(OrderEvent event) {
        System.out.printf("[Analytics] Recorded event — Order: %s | Status: %s | Amount: $%.2f%n",
                event.getOrderId(), event.getStatus(), event.getTotalAmount());
    }
}