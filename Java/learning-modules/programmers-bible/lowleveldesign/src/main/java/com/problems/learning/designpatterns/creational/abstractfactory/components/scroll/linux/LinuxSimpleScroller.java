package com.problems.learning.designpatterns.creational.abstractfactory.components.scroll.linux;

import com.problems.learning.designpatterns.creational.abstractfactory.components.scroll.ScrollHandler;
import com.problems.learning.designpatterns.creational.abstractfactory.components.scroll.Scroller;

public class LinuxSimpleScroller implements Scroller {


    @Override
    public void scrollUp(ScrollHandler scrollHandler) {
        System.out.println("Linux Simple scroller");
        scrollHandler.repaint();
    }
}
