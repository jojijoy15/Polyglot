package com.problems.learning.designpatterns.behavioral.mediator.aircraft.enums;

public enum AircraftStatus {
    AIRBORNE,           // in the air
    HOLDING,            // circling — waiting for clearance
    LANDING_CLEARANCE,  // cleared to land
    LANDING,            // on approach
    LANDED,             // on the ground, taxiing
    AT_GATE,            // parked at gate
    TAKEOFF_CLEARANCE,  // cleared for takeoff
    TAKING_OFF,         // rolling on runway
    EMERGENCY           // emergency declared
}