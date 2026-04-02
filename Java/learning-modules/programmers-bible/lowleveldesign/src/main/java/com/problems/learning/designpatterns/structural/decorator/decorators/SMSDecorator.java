package com.problems.learning.designpatterns.structural.decorator.decorators;

import com.problems.learning.designpatterns.structural.decorator.notifier.Notifier;

// Concrete Decorators
public class SMSDecorator extends NotifierDecorator {

    public SMSDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("Sending SMS: " + message);
    }
}