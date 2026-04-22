package com.problems.learning.designpatterns.structural.bridge.notification;

import com.problems.learning.designpatterns.structural.bridge.channel.DeliveryChannel;

public class AlertNotification extends Notification {

    public AlertNotification(DeliveryChannel channel) {
        super(channel);
    }

    @Override
    public void notify(String recipient, String message) {
        // Alerts are urgent — format accordingly
        String subject = "🚨 URGENT ALERT";
        String body = "[ACTION REQUIRED] " + message;
        channel.send(recipient, subject, body);
    }
}
