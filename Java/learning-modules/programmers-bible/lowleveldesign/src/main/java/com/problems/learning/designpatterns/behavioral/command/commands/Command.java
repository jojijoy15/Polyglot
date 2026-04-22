package com.problems.learning.designpatterns.behavioral.command.commands;

/**
 * PROBLEM SOLVED:
 * Without Command pattern, the HomeController would call device
 * methods directly:
 * <p>
 * light.turnOn();
 * thermostat.setTemperature(22);
 * door.lock();
 * <p>
 * Problems:
 * 1. Controller is tightly coupled to every device class
 * 2. Undo requires the controller to remember previous state of every device
 * 3. Macros (run multiple commands) require messy orchestration in the controller
 * 4. Scheduling, queuing, logging commands becomes impossible cleanly
 * <p>
 * Command pattern encapsulates each action as an object.
 * The controller only knows about Command — never the devices.
 */
public interface Command {
    void execute();                // perform the action

    void undo();                   // reverse the action

    String getDescription();       // human-readable label for logs/UI
}