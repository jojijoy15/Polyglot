package com.problems.learning.designpatterns.creational.factory.method.notification;

public class EmailSender implements NotificationSender {
    @Override
    public void send(String recipient, String message) {
        System.out.println("📧 Sending EMAIL to " + recipient + ": " + message);
    }
}
