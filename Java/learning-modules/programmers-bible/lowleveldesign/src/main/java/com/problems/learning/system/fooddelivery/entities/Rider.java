package com.problems.learning.system.fooddelivery.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class Rider extends Profile {

    private List<Order> orders;
    private boolean isAvailable;

    @Builder
    public Rider(int id, String firstName, String lastName,
                 String mobileNumber, String email, String address,
                 boolean isAvailable, List<Order> orders, float rating) {
        super(id, firstName, lastName, mobileNumber, email, address, rating);
        this.isAvailable = isAvailable;
        this.orders = orders;
    }

    public void assignOrder(Order order) {
        this.orders.add(order);
        order.markOrderInProgress(this);
    }

    public void markAvailabilityStatus(boolean availabilityStatus) {
        this.isAvailable = availabilityStatus;
    }

}
