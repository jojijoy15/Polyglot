package com.problems.learning.designpatterns.creational.factory.method.service;

import com.problems.learning.designpatterns.creational.factory.method.notification.NotificationSender;
import com.problems.learning.designpatterns.creational.factory.method.notification.PushSender;

public class PushNotificationService extends NotificationService {
    @Override
    protected NotificationSender createSender() {
        return new PushSender();
    }
}