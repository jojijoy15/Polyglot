package com.problems.learning.designpatterns.behavioral.command.client;

import com.problems.learning.designpatterns.behavioral.command.commands.*;
import com.problems.learning.designpatterns.behavioral.command.controllers.HomeController;
import com.problems.learning.designpatterns.behavioral.command.receivers.DoorLock;
import com.problems.learning.designpatterns.behavioral.command.receivers.Light;
import com.problems.learning.designpatterns.behavioral.command.receivers.Speaker;
import com.problems.learning.designpatterns.behavioral.command.receivers.Thermostat;

public class SmartHomeClient {

    public static void main(String[] args) {

        // ── Devices (Receivers) ───────────────────────────────────────
        Light livingRoomLight = new Light("Living Room");
        Light bedroomLight = new Light("Bedroom");
        Thermostat thermostat = new Thermostat("Main");
        DoorLock frontDoor = new DoorLock("Front");
        Speaker loungeSpeak = new Speaker("Lounge");

        // ── Invoker ───────────────────────────────────────────────────
        HomeController controller = new HomeController();

        // ── Scenario 1: Individual commands ───────────────────────────
        System.out.println("========== Scenario 1: Individual Commands ==========");
        controller.execute(new LightOnCommand(livingRoomLight));
        controller.execute(new SetTemperatureCommand(thermostat, 22.0));
        controller.execute(new UnlockDoorCommand(frontDoor));
        controller.execute(new PlayMusicCommand(loungeSpeak, "Chill Vibes"));

        // ── Scenario 2: Undo / Redo ───────────────────────────────────
        System.out.println("\n========== Scenario 2: Undo / Redo ==========");
        controller.undo();   // undo PlayMusic
        controller.undo();   // undo UnlockDoor
        controller.redo();   // redo UnlockDoor

        // ── Scenario 3: Macros ────────────────────────────────────────
        System.out.println("\n========== Scenario 3: Macros ==========");

        MacroCommand goodNight = new MacroCommand("Good Night")
                .add(new LightOffCommand(livingRoomLight))
                .add(new LightOffCommand(bedroomLight))
                .add(new SetTemperatureCommand(thermostat, 18.0))
                .add(new LockDoorCommand(frontDoor));

        controller.execute(goodNight);

        // Undo the entire macro in one call
        System.out.println();
        controller.undo();

        // ── Scenario 4: Scheduled commands ───────────────────────────
        System.out.println("\n========== Scenario 4: Scheduled Commands ==========");
        controller.schedule(new DimLightCommand(bedroomLight, 30));
        controller.schedule(new SetTemperatureCommand(thermostat, 19.0));
        controller.runScheduled();

        // ── Audit log ─────────────────────────────────────────────────
        controller.printAuditLog();
    }
}