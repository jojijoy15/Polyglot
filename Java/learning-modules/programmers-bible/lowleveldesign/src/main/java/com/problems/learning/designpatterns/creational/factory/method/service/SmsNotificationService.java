package com.problems.learning.designpatterns.creational.factory.method.service;

import com.problems.learning.designpatterns.creational.factory.method.notification.NotificationSender;
import com.problems.learning.designpatterns.creational.factory.method.notification.SmsSender;

public class SmsNotificationService extends NotificationService {
    @Override
    protected NotificationSender createSender() {
        return new SmsSender();
    }
}