package com.problems.learning.designpatterns.behavioral.mediator.tower;

import com.problems.learning.designpatterns.behavioral.mediator.aircraft.Aircraft;
import com.problems.learning.designpatterns.behavioral.mediator.aircraft.enums.AircraftStatus;
import com.problems.learning.designpatterns.behavioral.mediator.aircraft.enums.AircraftType;

import java.util.*;

/**
 * PROBLEM SOLVED:
 * ATCTower is the central coordinator — it holds ALL coordination logic.
 * <p>
 * WITHOUT Mediator:
 * - Each aircraft must know about every other aircraft
 * - N aircraft = N*(N-1)/2 relationships to manage
 * - Landing queue, runway state, gate assignments scattered across objects
 * - Adding a new aircraft type requires updating all existing types
 * <p>
 * WITH Mediator:
 * - All aircraft know only the tower
 * - N aircraft = N relationships total
 * - All runway, gate, queue logic centralized here
 * - New aircraft type: just register with the tower — done
 * <p>
 * The tower never exposes aircraft to each other.
 * It reads their state, makes decisions, and calls their callback methods.
 */
public class ATCTower implements AirTrafficControlMediator {

    private final String towerName;
    private final Map<String, Aircraft> registeredAircraft = new LinkedHashMap<>();
    private final Map<Integer, String> runwayOccupancy = new HashMap<>();
    private final Map<Integer, String> gateOccupancy = new HashMap<>();
    private final Queue<Aircraft> landingQueue = new PriorityQueue<>(
            Comparator.comparingInt(Aircraft::getLandingPriority).reversed()
    );
    private final Queue<Aircraft> takeoffQueue = new LinkedList<>();
    private final List<String> auditLog = new ArrayList<>();

    // Airport configuration
    private final int totalRunways;
    private final int totalGates;

    public ATCTower(String towerName, int totalRunways, int totalGates) {
        this.towerName = towerName;
        this.totalRunways = totalRunways;
        this.totalGates = totalGates;

        // Initialize runways and gates as free
        for (int i = 1; i <= totalRunways; i++) runwayOccupancy.put(i, null);
        for (int i = 1; i <= totalGates; i++) gateOccupancy.put(i, null);

        System.out.println("🗼 " + towerName + " online — "
                + totalRunways + " runways, "
                + totalGates + " gates\n");
    }

    // ── Registration ──────────────────────────────────────────────────

    @Override
    public void registerAircraft(Aircraft aircraft) {
        registeredAircraft.put(aircraft.getCallSign(), aircraft);
        log("Registered: " + aircraft.getCallSign()
                + " (" + aircraft.getType() + ")");
        System.out.printf("🗼 [%s] Registered: %s%n", towerName, aircraft);
    }

    // ── Landing Coordination ──────────────────────────────────────────

    /**
     * PROBLEM SOLVED:
     * Without Mediator, each aircraft requesting a landing would need
     * to scan all other aircraft to know which runways are free.
     * Here, the tower centrally manages runway state and grants/denies
     * clearance based on global airport state — invisible to any aircraft.
     */
    @Override
    public void requestLanding(Aircraft aircraft) {
        log(aircraft.getCallSign() + " requested landing");
        landingQueue.offer(aircraft);

        // Process queue immediately
        processLandingQueue();
    }

    private void processLandingQueue() {
        // Re-evaluate queue — process highest priority aircraft first
        List<Aircraft> toProcess = new ArrayList<>(landingQueue);
        toProcess.sort(Comparator.comparingInt(Aircraft::getLandingPriority).reversed());

        for (Aircraft aircraft : toProcess) {
            if (aircraft.getStatus() != AircraftStatus.HOLDING
                    && aircraft.getStatus() != AircraftStatus.AIRBORNE
                    && aircraft.getStatus() != AircraftStatus.EMERGENCY) {
                landingQueue.remove(aircraft);
                continue;
            }

            int runway = findFreeRunway(aircraft.getType());
            int gate = findFreeGate(aircraft.getType());

            if (runway != -1 && gate != -1) {
                // Assign and clear from queue
                occupyRunway(runway, aircraft.getCallSign());
                occupyGate(gate, aircraft.getCallSign());
                landingQueue.remove(aircraft);
                log("Cleared " + aircraft.getCallSign()
                        + " to land on runway " + runway + ", gate " + gate);
                aircraft.onLandingCleared(runway, gate);
            } else {
                String reason = runway == -1
                        ? "all runways occupied"
                        : "all gates occupied";
                aircraft.onLandingHold(reason);
                log(aircraft.getCallSign() + " holding — " + reason);
                break;  // if top priority can't land, no point checking lower priority
            }
        }
    }

