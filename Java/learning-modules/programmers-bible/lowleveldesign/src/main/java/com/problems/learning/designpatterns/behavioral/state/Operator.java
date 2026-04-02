package com.problems.learning.designpatterns.behavioral.state;

import com.problems.learning.designpatterns.behavioral.state.machine.TrafficLight;

public class Operator {

    public static void main(String[] args) {
        TrafficLight light = new TrafficLight(); // starts at Red
        light.run(8);                            // run through 6 transitions
    }
}