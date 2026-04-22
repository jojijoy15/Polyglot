package com.problems.learning.designpatterns.behavioral.command.commands;

import com.problems.learning.designpatterns.behavioral.command.receivers.DoorLock;

public class UnlockDoorCommand implements Command {

    private final DoorLock door;
    private boolean previousState;

    public UnlockDoorCommand(DoorLock door) {
        this.door = door;
    }

    @Override
    public void execute() {
        previousState = door.isLocked();
        door.unlock();
    }

    @Override
    public void undo() {
        if (previousState) door.lock();   // it was locked before — restore
    }

    @Override
    public String getDescription() {
        return "Unlock door";
    }
}