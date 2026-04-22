package com.problems.learning.designpatterns.behavioral.command.receivers;

public class DoorLock {

    private final String location;
    private boolean locked;

    public DoorLock(String location) {
        this.location = location;
        this.locked = true;
    }

    public void lock() {
        this.locked = true;
        System.out.println("🔒 " + location + " door → LOCKED");
    }

    public void unlock() {
        this.locked = false;
        System.out.println("🔓 " + location + " door → UNLOCKED");
    }

    public boolean isLocked() {
        return locked;
    }

    public String getLocation() {
        return location;
    }
}