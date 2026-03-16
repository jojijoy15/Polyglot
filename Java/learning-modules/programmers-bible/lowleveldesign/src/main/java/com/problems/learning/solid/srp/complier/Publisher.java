package com.problems.learning.solid.srp.complier;

import java.util.HashMap;
import java.util.Map;

public class Publisher {

    private static final Map<String, Double> scoreBoard = new HashMap<>();

    public void publishResult(Interviewee interviewee) {
        scoreBoard.put(interviewee.getName(), interviewee.getScore());
    }
}
