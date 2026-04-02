package com.problems.learning.designpatterns.behavioral.observer.subject;

import com.problems.learning.designpatterns.behavioral.observer.event.OrderEvent;

public interface OrderObserver {
    void onOrderEvent(OrderEvent event);
}