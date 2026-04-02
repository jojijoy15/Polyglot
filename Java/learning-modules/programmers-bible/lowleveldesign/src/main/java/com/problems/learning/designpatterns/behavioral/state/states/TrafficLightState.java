package com.problems.learning.designpatterns.behavioral.state.states;

import com.problems.learning.designpatterns.behavioral.state.machine.TrafficLight;

public interface TrafficLightState {
    String displaySignal();
    TrafficLightState next();
    int getDurationSeconds();
    String getStateName();
}