package com.problems.learning.designpatterns.creational.abstractfactory.factories;

import com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.Button;
import com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.linux.LinuxSimpleButton;
import com.problems.learning.designpatterns.creational.abstractfactory.components.scroll.Scroller;
import com.problems.learning.designpatterns.creational.abstractfactory.components.scroll.linux.LinuxSimpleScroller;

public class LinuxUIFactory implements UIFactory {

    @Override
    public Button createButton() {
        System.out.println("Creating Linux Button");
        return new LinuxSimpleButton();
    }

    @Override
    public Scroller createScroller() {
        System.out.println("Creating Linux Scroller");
        return new LinuxSimpleScroller();
    }
}
