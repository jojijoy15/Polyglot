package com.problems.learning.designpatterns.behavioral.observer.subject;

import com.problems.learning.designpatterns.behavioral.observer.event.OrderEvent;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private final String orderId;
    private final String customerId;
    private final double totalAmount;
    private OrderEvent.OrderStatus status;

    private final List<OrderObserver> observers = new ArrayList<>();

    public Order(String orderId, String customerId, double totalAmount) {
        this.orderId     = orderId;
        this.customerId  = customerId;
        this.totalAmount = totalAmount;
        this.status      = OrderEvent.OrderStatus.PLACED;
    }

    public void addObserver(OrderObserver observer)    { observers.add(observer); }
    public void removeObserver(OrderObserver observer) { observers.remove(observer); }

    // Core: transition state and notify all observers
    public void updateStatus(OrderEvent.OrderStatus newStatus) {
        this.status = newStatus;
        System.out.println("\n── Order " + orderId + " → " + newStatus + " ──");
        notifyObservers();
    }

    private void notifyObservers() {
        OrderEvent event = new OrderEvent(orderId, status, totalAmount, customerId);
        observers.forEach(o -> o.onOrderEvent(event));
    }
}
