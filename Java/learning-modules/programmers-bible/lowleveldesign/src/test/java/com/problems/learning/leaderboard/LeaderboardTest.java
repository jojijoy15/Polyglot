package com.problems.learning.leaderboard;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class  LeaderboardTest {

    @Test
    void leetcodeExample() {
        Leaderboard lb = new Leaderboard();
        lb.addScore(1, 73);   // player 1 = 73
        lb.addScore(2, 56);   // player 2 = 56
        lb.addScore(3, 39);   // player 3 = 39
        lb.addScore(4, 51);   // player 4 = 51
        lb.addScore(5, 4);    // player 5 = 4

        assertThat(lb.top(1)).isEqualTo(73);        // top 1: 73
        assertThat(lb.top(3)).isEqualTo(180);       // top 3: 73+56+51 = 180

        lb.reset(1);          // remove player 1

        assertThat(lb.top(1)).isEqualTo(56);        // top 1: 56

        lb.addScore(2, 51);   // player 2 = 56+51 = 107

        assertThat(lb.top(3)).isEqualTo(197);       // top 3: 107+51+39 = 197
    }

    @Test
    void addScoreAccumulates() {
        Leaderboard lb = new Leaderboard();
        lb.addScore(1, 10);
        lb.addScore(1, 20);
        lb.addScore(1, 30);
        assertThat(lb.top(1)).isEqualTo(60);
    }

    @Test
    void resetRemovesPlayer() {
        Leaderboard lb = new Leaderboard();
        lb.addScore(1, 100);
        lb.addScore(2, 50);
        lb.reset(1);
        assertThat(lb.top(1)).isEqualTo(50);
    }

    @Test
    void topAllPlayers() {
        Leaderboard lb = new Leaderboard();
        lb.addScore(1, 10);
        lb.addScore(2, 20);
        lb.addScore(3, 30);
        assertThat(lb.top(3)).isEqualTo(60);
    }

    @Test
    void resetThenAddAgain() {
        Leaderboard lb = new Leaderboard();
        lb.addScore(1, 100);
        lb.reset(1);
        lb.addScore(1, 50);
        assertThat(lb.top(1)).isEqualTo(50);
    }

    @Test
    void singlePlayer() {
        Leaderboard lb = new Leaderboard();
        lb.addScore(1, 42);
        assertThat(lb.top(1)).isEqualTo(42);
    }

    // --- Tests verifying heap stays in sync across operations ---

    @Test
    void heapSyncsAfterMultipleAddsAndResets() {
        Leaderboard lb = new Leaderboard();
        lb.addScore(1, 50);
        lb.addScore(2, 80);
        lb.addScore(3, 60);

        assertThat(lb.top(2)).isEqualTo(140);  // 80 + 60

        lb.addScore(1, 40);   // player 1 = 90 (heap should remove 50, add 90)

        assertThat(lb.top(1)).isEqualTo(90);   // player 1 now highest
        assertThat(lb.top(2)).isEqualTo(170);  // 90 + 80

        lb.reset(1);           // remove player 1 (heap should remove 90)

        assertThat(lb.top(2)).isEqualTo(140);  // back to 80 + 60
    }

    @Test
    void topCalledConsecutivelyReturnsSameResult() {
        // Verifies poll + push-back keeps heap intact
        Leaderboard lb = new Leaderboard();
        lb.addScore(1, 30);
        lb.addScore(2, 20);
        lb.addScore(3, 10);

        assertThat(lb.top(2)).isEqualTo(50);   // first call: 30 + 20
        assertThat(lb.top(2)).isEqualTo(50);   // second call: same result
        assertThat(lb.top(2)).isEqualTo(50);   // third call: still same
    }

    @Test
    void addBetweenTopCalls() {
        Leaderboard lb = new Leaderboard();
        lb.addScore(1, 10);
        lb.addScore(2, 20);

        assertThat(lb.top(1)).isEqualTo(20);

        lb.addScore(3, 50);  // new highest player added between top() calls

        assertThat(lb.top(1)).isEqualTo(50);   // heap reflects new player
        assertThat(lb.top(2)).isEqualTo(70);   // 50 + 20
    }

    @Test
    void resetAllPlayers() {
        Leaderboard lb = new Leaderboard();
        lb.addScore(1, 10);
        lb.addScore(2, 20);
        lb.reset(1);
        lb.reset(2);

        assertThat(lb.top(1)).isEqualTo(0);    // heap is empty
    }

    @Test
    void resetNonExistentPlayerIsNoOp() {
        Leaderboard lb = new Leaderboard();
        lb.addScore(1, 50);
        lb.reset(999);  // player doesn't exist, should not affect anything

        assertThat(lb.top(1)).isEqualTo(50);
    }
}
