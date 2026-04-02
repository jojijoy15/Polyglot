package com.problems.learning.designpatterns.creational.factory.method.service;

import com.problems.learning.designpatterns.creational.factory.method.notification.NotificationSender;

public abstract class NotificationService {

    // The Factory Method — subclasses decide what to create
    protected abstract NotificationSender createSender();

    // Business logic using the factory method
    public void notify(String recipient, String message) {
        NotificationSender sender = createSender(); // delegated to subclass
        sender.send(recipient, message);
    }
}