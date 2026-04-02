package com.problems.learning.designpatterns.structural.bridge.channel;

public class PushChannel implements DeliveryChannel {
    @Override
    public void send(String recipient, String subject, String body) {
        System.out.println("[PUSH NOTIFICATION]");
        System.out.println("  Device token : " + recipient);
        System.out.println("  Title        : " + subject);
        System.out.println("  Message      : " + body);
    }
}