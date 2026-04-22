package com.problems.learning.system.snakeladder;

public abstract class BoardEntity {

    private final int start;
    private final int end;

    protected BoardEntity(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return getType() + " [" + start + " → " + end + "]";
    }
}

