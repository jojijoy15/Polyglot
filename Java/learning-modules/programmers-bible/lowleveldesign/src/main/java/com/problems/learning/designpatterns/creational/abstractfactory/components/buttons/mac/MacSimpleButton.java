package com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.mac;

import com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.Button;
import com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.ClickHandler;

public class MacSimpleButton implements Button {

    @Override
    public void onClick(ClickHandler clickHandler) {
        System.out.println("Mac Simple Button onClick");
        clickHandler.doAction();
    }
}
