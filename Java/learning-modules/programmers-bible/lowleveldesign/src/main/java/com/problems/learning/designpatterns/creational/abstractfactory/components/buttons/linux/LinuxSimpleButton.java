package com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.linux;

import com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.Button;
import com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.ClickHandler;

public class LinuxSimpleButton implements Button {

    @Override
    public void onClick(ClickHandler clickHandler) {
        System.out.println("Linux Simple onClick");
        clickHandler.doAction();
    }
}
