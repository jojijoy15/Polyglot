package com.problems.learning.designpatterns.behavioral.mediator.aircraft;

import com.problems.learning.designpatterns.behavioral.mediator.aircraft.enums.AircraftStatus;
import com.problems.learning.designpatterns.behavioral.mediator.aircraft.enums.AircraftType;
import com.problems.learning.designpatterns.behavioral.mediator.tower.AirTrafficControlMediator;

/**
 * PROBLEM SOLVED:
 * Aircraft is the Colleague base class. It holds a reference ONLY
 * to the mediator — never to other aircraft.
 * <p>
 * This is the key structural constraint of the pattern:
 * aircraft → mediator ✅
 * aircraft → aircraft ❌ (never directly)
 * <p>
 * All inter-aircraft coordination routes through the mediator.
 */
public abstract class Aircraft {

    protected final String callSign;    // unique identifier e.g. "AI-101"
    protected final AircraftType type;
    protected AircraftStatus status;
    protected int assignedRunway;
    protected int assignedGate;
    protected int altitude;    // feet
    protected int fuel;        // percentage
    protected final AirTrafficControlMediator mediator;

    public Aircraft(String callSign, AircraftType type,
                    int altitude, int fuel,
                    AirTrafficControlMediator mediator) {
        this.callSign = callSign;
        this.type = type;
        this.altitude = altitude;
        this.fuel = fuel;
        this.status = altitude > 0 ? AircraftStatus.AIRBORNE : AircraftStatus.AT_GATE;
        this.assignedRunway = -1;
        this.assignedGate = -1;
        this.mediator = mediator;

        // Register with the mediator on creation
        mediator.registerAircraft(this);
    }

    // ── Actions — all delegate to mediator ────────────────────────────

    public void requestLanding() {
        System.out.printf("✈️  [%s] Requesting landing clearance "
                        + "(altitude: %,dft | fuel: %d%%)%n",
                callSign, altitude, fuel);
        status = AircraftStatus.HOLDING;
        mediator.requestLanding(this);
    }

    public void requestTakeoff() {
        System.out.printf("✈️  [%s] Requesting takeoff clearance%n", callSign);
        mediator.requestTakeoff(this);
    }

    public void requestSpecificRunway(int runwayNumber) {
        System.out.printf("✈️  [%s] Requesting runway %d%n", callSign, runwayNumber);
        mediator.requestRunway(this, runwayNumber);
    }

    public void declareEmergency(String reason) {
        System.out.printf("🚨 [%s] DECLARING EMERGENCY: %s%n", callSign, reason);
        status = AircraftStatus.EMERGENCY;
        mediator.broadcastEmergency(this, reason);
    }

    public void sendMessageTo(String targetCallSign, String message) {
        mediator.sendMessage(this, targetCallSign, message);
    }

    public void releaseRunway() {
        if (assignedRunway != -1) {
            System.out.printf("✅ [%s] Releasing runway %d%n", callSign, assignedRunway);
            mediator.releaseRunway(this, assignedRunway);
            assignedRunway = -1;
        }
    }

    // ── Callbacks FROM mediator — aircraft reacts to tower instructions ─

    /**
     * PROBLEM SOLVED:
     * The mediator calls these methods to push decisions back to aircraft.
     * Aircraft never pull state from other aircraft — they only receive
     * instructions from the tower.
     */
    public void onLandingCleared(int runway, int gate) {
        this.assignedRunway = runway;
        this.assignedGate = gate;
        this.status = AircraftStatus.LANDING_CLEARANCE;
        System.out.printf("   📢 [%s] Tower: Cleared to land on runway %d → gate %d%n",
                callSign, runway, gate);
        performLanding();
    }

    public void onLandingHold(String reason) {
        this.status = AircraftStatus.HOLDING;
        System.out.printf("   📢 [%s] Tower: Hold position — %s%n", callSign, reason);
    }

    public void onTakeoffCleared(int runway) {
        this.assignedRunway = runway;
        this.status = AircraftStatus.TAKEOFF_CLEARANCE;
        System.out.printf("   📢 [%s] Tower: Cleared for takeoff on runway %d%n",
                callSign, runway);
        performTakeoff();
    }

    public void onEmergencyAlert(String emergencyCallSign, String reason) {
        System.out.printf("   🚨 [%s] ALERT: Emergency declared by %s — %s%n",
                callSign, emergencyCallSign, reason);
        respondToEmergency(emergencyCallSign);
    }

    public void onMessageReceived(String senderCallSign, String message) {
        System.out.printf("   📻 [%s] Message from %s: \"%s\"%n",
                callSign, senderCallSign, message);
    }

    // ── Abstract: subclasses define type-specific behavior ────────────

    public abstract void performLanding();

    public abstract void performTakeoff();

    public abstract void respondToEmergency(String emergencyCallSign);

    public abstract int getLandingPriority();   // higher = lands first

    // ── Getters ───────────────────────────────────────────────────────

    public String getCallSign() {
        return callSign;
    }

    public AircraftType getType() {
        return type;
    }

    public AircraftStatus getStatus() {
        return status;
    }

    public int getAltitude() {
        return altitude;
    }

    public int getFuel() {
        return fuel;
    }

    public int getAssignedRunway() {
        return assignedRunway;
    }

    public void setStatus(AircraftStatus s) {
        this.status = s;
    }

    public void setAltitude(int alt) {
        this.altitude = alt;
    }

    @Override
    public String toString() {
        return String.format("%-10s | %-12s | %-20s | Alt: %,6dft | Fuel: %3d%%",
                callSign, type, status, altitude, fuel);
    }
}