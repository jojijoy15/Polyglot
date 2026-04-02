package com.problems.learning.designpatterns.creational.factory.method.notification;

public class SmsSender implements NotificationSender {
    @Override
    public void send(String recipient, String message) {
        System.out.println("📱 Sending SMS to " + recipient + ": " + message);
    }
}