package com.problems.learning.designpatterns.structural.bridge.channel;

public class EmailChannel implements DeliveryChannel {
    @Override
    public void send(String recipient, String subject, String body) {
        System.out.println("[EMAIL]");
        System.out.println("  To      : " + recipient);
        System.out.println("  Subject : " + subject);
        System.out.println("  Body    : " + body);
    }
}
