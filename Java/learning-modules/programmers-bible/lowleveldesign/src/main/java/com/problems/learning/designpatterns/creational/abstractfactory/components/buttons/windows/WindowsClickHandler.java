package com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.windows;

import com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.ClickHandler;

public class WindowsClickHandler implements ClickHandler {


    public void doAction() {
        System.out.println("WindowsClickHandler clicked");
    }
}
