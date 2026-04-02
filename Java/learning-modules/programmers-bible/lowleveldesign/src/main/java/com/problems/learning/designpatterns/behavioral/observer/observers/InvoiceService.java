package com.problems.learning.designpatterns.behavioral.observer.observers;

import com.problems.learning.designpatterns.behavioral.observer.event.OrderEvent;
import com.problems.learning.designpatterns.behavioral.observer.subject.OrderObserver;

public class InvoiceService implements OrderObserver {

    @Override
    public void onOrderEvent(OrderEvent event) {
        switch (event.getStatus()) {
            case CONFIRMED  -> System.out.printf("[Invoice]   Generating invoice for order %s — $%.2f%n",
                                    event.getOrderId(), event.getTotalAmount());
            case CANCELLED  -> System.out.println("[Invoice]   Issuing refund for order " + event.getOrderId());
            default         -> {}
        }
    }
}