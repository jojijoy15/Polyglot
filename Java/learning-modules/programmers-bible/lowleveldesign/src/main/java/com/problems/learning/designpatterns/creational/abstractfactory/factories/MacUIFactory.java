package com.problems.learning.designpatterns.creational.abstractfactory.factories;

import com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.Button;
import com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.mac.MacSimpleButton;
import com.problems.learning.designpatterns.creational.abstractfactory.components.scroll.Scroller;
import com.problems.learning.designpatterns.creational.abstractfactory.components.scroll.mac.MacSimpleScroller;

public class MacUIFactory implements UIFactory {

    @Override
    public Button createButton() {
        System.out.println("Creating Mac button");
        return new MacSimpleButton();
    }

    @Override
    public Scroller createScroller() {
        System.out.println("Creating Mac Scroller");
        return new MacSimpleScroller();
    }
}
