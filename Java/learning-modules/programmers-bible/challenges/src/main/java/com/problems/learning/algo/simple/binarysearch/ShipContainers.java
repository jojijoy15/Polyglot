package com.problems.learning.algo.simple.binarysearch;

import java.util.Arrays;

public class ShipContainers {

    //Intuition use binary Search
    public int minCapacityOfShip(int[] weights, int days) {
        int minCapacity = Arrays.stream(weights).max().getAsInt();
        int maxCapacity = Arrays.stream(weights).sum();

        while (minCapacity <= maxCapacity) {
            int currentCapacity = (minCapacity + maxCapacity) / 2;
            if (findDays(currentCapacity, weights) <= days) {
                maxCapacity = currentCapacity - 1;
            } else {
                minCapacity = currentCapacity + 1;
            }
        }
        return minCapacity;
    }

    private int findDays(int cap, int[] weights) {
        int load = 0, days = 1;
        for (int i = 0; i < weights.length; i++) {
            if(weights[i] + load > cap) {
                days++;
                load = weights[i];
            } else {
                load += weights[i];
            }
        }
        return days;
    }
}