    // ── Takeoff Coordination ──────────────────────────────────────────

    @Override
    public void requestTakeoff(Aircraft aircraft) {
        log(aircraft.getCallSign() + " requested takeoff");
        takeoffQueue.offer(aircraft);
        processTakeoffQueue();
    }

    private void processTakeoffQueue() {
        if (takeoffQueue.isEmpty()) return;

        Aircraft aircraft = takeoffQueue.peek();
        int runway = findFreeRunway(aircraft.getType());

        if (runway != -1) {
            takeoffQueue.poll();
            occupyRunway(runway, aircraft.getCallSign());
            log("Cleared " + aircraft.getCallSign()
                    + " for takeoff on runway " + runway);
            aircraft.onTakeoffCleared(runway);
        } else {
            System.out.printf("🗼 [%s] All runways busy — %s holds for takeoff%n",
                    towerName, aircraft.getCallSign());
        }
    }

    // ── Specific Runway Request ───────────────────────────────────────

    @Override
    public void requestRunway(Aircraft aircraft, int runwayNumber) {
        if (!runwayOccupancy.containsKey(runwayNumber)) {
            System.out.printf("🗼 [%s] Runway %d does not exist%n",
                    towerName, runwayNumber);
            return;
        }

        if (runwayOccupancy.get(runwayNumber) == null) {
            occupyRunway(runwayNumber, aircraft.getCallSign());
            int gate = findFreeGate(aircraft.getType());
            aircraft.onLandingCleared(runwayNumber, gate != -1 ? gate : 1);
        } else {
            String holder = runwayOccupancy.get(runwayNumber);
            aircraft.onLandingHold("Runway " + runwayNumber
                    + " occupied by " + holder);
        }
    }

    // ── Emergency Protocol ────────────────────────────────────────────

    /**
     * PROBLEM SOLVED:
     * Emergency broadcast is the clearest demonstration of Mediator value.
     * ONE call to the tower notifies ALL registered aircraft.
     * <p>
     * Without Mediator, the emergency aircraft would need a reference
     * to every other aircraft and call each one individually.
     * With Mediator, one call propagates to everyone automatically —
     * including aircraft registered AFTER the emergency caller was created.
     */
    @Override
    public void broadcastEmergency(Aircraft emergencyAircraft, String reason) {
        log("🚨 EMERGENCY: " + emergencyAircraft.getCallSign() + " — " + reason);
        System.out.printf("%n🚨🚨🚨 [%s] EMERGENCY ALERT: %s declares emergency — %s 🚨🚨🚨%n%n",
                towerName, emergencyAircraft.getCallSign(), reason);

        // Grant emergency aircraft immediate runway access
        int emergencyRunway = findFreeRunway(emergencyAircraft.getType());
        if (emergencyRunway == -1) {
            // Force-clear the first available runway for emergency
            emergencyRunway = clearRunwayForEmergency();
        }
        int gate = findFreeGate(emergencyAircraft.getType());
        occupyRunway(emergencyRunway, emergencyAircraft.getCallSign());
        if (gate != -1) occupyGate(gate, emergencyAircraft.getCallSign());

        // Notify ALL other aircraft
        for (Aircraft aircraft : registeredAircraft.values()) {
            if (!aircraft.getCallSign().equals(emergencyAircraft.getCallSign())
                    && aircraft.getStatus() != AircraftStatus.AT_GATE) {
                aircraft.onEmergencyAlert(emergencyAircraft.getCallSign(), reason);
            }
        }

        // Immediately clear emergency aircraft to land
        emergencyAircraft.onLandingCleared(emergencyRunway,
                gate != -1 ? gate : 1);
    }

    // ── Direct Messaging ──────────────────────────────────────────────

