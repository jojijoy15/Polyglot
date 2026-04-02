package com.problems.learning.designpatterns.creational.factory.method.notification;

public class PushSender implements NotificationSender {
    @Override
    public void send(String recipient, String message) {
        System.out.println("🔔 Sending PUSH to " + recipient + ": " + message);
    }
}