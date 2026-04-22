package com.problems.learning.designpatterns.structural.bridge.notification;

import com.problems.learning.designpatterns.structural.bridge.channel.DeliveryChannel;

public class ReminderNotification extends Notification {

    private final String dueDate;

    public ReminderNotification(DeliveryChannel channel, String dueDate) {
        super(channel);
        this.dueDate = dueDate;
    }

    @Override
    public void notify(String recipient, String message) {
        String subject = "Reminder — Due: " + dueDate;
        String body = "Just a friendly reminder: " + message;
        channel.send(recipient, subject, body);
    }
}