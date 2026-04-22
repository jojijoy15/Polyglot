package com.problems.learning.designpatterns.behavioral.command.commands;

import com.problems.learning.designpatterns.behavioral.command.receivers.Light;

public class LightOffCommand implements Command {

    private final Light light;
    private boolean previousState;
    private int previousBrightness;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        previousState = light.isOn();
        previousBrightness = light.getBrightness();
        light.turnOff();
    }

    @Override
    public void undo() {
        if (previousState) {
            light.turnOn();
            light.setBrightness(previousBrightness);
        }
    }

    @Override
    public String getDescription() {
        return "Turn OFF " + light.getLocation() + " light";
    }
}