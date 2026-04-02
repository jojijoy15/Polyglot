package com.problems.learning.designpatterns.structural.decorator.decorators;

import com.problems.learning.designpatterns.structural.decorator.notifier.Notifier;

public abstract class NotifierDecorator implements Notifier {
    protected Notifier notifier;

    public NotifierDecorator(Notifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void send(String message) {
        notifier.send(message);
    }
}