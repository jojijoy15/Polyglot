package com.problems.learning.designpatterns.structural.bridge.notification;

import com.problems.learning.designpatterns.structural.bridge.channel.DeliveryChannel;

public abstract class Notification {

    // The bridge — abstraction holds implementor, not inheritance
    protected DeliveryChannel channel;

    public Notification(DeliveryChannel channel) {
        this.channel = channel;
    }

    // Subclasses define WHAT the notification says
    public abstract void notify(String recipient, String message);

    // Allow switching channel at runtime
    public void setChannel(DeliveryChannel channel) {
        this.channel = channel;
    }
}
