package com.problems.learning.designpatterns.creational.abstractfactory.components.scroll.windows;

import com.problems.learning.designpatterns.creational.abstractfactory.components.scroll.ScrollHandler;
import com.problems.learning.designpatterns.creational.abstractfactory.components.scroll.Scroller;

public class WindowsSimpleScroller implements Scroller {
    

    @Override
    public void scrollUp(ScrollHandler scrollHandler) {
        System.out.println("Mac Simple scroller");
        scrollHandler.repaint();
    }
}
