package com.problems.learning.designpatterns.behavioral.observer.client;

import com.problems.learning.designpatterns.behavioral.observer.event.OrderEvent;
import com.problems.learning.designpatterns.behavioral.observer.observers.AnalyticsService;
import com.problems.learning.designpatterns.behavioral.observer.observers.InventoryService;
import com.problems.learning.designpatterns.behavioral.observer.observers.InvoiceService;
import com.problems.learning.designpatterns.behavioral.observer.observers.LoyaltyService;
import com.problems.learning.designpatterns.behavioral.observer.subject.Order;

public class Client {

    public static void main(String[] args) {
        Order order = new Order("ORD-1001", "CUST-42", 149.99);

        // Register observers — Order has no idea what they do internally
        order.addObserver(new InventoryService());
        order.addObserver(new InvoiceService());
        order.addObserver(new LoyaltyService());
        order.addObserver(new AnalyticsService());

        // Simulate order lifecycle
        order.updateStatus(OrderEvent.OrderStatus.CONFIRMED);
        order.updateStatus(OrderEvent.OrderStatus.SHIPPED);
        order.updateStatus(OrderEvent.OrderStatus.DELIVERED);
    }
}