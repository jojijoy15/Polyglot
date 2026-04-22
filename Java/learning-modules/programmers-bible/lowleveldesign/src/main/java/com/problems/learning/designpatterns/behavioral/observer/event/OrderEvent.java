package com.problems.learning.designpatterns.behavioral.observer.event;

public class OrderEvent {

    private final String orderId;
    private final OrderStatus status;
    private final double totalAmount;
    private final String customerId;

    public enum OrderStatus {
        PLACED, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    }

    public OrderEvent(String orderId, OrderStatus status, double totalAmount, String customerId) {
        this.orderId = orderId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getCustomerId() {
        return customerId;
    }
}