    /**
     * PROBLEM SOLVED:
     * Aircraft-to-aircraft messaging routes through the tower.
     * The sender never holds a reference to the receiver —
     * it only knows the receiver's call sign (a String).
     * The tower resolves the call sign to an Aircraft and delivers it.
     */
    @Override
    public void sendMessage(Aircraft sender, String targetCallSign, String message) {
        Aircraft target = registeredAircraft.get(targetCallSign);
        if (target == null) {
            System.out.printf("🗼 [%s] Unknown call sign: %s%n",
                    towerName, targetCallSign);
            return;
        }
        log(sender.getCallSign() + " → " + targetCallSign + ": " + message);
        target.onMessageReceived(sender.getCallSign(), message);
    }

    // ── Runway Release ────────────────────────────────────────────────

    @Override
    public void releaseRunway(Aircraft aircraft, int runwayNumber) {
        runwayOccupancy.put(runwayNumber, null);
        log("Runway " + runwayNumber + " released by " + aircraft.getCallSign());

        // Automatically process waiting aircraft when a runway frees up
        if (!landingQueue.isEmpty()) {
            System.out.printf("🗼 [%s] Runway %d free — processing landing queue (%d waiting)%n%n",
                    towerName, runwayNumber, landingQueue.size());
            processLandingQueue();
        } else if (!takeoffQueue.isEmpty()) {
            processTakeoffQueue();
        }
    }

    public void releaseGate(Aircraft aircraft, int gateNumber) {
        gateOccupancy.put(gateNumber, null);
        log("Gate " + gateNumber + " released by " + aircraft.getCallSign());
    }

    // ── Runway / Gate Utilities ───────────────────────────────────────

    private int findFreeRunway(AircraftType type) {
        for (Map.Entry<Integer, String> entry : runwayOccupancy.entrySet()) {
            if (entry.getValue() == null) {
                // Helicopters prefer higher-numbered pads
                if (type == AircraftType.HELICOPTER) {
                    return entry.getKey() == totalRunways ? entry.getKey() : -1;
                }
                return entry.getKey();
            }
        }
        return -1;  // all occupied
    }

    private int findFreeGate(AircraftType type) {
        for (Map.Entry<Integer, String> entry : gateOccupancy.entrySet()) {
            if (entry.getValue() == null) return entry.getKey();
        }
        return -1;
    }

    private int clearRunwayForEmergency() {
        // Pick first occupied runway and mark it to be vacated
        for (Map.Entry<Integer, String> entry : runwayOccupancy.entrySet()) {
            if (entry.getValue() != null) {
                System.out.printf("🗼 [%s] Force-clearing runway %d for emergency%n",
                        towerName, entry.getKey());
                runwayOccupancy.put(entry.getKey(), null);
                return entry.getKey();
            }
        }
        return 1;
    }

    private void occupyRunway(int runway, String callSign) {
        runwayOccupancy.put(runway, callSign);
    }

    private void occupyGate(int gate, String callSign) {
        gateOccupancy.put(gate, callSign);
    }

    // ── Status Board ──────────────────────────────────────────────────

    public void printStatusBoard() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.printf("║  🗼 %s — Status Board%-37s║%n", towerName, "");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");

        System.out.println("║  AIRCRAFT                                                    ║");
        registeredAircraft.values().forEach(a ->
                System.out.printf("║  %s║%n", String.format("%-62s", a)));

        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║  RUNWAYS                                                     ║");
        runwayOccupancy.forEach((rwy, occ) ->
                System.out.printf("║    Runway %d: %-50s║%n",
                        rwy, occ != null ? "OCCUPIED by " + occ : "FREE"));

        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║  GATES                                                       ║");
        gateOccupancy.forEach((gate, occ) ->
                System.out.printf("║    Gate %2d: %-51s║%n",
                        gate, occ != null ? "OCCUPIED by " + occ : "FREE"));

        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.printf("║  Landing Queue: %-3d | Takeoff Queue: %-21d║%n",
                landingQueue.size(), takeoffQueue.size());
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

    public void printAuditLog() {
        System.out.println("\n📋 ATC Audit Log ──────────────────────────────────────");
        auditLog.forEach(entry -> System.out.println("   " + entry));
        System.out.println("──────────────────────────────────────────────────────");
    }

    private void log(String event) {
        String entry = String.format("[%s] %s", towerName, event);
        auditLog.add(entry);
    }
}