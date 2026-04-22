package com.problems.learning.designpatterns.behavioral.command.commands;

import com.problems.learning.designpatterns.behavioral.command.receivers.Light;

/**
 * PROBLEM SOLVED:
 * LightOnCommand captures everything needed to execute AND undo
 * the action — the receiver (light) and the previous state.
 * The controller never needs to know Light has a turnOn() method.
 */
public class LightOnCommand implements Command {

    private final Light light;
    private boolean previousState;    // for undo
    private int previousBrightness;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        // Save state BEFORE executing — needed for undo
        previousState = light.isOn();
        previousBrightness = light.getBrightness();
        light.turnOn();
    }

    @Override
    public void undo() {
        if (!previousState) {
            light.turnOff();  // it was OFF before — restore OFF
        }
        light.setBrightness(previousBrightness);
    }

    @Override
    public String getDescription() {
        return "Turn ON " + light.getLocation() + " light";
    }
}