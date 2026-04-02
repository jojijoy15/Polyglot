package com.problems.learning.designpatterns.structural.bridge.channel;

public interface DeliveryChannel {
    void send(String recipient, String subject, String body);
}