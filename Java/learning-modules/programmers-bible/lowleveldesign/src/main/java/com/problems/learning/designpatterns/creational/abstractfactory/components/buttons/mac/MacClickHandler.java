package com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.mac;

import com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.ClickHandler;

public class MacClickHandler implements ClickHandler {

    public void doAction() {
        System.out.println("Mac Button clicked");
    }
}
