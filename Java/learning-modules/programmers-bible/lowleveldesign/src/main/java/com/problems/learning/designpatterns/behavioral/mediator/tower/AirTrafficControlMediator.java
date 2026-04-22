package com.problems.learning.designpatterns.behavioral.mediator.tower;

import com.problems.learning.designpatterns.behavioral.mediator.aircraft.Aircraft;

/**
 * PROBLEM SOLVED:
 * Without Mediator, N aircraft need N*(N-1) direct connections.
 * With Mediator, each aircraft only knows the tower — N connections total.
 * <p>
 * The tower (mediator) becomes the single coordination hub.
 * Aircraft never reference each other — they only send messages to the tower.
 * Adding a new aircraft type requires zero changes to existing aircraft.
 */
public interface AirTrafficControlMediator {
    void registerAircraft(Aircraft aircraft);

    void requestLanding(Aircraft aircraft);

    void requestTakeoff(Aircraft aircraft);

    void requestRunway(Aircraft aircraft, int runwayNumber);

    void broadcastEmergency(Aircraft aircraft, String reason);

    void sendMessage(Aircraft sender, String targetCallSign, String message);

    void releaseRunway(Aircraft aircraft, int runwayNumber);
}