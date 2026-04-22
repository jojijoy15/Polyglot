package com.problems.learning.designpatterns.behavioral.command.receivers;

public class Thermostat {

    private final String location;
    private double temperature;  // in Celsius
    private String mode;         // "HEAT", "COOL", "AUTO", "OFF"

    public Thermostat(String location) {
        this.location = location;
        this.temperature = 20.0;
        this.mode = "AUTO";
    }

    public void setTemperature(double temp) {
        this.temperature = temp;
        System.out.printf("🌡️  %s thermostat → %.1f°C (mode: %s)%n", location, temp, mode);
    }

    public void setMode(String mode) {
        this.mode = mode;
        System.out.println("🌡️  " + location + " thermostat mode → " + mode);
    }

    public double getTemperature() {
        return temperature;
    }

    public String getMode() {
        return mode;
    }
}