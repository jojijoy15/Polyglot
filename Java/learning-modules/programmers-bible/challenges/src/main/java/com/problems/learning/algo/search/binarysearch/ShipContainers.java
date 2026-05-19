package com.problems.learning.algo.search.binarysearch;

import java.util.Arrays;

public class ShipContainers {

    //Intuition use binary Search
    public int minCapacityOfShip(int[] weights, int days) {
        int minCapacity = Arrays.stream(weights).max().orElse(0);
        int maxCapacity = Arrays.stream(weights).sum();

        while (minCapacity <= maxCapacity) {
            int currentCapacity = (minCapacity + maxCapacity) / 2;
            if (findDays(currentCapacity, weights, days)) { // Ship can finish in time → try a smaller ship → shrink right
                maxCapacity = currentCapacity - 1;
            } else {                                          // Ship is too small, takes too many days → need a bigger ship → shrink left
                minCapacity = currentCapacity + 1;
            }
        }
        return minCapacity;
    }

    private boolean findDays(int cap, int[] weights, int givenDays) {
        int load = 0, days = 1;
        for (int weight : weights) {
            load += weight;
            if ( load > cap) {
                days++;
                load = weight;
            }
        }
        return days <= givenDays;
    }
}
