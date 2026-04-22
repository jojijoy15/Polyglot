package com.problems.learning.system.leaderboard;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Design a Leaderboard.
 *
 * addScore(playerId, score) - O(log n) TreeMap put/remove
 * top(K)                    - O(K) iterate descending, no mutation
 * reset(playerId)           - O(log n) TreeMap remove
 *
 * TreeMap keeps scores sorted at all times.
 * top(K) simply iterates from highest — no poll/push-back needed.
 */
public class Leaderboard {

    private final Map<Integer, Integer> scores;           // playerId → totalScore
    private final TreeMap<Integer, Integer> sortedScores; // score → frequency

    public Leaderboard() {
        this.scores = new HashMap<>();
        this.sortedScores = new TreeMap<>();
    }

    // O(log n)
    public void addScore(int playerId, int score) {
        if (scores.containsKey(playerId)) {
            removeFromTreeMap(scores.get(playerId));
        }
        scores.merge(playerId, score, Integer::sum);
        sortedScores.merge(scores.get(playerId), 1, Integer::sum);
    }

    // O(K) — iterate from highest score, no mutation needed
    public int top(int k) {
        int sum = 0;
        int remaining = k;

        for (Map.Entry<Integer, Integer> entry : sortedScores.descendingMap().entrySet()) {
            int score = entry.getKey();
            int count = entry.getValue();

            int take = Math.min(remaining, count);
            sum += score * take;
            remaining -= take;

            if (remaining == 0) break;
        }

        return sum;
    }

    // O(log n)
    public void reset(int playerId) {
        if (scores.containsKey(playerId)) {
            removeFromTreeMap(scores.get(playerId));
            scores.remove(playerId);
        }
    }

    private void removeFromTreeMap(int score) {
        int count = sortedScores.getOrDefault(score, 0);
        if (count <= 1) {
            sortedScores.remove(score);
        } else {
            sortedScores.put(score, count - 1);
        }
    }
}
