package com.problems.learning.designpatterns.behavioral.state.states;

public class YellowState implements TrafficLightState {

    // Tracks which direction Yellow is bridging
    public enum Direction {
        TO_GREEN,   // came from Red,   going to Green
        TO_RED      // came from Green, going to Red
    }

    private final Direction direction;

    public YellowState(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String displaySignal() {
        if (direction == Direction.TO_GREEN) {
            return "🟡 YELLOW — Get ready! Signal turning Green soon.";
        } else {
            return "🟡 YELLOW — Slow down! Signal turning Red soon.";
        }
    }

    @Override
    public TrafficLightState next() {
        // Yellow decides next state based on direction it came from
        return switch (direction) {
            case TO_GREEN -> new GreenState();   // Red → Yellow → Green
            case TO_RED -> new RedState();     // Green → Yellow → Red
        };
    }

    @Override
    public int getDurationSeconds() {
        return 5;
    }

    @Override
    public String getStateName() {
        return "YELLOW(" + direction + ")";
    }
}