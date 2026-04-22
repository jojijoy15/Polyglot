package com.problems.learning.designpatterns.behavioral.command.commands;

import com.problems.learning.designpatterns.behavioral.command.receivers.Light;

public class DimLightCommand implements Command {

    private final Light light;
    private final int targetBrightness;
    private int previousBrightness;
    private boolean previousState;

    public DimLightCommand(Light light, int brightness) {
        this.light = light;
        this.targetBrightness = brightness;
    }

    @Override
    public void execute() {
        previousBrightness = light.getBrightness();
        previousState = light.isOn();
        light.setBrightness(targetBrightness);
        if (!light.isOn()) light.turnOn();
    }

    @Override
    public void undo() {
        light.setBrightness(previousBrightness);
        if (!previousState) light.turnOff();
    }

    @Override
    public String getDescription() {
        return "Dim " + light.getLocation() + " light to " + targetBrightness + "%";
    }
}