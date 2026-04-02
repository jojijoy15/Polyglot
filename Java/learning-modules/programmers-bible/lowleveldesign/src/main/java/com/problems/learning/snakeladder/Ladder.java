package com.problems.learning.snakeladder;

// Ladder: bottom (start) is lower, top (end) is higher — moves player UP
public class Ladder extends BoardEntity {

    public Ladder(int bottom, int top) {
        super(bottom, top);
        if (bottom >= top) {
            throw new IllegalArgumentException("Ladder bottom (" + bottom + ") must be less than top (" + top + ")");
        }
    }

    @Override
    public String getType() {
        return "Ladder";
    }
}

