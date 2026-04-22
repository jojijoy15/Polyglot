package com.problems.learning.designpatterns.structural.bridge.client;

import com.problems.learning.designpatterns.structural.bridge.channel.EmailChannel;
import com.problems.learning.designpatterns.structural.bridge.channel.PushChannel;
import com.problems.learning.designpatterns.structural.bridge.channel.SmsChannel;
import com.problems.learning.designpatterns.structural.bridge.notification.AlertNotification;
import com.problems.learning.designpatterns.structural.bridge.notification.Notification;
import com.problems.learning.designpatterns.structural.bridge.notification.PromotionNotification;
import com.problems.learning.designpatterns.structural.bridge.notification.ReminderNotification;

/*
    Note:
    * The Bridge design pattern is a structural pattern in Java that decouples an abstraction from its implementation,
      allowing both to vary independently.
    * This pattern is essential for developing flexible and extensible software systems.
 */
public class NotificationSystem {

    public static void main(String[] args) {

        // Alert via Email
        Notification alert = new AlertNotification(new EmailChannel());
        alert.notify("joji@company.com", "Database CPU at 95%");

        System.out.println("#".repeat(30));

        // Same Alert — switch channel to SMS at runtime
        alert.setChannel(new SmsChannel());
        alert.notify("+91-9876543210", "Database CPU at 95%");

        System.out.println("#".repeat(30));

        // Reminder via Push
        Notification reminder = new ReminderNotification(
                new PushChannel(), "2025-08-01"
        );
        reminder.notify("device-token-xyz-123", "Submit your timesheet");

        System.out.println("#".repeat(30));

        // Promotion via Email
        Notification promo = new PromotionNotification(
                new EmailChannel(), "SAVE30"
        );
        promo.notify("joji@company.com", "Get 30% off on all courses this week!");

        System.out.println("#".repeat(30));

        // Promotion — same object, different channel, no code change
        promo.setChannel(new SmsChannel());
        promo.notify("+91-9876543210", "Get 30% off on all courses this week!");
    }
}
