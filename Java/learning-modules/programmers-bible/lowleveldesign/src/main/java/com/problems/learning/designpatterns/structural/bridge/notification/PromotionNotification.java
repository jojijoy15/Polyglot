package com.problems.learning.designpatterns.structural.bridge.notification;

import com.problems.learning.designpatterns.structural.bridge.channel.DeliveryChannel;

public class PromotionNotification extends Notification {

    private final String discountCode;

    public PromotionNotification(DeliveryChannel channel, String discountCode) {
        super(channel);
        this.discountCode = discountCode;
    }

    @Override
    public void notify(String recipient, String message) {
        String subject = "Special Offer Just For You!";
        String body = message + " Use code: " + discountCode;
        channel.send(recipient, subject, body);
    }
}