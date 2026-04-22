package com.problems.learning.designpatterns.behavioral.state.states;

public interface TrafficLightState {
    String displaySignal();

    TrafficLightState next();

    int getDurationSeconds();

    String getStateName();
}