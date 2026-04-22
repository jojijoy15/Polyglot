package com.problems.learning.designpatterns.behavioral.command.receivers;

/**
 * Receiver: knows how to perform the actual work.
 * Has no knowledge of commands, controller, or undo stacks.
 */
public class Light {

    private final String location;    // "Living Room", "Bedroom"
    private boolean on;
    private int brightness;  // 0 - 100

    public Light(String location) {
        this.location = location;
        this.on = false;
        this.brightness = 100;
    }

    public void turnOn() {
        this.on = true;
        System.out.println("💡 " + location + " light → ON  (brightness: " + brightness + "%)");
    }

    public void turnOff() {
        this.on = false;
        System.out.println("💡 " + location + " light → OFF");
    }

    public void setBrightness(int level) {
        this.brightness = level;
        System.out.println("💡 " + location + " light brightness → " + level + "%");
    }

    public boolean isOn() {
        return on;
    }

    public int getBrightness() {
        return brightness;
    }

    public String getLocation() {
        return location;
    }
}