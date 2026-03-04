package com.problems.learning.fooddelivery.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class Customer extends Profile {

    private List<Order> orders;

    @Builder
    public Customer(int id, String firstName, String lastName,
                    String mobileNumber, String email, String address,
                    List<Order> orders, float rating) {
        super(id, firstName, lastName, mobileNumber, email, address, rating);
        this.orders = orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", rating=" + rating +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
