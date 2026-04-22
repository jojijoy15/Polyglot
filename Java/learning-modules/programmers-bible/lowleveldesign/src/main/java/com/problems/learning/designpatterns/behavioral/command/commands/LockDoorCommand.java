package com.problems.learning.designpatterns.behavioral.command.commands;

import com.problems.learning.designpatterns.behavioral.command.receivers.DoorLock;

public class LockDoorCommand implements Command {

    private final DoorLock door;
    private boolean previousState;

    public LockDoorCommand(DoorLock door) {
        this.door = door;
    }

    @Override
    public void execute() {
        previousState = door.isLocked();
        door.lock();
    }

    @Override
    public void undo() {
        if (!previousState) door.unlock();   // it was unlocked before — restore
    }

    @Override
    public String getDescription() {
        return "Lock " + door.getLocation() + " door";  // Fixed: using door directly
    }
}