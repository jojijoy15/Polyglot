package com.problems.learning.designpatterns.behavioral.mediator.client;

import com.problems.learning.designpatterns.behavioral.mediator.aircraft.CargoPlane;
import com.problems.learning.designpatterns.behavioral.mediator.aircraft.CommercialFlight;
import com.problems.learning.designpatterns.behavioral.mediator.aircraft.Helicopter;
import com.problems.learning.designpatterns.behavioral.mediator.aircraft.PrivateJet;
import com.problems.learning.designpatterns.behavioral.mediator.tower.ATCTower;

public class AirCraftRunwayManagementApp {

    public static void main(String[] args) throws InterruptedException {

        // ── Create the Mediator ───────────────────────────────────────
        ATCTower tower = new ATCTower("Mumbai ATC", 3, 5);

        // ── Register Aircraft (Colleagues) ───────────────────────────
        // Aircraft register themselves on construction — controller never does it manually
        CommercialFlight ai101 = new CommercialFlight("AI-101", 8000, 45, 280, tower);
        CommercialFlight ai202 = new CommercialFlight("AI-202", 6000, 30, 190, tower);
        CargoPlane fx501 = new CargoPlane("FX-501", 9000, 60, 42.5, tower);
        PrivateJet vt900 = new PrivateJet("VT-900", 5000, 55, "Ambani", tower);
        Helicopter heli1 = new Helicopter("HE-001", 1500, 80, false, tower);
        Helicopter medic1 = new Helicopter("MED-911", 2000, 70, true, tower);

        tower.printStatusBoard();

        // ── Scenario 1: Multiple simultaneous landing requests ────────
        System.out.println("\n═══════ Scenario 1: Multiple Landing Requests ═══════\n");
        // All request at once — tower queues and prioritizes
        ai101.requestLanding();   // priority: 40 + 50 (fuel 30%) = 90
        ai202.requestLanding();   // priority: 40 + 50 (fuel 30%) = 90
        fx501.requestLanding();   // priority: 25
        vt900.requestLanding();   // priority: 10

        tower.printStatusBoard();

        // ── Scenario 2: Direct aircraft-to-aircraft messaging ─────────
        System.out.println("\n═══════ Scenario 2: Aircraft Communication ═══════\n");
        // Aircraft communicate via call sign only — no direct references
        ai101.sendMessageTo("AI-202", "Be aware of turbulence at 7,000ft");
        vt900.sendMessageTo("FX-501", "Request reduced wake turbulence separation");
        ai202.sendMessageTo("UNKNOWN-999", "Any traffic on final?"); // unknown call sign

        // ── Scenario 3: Takeoff requests ──────────────────────────────
        System.out.println("\n═══════ Scenario 3: Takeoff Sequence ═══════\n");
        ai101.requestTakeoff();
        fx501.requestTakeoff();

        // ── Scenario 4: Medical helicopter requests landing ───────────
        System.out.println("\n═══════ Scenario 4: Medical Helicopter ═══════\n");
        medic1.requestLanding();  // medical helicopter — highest priority

        // ── Scenario 5: Emergency declaration ─────────────────────────
        System.out.println("\n═══════ Scenario 5: Emergency Declaration ═══════\n");
        // AI-202 declares engine failure — tower broadcasts to ALL aircraft
        // No aircraft knows about each other — tower handles everything
        ai202.declareEmergency("Engine failure — fuel critically low");

        tower.printStatusBoard();

        // ── Scenario 6: Helicopter using helipad ──────────────────────
        System.out.println("\n═══════ Scenario 6: Helicopter Operations ═══════\n");
        heli1.requestLanding();
        heli1.requestTakeoff();

        // ── Final audit log ───────────────────────────────────────────
        tower.printAuditLog();
    }
}