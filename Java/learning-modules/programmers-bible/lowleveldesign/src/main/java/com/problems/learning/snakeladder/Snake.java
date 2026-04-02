package com.problems.learning.snakeladder;

// Snake: head (start) is higher, tail (end) is lower — moves player DOWN
public class Snake extends BoardEntity {

    public Snake(int head, int tail) {
        super(head, tail);
        if (head <= tail) {
            throw new IllegalArgumentException("Snake head (" + head + ") must be greater than tail (" + tail + ")");
        }
    }

    @Override
    public String getType() {
        return "Snake";
    }
}

