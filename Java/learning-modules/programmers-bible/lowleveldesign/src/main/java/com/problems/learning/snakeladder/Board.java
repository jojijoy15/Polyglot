package com.problems.learning.snakeladder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final int size;
    private final Map<Integer, BoardEntity> entityMap; // position → snake or ladder

    public Board(int size, List<Snake> snakes, List<Ladder> ladders) {
        this.size = size;
        this.entityMap = new HashMap<>();

        for (Snake snake : snakes) {
            validatePosition(snake.getStart(), "Snake head");
            validatePosition(snake.getEnd(), "Snake tail");
            if (entityMap.containsKey(snake.getStart())) {
                throw new IllegalArgumentException("Duplicate entity at position " + snake.getStart());
            }
            entityMap.put(snake.getStart(), snake);
        }

        for (Ladder ladder : ladders) {
            validatePosition(ladder.getStart(), "Ladder bottom");
            validatePosition(ladder.getEnd(), "Ladder top");
            if (entityMap.containsKey(ladder.getStart())) {
                throw new IllegalArgumentException("Duplicate entity at position " + ladder.getStart());
            }
            entityMap.put(ladder.getStart(), ladder);
        }
    }

    public int getSize() {
        return size;
    }

    // Returns the final position after applying any snake/ladder at the given position
    public int getFinalPosition(int position) {
        if (entityMap.containsKey(position)) {
            BoardEntity entity = entityMap.get(position);
            return entity.getEnd();
        }
        return position;
    }

    public boolean hasEntityAt(int position) {
        return entityMap.containsKey(position);
    }

    public BoardEntity getEntityAt(int position) {
        return entityMap.get(position);
    }

    private void validatePosition(int position, String label) {
        if (position < 1 || position > size) {
            throw new IllegalArgumentException(label + " position " + position + " is out of board range [1, " + size + "]");
        }
    }
}

