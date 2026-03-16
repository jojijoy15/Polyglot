package com.problems.learning.solid.srp.complier;

import java.util.List;

public class Evaluator {

    public double evaluate(Interviewee interviewee, List<Integer> parameters) {
        return parameters.stream()
                .mapToInt(e -> e).average().getAsDouble();
    }
}
