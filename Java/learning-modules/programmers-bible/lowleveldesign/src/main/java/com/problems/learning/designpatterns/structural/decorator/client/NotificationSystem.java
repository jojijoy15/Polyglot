package com.problems.learning.designpatterns.structural.decorator.client;

import com.problems.learning.designpatterns.structural.decorator.decorators.SMSDecorator;
import com.problems.learning.designpatterns.structural.decorator.decorators.SlackDecorator;
import com.problems.learning.designpatterns.structural.decorator.notifier.EmailNotifier;
import com.problems.learning.designpatterns.structural.decorator.notifier.Notifier;

import java.util.Map;

/*
    Note:
    * The Decorator pattern allows for the dynamic addition of responsibilities to objects without modifying their existing code.
    * It achieves this by providing a way to "wrap" objects within objects of similar interface, enhancing Java design patterns flexibility
 */
public class NotificationSystem {

    public static void main(String[] args) {

        Notifier notifier = new SlackDecorator(
                        new SMSDecorator(
                            new EmailNotifier()));
        notifier.send("Server is down!");
    }
}