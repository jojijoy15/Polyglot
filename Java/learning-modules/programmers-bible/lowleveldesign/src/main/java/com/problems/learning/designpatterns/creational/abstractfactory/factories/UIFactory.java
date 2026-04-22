package com.problems.learning.designpatterns.creational.abstractfactory.factories;

import com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.Button;
import com.problems.learning.designpatterns.creational.abstractfactory.components.scroll.Scroller;

public interface UIFactory {

    Button createButton();

    Scroller createScroller();
}
