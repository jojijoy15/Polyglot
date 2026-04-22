package com.problems.learning.designpatterns.behavioral.command.commands;

import com.problems.learning.designpatterns.behavioral.command.receivers.Thermostat;

public class SetTemperatureCommand implements Command {

    private final Thermostat thermostat;
    private final double targetTemp;
    private double previousTemp;
    private String previousMode;

    public SetTemperatureCommand(Thermostat thermostat, double temp) {
        this.thermostat = thermostat;
        this.targetTemp = temp;
    }

    @Override
    public void execute() {
        previousTemp = thermostat.getTemperature();
        previousMode = thermostat.getMode();
        thermostat.setTemperature(targetTemp);
    }

    @Override
    public void undo() {
        thermostat.setTemperature(previousTemp);
        thermostat.setMode(previousMode);
    }

    @Override
    public String getDescription() {
        return "Set thermostat to " + targetTemp + "°C";
    }
}