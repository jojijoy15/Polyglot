package com.problems.learning.designpatterns.creational.factory.method.client;

import com.problems.learning.designpatterns.creational.factory.method.service.EmailNotificationService;
import com.problems.learning.designpatterns.creational.factory.method.service.NotificationService;
import com.problems.learning.designpatterns.creational.factory.method.service.PushNotificationService;
import com.problems.learning.designpatterns.creational.factory.method.service.SmsNotificationService;

public class Client {

    public static void main(String[] args) {
        NotificationService service = resolveService("SMS"); // could come from config/DB
        service.notify("+91-9999999999", "Your OTP is 482910");
    }

    // Optional: a dispatcher that selects the right creator
    static NotificationService resolveService(String channel) {
        return switch (channel) {
            case "EMAIL" -> new EmailNotificationService();
            case "SMS" -> new SmsNotificationService();
            case "PUSH" -> new PushNotificationService();
            default -> throw new IllegalArgumentException("Unknown channel: " + channel);
        };
    }
}