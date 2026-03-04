package com.problems.learning.fooddelivery.entities;

import com.problems.learning.fooddelivery.enums.DeliveryStatus;
import com.problems.learning.fooddelivery.enums.Urgency;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Getter
@Builder
@ToString
public class Order {

    private String orderId;
    private Customer customer;
    private Rider rider;
    private List<Item> items; //Represents itemId
    private Restaurant restaurant;
    private Urgency urgency;
    private DeliveryStatus deliveryStatus;
    private LocalDateTime creationTime;
    private LocalDateTime deliveryInitiationTime;
    private LocalDateTime deliveryCompletionTime;

    public void markOrderInProgress(Rider rider) {
        this.rider = rider;
        this.rider.markAvailabilityStatus(false);
        this.deliveryStatus = DeliveryStatus.IN_PROGRESS;
        this.deliveryInitiationTime = LocalDateTime.now();
    }

    public void markDelivered() {
        rider.markAvailabilityStatus(true);
        this.deliveryCompletionTime= LocalDateTime.now().plusMinutes(new Random().nextInt(15, 45));
        this.deliveryStatus = DeliveryStatus.DELIVERED;
    }

}
