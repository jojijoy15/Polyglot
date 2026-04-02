package com.problems.learning.designpatterns.creational.factory.method.service;

import com.problems.learning.designpatterns.creational.factory.method.notification.EmailSender;
import com.problems.learning.designpatterns.creational.factory.method.notification.NotificationSender;

public class EmailNotificationService extends NotificationService {
    @Override
    protected NotificationSender createSender() {
        return new EmailSender();
    }
}
