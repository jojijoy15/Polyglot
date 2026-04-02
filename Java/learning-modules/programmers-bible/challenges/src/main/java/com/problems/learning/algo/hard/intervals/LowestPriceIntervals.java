package com.problems.learning.algo.hard.intervals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Get Lowest Prices Across Time Intervals.
 *
 * Given vendor offers (startTime, endTime, price), produce non-overlapping
 * intervals showing the lowest price at any point in time.
 *
 * Sweep Line approach:
 * 1. Create events: +price at startTime, -price at endTime
 * 2. Process events in time order using a TreeMap
 * 3. Between consecutive events, the minimum active price is TreeMap.firstKey()
 *
 * Time: O(n log n), Space: O(n)
 */
public class LowestPriceIntervals {

    // O(n log n) — sweep line with TreeMap of active prices
    public int[][] getLowestPrices(int[][] offers) {
        if (offers == null || offers.length == 0) return new int[0][];

        // Step 1: Build events — time → list of (price, +1 or -1)
        TreeMap<Integer, List<int[]>> events = new TreeMap<>();
        for (int[] offer : offers) {
            events.computeIfAbsent(offer[0], k -> new ArrayList<>()).add(new int[]{offer[2], 1});   // start: add price
            events.computeIfAbsent(offer[1], k -> new ArrayList<>()).add(new int[]{offer[2], -1});  // end: remove price
        }

        // Step 2: Sweep through events in time order
        TreeMap<Integer, Integer> activePrices = new TreeMap<>(); // price → frequency
        List<int[]> result = new ArrayList<>();
        int prevTime = -1;

        for (Map.Entry<Integer, List<int[]>> entry : events.entrySet()) {
            int time = entry.getKey();

            // Record interval [prevTime, time) with current minimum price
            if (prevTime != -1 && !activePrices.isEmpty()) {
                int minPrice = activePrices.firstKey();

                // Merge with previous interval if same price
                if (!result.isEmpty() && result.get(result.size() - 1)[2] == minPrice
                        && result.get(result.size() - 1)[1] == prevTime) {
                    result.get(result.size() - 1)[1] = time;
                } else {
                    result.add(new int[]{prevTime, time, minPrice});
                }
            }

            // Process all events at this time point
            for (int[] event : entry.getValue()) {
                int price = event[0];
                int delta = event[1];
                activePrices.merge(price, delta, Integer::sum);
                if (activePrices.getOrDefault(price, 0) == 0) {
                    activePrices.remove(price);
                }
            }

            prevTime = time;
        }

        return result.toArray(int[][]::new);
    }
}

