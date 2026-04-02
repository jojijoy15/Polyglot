package com.problems.learning.snakeladder;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Snake and Ladder Game.
 *
 * Rules:
 * 1. Players take turns rolling a dice and move forward by the rolled amount.
 * 2. If a player lands on the head of a snake, they slide down to the tail.
 * 3. If a player lands on the bottom of a ladder, they climb up to the top.
 * 4. If the roll would take a player beyond the board size, they stay in place.
 * 5. The first player to reach exactly the last cell wins.
 * 6. Minimum 2 players required.
 */
public class SnakeLadderGame {

    private final Board board;
    private final Dice dice;
    private final Queue<Player> players;
    private GameStatus status;
    private Player winner;

    public SnakeLadderGame(Board board, Dice dice, List<Player> players) {
        if (players == null || players.size() < 2) {
            throw new IllegalArgumentException("At least 2 players are required");
        }
        this.board = board;
        this.dice = dice;
        this.players = new LinkedList<>(players);
        this.status = GameStatus.NOT_STARTED;
        this.winner = null;
    }

    // Play the entire game until a winner is found
    public Player play() {
        status = GameStatus.IN_PROGRESS;

        while (status == GameStatus.IN_PROGRESS) {
            playTurn();
        }

        return winner;
    }

    // Play a single turn for the current player
    public void playTurn() {
        if (status == GameStatus.FINISHED) return;
        if (status == GameStatus.NOT_STARTED) status = GameStatus.IN_PROGRESS;

        Player current = players.poll();
        int rolled = dice.roll();
        int oldPosition = current.getPosition();
        int newPosition = oldPosition + rolled;

        if (newPosition > board.getSize()) {
            // Overshoot — stay in place
            System.out.println(current.getName() + " rolled " + rolled
                    + " but stays at " + oldPosition + " (would exceed " + board.getSize() + ")");
            players.offer(current);
            return;
        }

        // Check for snake or ladder at the new position
        int finalPosition = board.getFinalPosition(newPosition);

        if (finalPosition != newPosition) {
            BoardEntity entity = board.getEntityAt(newPosition);
            System.out.println(current.getName() + " rolled " + rolled
                    + ": " + oldPosition + " → " + newPosition
                    + " → hit " + entity + " → moved to " + finalPosition);
        } else {
            System.out.println(current.getName() + " rolled " + rolled
                    + ": " + oldPosition + " → " + finalPosition);
        }

        current.setPosition(finalPosition);

        // Check for win
        if (finalPosition == board.getSize()) {
            winner = current;
            status = GameStatus.FINISHED;
            System.out.println("🎉 " + current.getName() + " wins!");
            return;
        }

        players.offer(current);
    }

    public GameStatus getStatus() {
        return status;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getCurrentPlayer() {
        return players.peek();
    }
}

