package com.problems.learning.designpatterns.behavioral.state.states;

public class GreenState implements TrafficLightState {

    @Override
    public String displaySignal() {
        return "🟢 GREEN  — Go! Vehicles may proceed.";
    }

    @Override
    public TrafficLightState next() {
        // Green → Yellow (on the way to Red)
        return new YellowState(YellowState.Direction.TO_RED);
    }

    @Override
    public int getDurationSeconds() {
        return 25;
    }

    @Override
    public String getStateName() {
        return "GREEN";
    }
}