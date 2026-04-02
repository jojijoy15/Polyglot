package com.problems.learning.designpatterns.behavioral.state.states;

import com.problems.learning.designpatterns.behavioral.state.machine.TrafficLight;

public class RedState implements TrafficLightState {

    @Override
    public String displaySignal() {
        return "🔴 RED    — Stop! Vehicles must wait.";
    }

    @Override
    public TrafficLightState next() {
        // Red → Yellow (on the way to Green)
        return new YellowState(YellowState.Direction.TO_GREEN);
    }

    @Override
    public int getDurationSeconds() { return 30; }

    @Override
    public String getStateName() { return "RED"; }
}