package com.problems.learning.designpatterns.behavioral.observer.observers;

import com.problems.learning.designpatterns.behavioral.observer.event.OrderEvent;
import com.problems.learning.designpatterns.behavioral.observer.subject.OrderObserver;

public class InventoryService implements OrderObserver {

    @Override
    public void onOrderEvent(OrderEvent event) {
        switch (event.getStatus()) {
            case CONFIRMED  -> System.out.println("[Inventory] Reserving stock for order " + event.getOrderId());
            case SHIPPED    -> System.out.println("[Inventory] Deducting stock — order " + event.getOrderId() + " dispatched");
            case CANCELLED  -> System.out.println("[Inventory] Releasing reserved stock for order " + event.getOrderId());
            default         -> {} // No action for other states
        }
    }
}