package com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.windows;

import com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.Button;
import com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.ClickHandler;

public class WindowsSimpleButton implements Button {

    @Override
    public void onClick(ClickHandler clickHandler) {
        System.out.println("WindowsSimpleButton onClick");
        clickHandler.doAction();
    }
}
