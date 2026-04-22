package com.problems.learning.system.snakeladder;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SnakeLadderGameTest {

    // --- Entity Tests ---

    @Test
    void snakeHeadMustBeGreaterThanTail() {
        assertThatThrownBy(() -> new Snake(5, 20))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void ladderBottomMustBeLessThanTop() {
        assertThatThrownBy(() -> new Ladder(20, 5))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void snakeMovesPlayerDown() {
        Snake snake = new Snake(50, 10);
        assertThat(snake.getStart()).isEqualTo(50);
        assertThat(snake.getEnd()).isEqualTo(10);
        assertThat(snake.getType()).isEqualTo("Snake");
    }

    @Test
    void ladderMovesPlayerUp() {
        Ladder ladder = new Ladder(10, 50);
        assertThat(ladder.getStart()).isEqualTo(10);
        assertThat(ladder.getEnd()).isEqualTo(50);
        assertThat(ladder.getType()).isEqualTo("Ladder");
    }

    // --- Board Tests ---

    @Test
    void boardResolvesSnakePosition() {
        Board board = new Board(100,
                List.of(new Snake(50, 10)),
                List.of());
        assertThat(board.getFinalPosition(50)).isEqualTo(10);
    }

    @Test
    void boardResolvesLadderPosition() {
        Board board = new Board(100,
                List.of(),
                List.of(new Ladder(10, 50)));
        assertThat(board.getFinalPosition(10)).isEqualTo(50);
    }

    @Test
    void boardReturnsPositionWhenNoEntity() {
        Board board = new Board(100,
                List.of(new Snake(50, 10)),
                List.of(new Ladder(10, 50)));
        assertThat(board.getFinalPosition(25)).isEqualTo(25);
    }

    @Test
    void boardRejectsDuplicatePositions() {
        assertThatThrownBy(() -> new Board(100,
                List.of(new Snake(50, 10)),
                List.of(new Ladder(50, 80))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Duplicate");
    }

    @Test
    void boardRejectsOutOfRangePositions() {
        assertThatThrownBy(() -> new Board(100,
                List.of(new Snake(150, 10)),
                List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("out of board range");
    }

    // --- Dice Tests ---

    @Test
    void diceRollsWithinRange() {
        Dice dice = new Dice();
        for (int i = 0; i < 100; i++) {
            int roll = dice.roll();
            assertThat(roll).isBetween(1, 6);
        }
    }

    @Test
    @Disabled
    void twoDiceRollWithinRange() {
        Dice dice = new Dice();
        for (int i = 0; i < 100; i++) {
            int roll = dice.roll();
            assertThat(roll).isBetween(2, 12);
        }
    }

    // --- Player Tests ---

    @Test
    void playerStartsAtZero() {
        Player player = new Player("Alice");
        assertThat(player.getPosition()).isEqualTo(0);
        assertThat(player.getName()).isEqualTo("Alice");
    }

    // --- Game Tests ---

    @Test
    void gameRequiresAtLeastTwoPlayers() {
        Board board = new Board(100, List.of(), List.of());
        Dice dice = new Dice();
        assertThatThrownBy(() -> new SnakeLadderGame(board, dice, List.of(new Player("Solo"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("At least 2 players");
    }

    @Test
    void gameStartsAsNotStarted() {
        Board board = new Board(100, List.of(), List.of());
        Dice dice = new Dice();
        List<Player> players = List.of(new Player("Alice"), new Player("Bob"));
        SnakeLadderGame game = new SnakeLadderGame(board, dice, players);

        assertThat(game.getStatus()).isEqualTo(GameStatus.NOT_STARTED);
        assertThat(game.getWinner()).isNull();
    }

    @Test
    void gameChangesStatusOnFirstTurn() {
        Board board = new Board(100, List.of(), List.of());
        Dice dice = new Dice();
        List<Player> players = List.of(new Player("Alice"), new Player("Bob"));
        SnakeLadderGame game = new SnakeLadderGame(board, dice, players);

        game.playTurn();

        assertThat(game.getStatus()).isEqualTo(GameStatus.IN_PROGRESS);
    }

    @Test
    void gameProducesWinner() {
        // Use a small board (size 10) to finish quickly
        Board board = new Board(10,
                List.of(new Snake(9, 2)),
                List.of(new Ladder(3, 8)));
        Dice dice = new Dice();
        List<Player> players = List.of(new Player("Alice"), new Player("Bob"));
        SnakeLadderGame game = new SnakeLadderGame(board, dice, players);

        Player winner = game.play();

        assertThat(winner).isNotNull();
        assertThat(winner.getPosition()).isEqualTo(10);
        assertThat(game.getStatus()).isEqualTo(GameStatus.FINISHED);
    }

    @Test
    void fullGameWithMultiplePlayers() {
        List<Snake> snakes = List.of(
                new Snake(99, 12),
                new Snake(70, 30),
                new Snake(50, 5)
        );
        List<Ladder> ladders = List.of(
                new Ladder(2, 38),
                new Ladder(15, 55),
                new Ladder(42, 85)
        );
        Board board = new Board(100, snakes, ladders);
        Dice dice = new Dice();
        List<Player> players = List.of(
                new Player("Alice"),
                new Player("Bob"),
                new Player("Charlie")
        );
        SnakeLadderGame game = new SnakeLadderGame(board, dice, players);

        Player winner = game.play();

        assertThat(winner).isNotNull();
        assertThat(winner.getPosition()).isEqualTo(100);
        assertThat(game.getStatus()).isEqualTo(GameStatus.FINISHED);
        System.out.println("Winner: " + winner.getName());
    }
}

