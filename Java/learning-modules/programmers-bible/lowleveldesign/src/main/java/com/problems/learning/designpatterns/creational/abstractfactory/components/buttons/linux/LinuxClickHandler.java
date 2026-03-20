package com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.linux;

import com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.ClickHandler;

public class LinuxClickHandler implements ClickHandler {

    public void doAction() {
        System.out.println("Linux button clicked");
    }
}
