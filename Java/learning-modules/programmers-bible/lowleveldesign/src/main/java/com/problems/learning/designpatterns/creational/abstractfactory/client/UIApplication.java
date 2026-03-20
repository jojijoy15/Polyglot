package com.problems.learning.designpatterns.creational.abstractfactory.client;

import com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.Button;
import com.problems.learning.designpatterns.creational.abstractfactory.components.buttons.mac.MacClickHandler;
import com.problems.learning.designpatterns.creational.abstractfactory.components.scroll.Scroller;
import com.problems.learning.designpatterns.creational.abstractfactory.components.scroll.windows.WindowsSimpleScroller;
import com.problems.learning.designpatterns.creational.abstractfactory.factories.MacUIFactory;
import com.problems.learning.designpatterns.creational.abstractfactory.factories.UIFactory;

public class UIApplication {

    private UIFactory uiFactory;

    public UIApplication(UIFactory uiFactory) {
        this.uiFactory = uiFactory;
    }

    public void renderPage() {
        Scroller scroller = this.uiFactory.createScroller();
        Button button = this.uiFactory.createButton();
        button.onClick(new MacClickHandler()); //This will not be in application but a factory will supply this
    }

    public static void main(String[] args) {
        UIFactory factory = new MacUIFactory();
        UIApplication application = new UIApplication(factory);
        application.renderPage();
    }
}
