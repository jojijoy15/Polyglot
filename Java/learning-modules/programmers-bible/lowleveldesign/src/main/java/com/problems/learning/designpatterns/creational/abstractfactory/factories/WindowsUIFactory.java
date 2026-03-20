package com.problems.learning.designpatterns.creational.abstractfactory.factories;

import com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.Button;
import com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.windows.WindowsSimpleButton;
import com.problems.learning.designpatterns.creational.abstractfactory.components.scroll.Scroller;
import com.problems.learning.designpatterns.creational.abstractfactory.components.scroll.windows.WindowsSimpleScroller;

public class WindowsUIFactory implements UIFactory {

    @Override
    public Button createButton() {
        System.out.println("Creating Windows Button");
        return new WindowsSimpleButton();
    }

    @Override
    public Scroller createScroller() {
        System.out.println("Creating Windows Scroller");
        return new WindowsSimpleScroller();
    }
}
