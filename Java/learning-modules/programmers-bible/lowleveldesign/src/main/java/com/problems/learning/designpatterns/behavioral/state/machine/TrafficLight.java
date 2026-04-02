package com.problems.learning.designpatterns.behavioral.state.machine;

import com.problems.learning.designpatterns.behavioral.state.states.RedState;
import com.problems.learning.designpatterns.behavioral.state.states.TrafficLightState;

public class TrafficLight {

    private TrafficLightState currentState;
    private int cycleCount;

    public TrafficLight() {
        this.currentState = new RedState(); // always starts at Red
        this.cycleCount = 0;
    }

    public void display() {
        System.out.printf("%-30s ⏱ %d seconds\n", currentState.displaySignal(),   // print handled inside displaySignal
                currentState.getDurationSeconds());
    }

    public void change() {
        TrafficLightState previous = currentState;
        currentState = currentState.next();
        cycleCount++;
        System.out.printf("   ↳ Transition #%-2d : %-20s → %s%n%n", cycleCount, previous.getStateName(), currentState.getStateName());
    }

    public void run(int cycles) {
        System.out.println("========== Traffic Light Started ==========\n");
        for (int i = 0; i < cycles; i++) {
            currentState.displaySignal();
            System.out.println("   ⏱ Duration : " + currentState.getDurationSeconds() + "s");
            change();
        }
        // Display final state
        currentState.displaySignal();
        System.out.println("   ⏱ Duration : " + currentState.getDurationSeconds() + "s");
        System.out.println("\n========== Total Transitions: " + cycleCount + " ==========");
    }
}
