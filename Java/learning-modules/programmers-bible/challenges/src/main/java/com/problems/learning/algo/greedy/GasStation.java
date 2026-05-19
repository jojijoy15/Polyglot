package com.problems.learning.algo.greedy;

public class GasStation {

    /*
        There are n gas stations along a circular route.
        You are given two integer arrays gas and cost of length n.
        At each gas station i, gas[i] represents the amount of gas you receive by stopping at this station,
         and cost[i] represents the amount of gas required to travel from station i to the next station.
        You begin the journey with an empty tank at one of the gas stations.

        Write a function to return the starting gas station's index if you can travel
         around the circuit once in the clockwise direction; otherwise, return -1.
        Note that if there exists a solution, it is guaranteed to be unique.
        Also, you can only travel from station i to station i + 1, and the last station will lead back to the first station.
    */
    public Integer canCompleteCircuit(int[] gas, int[] cost) {
        int length = gas.length;
        int totalGas=0, totalCost=0;
        for(int i = 0; i < length; ++i) {
            totalGas += gas[i];
            totalCost += cost[i];
        }

        if(totalGas < totalCost) {
            return -1;
        }

        int fuel = 0;
        int start = 0;
        for(int i = 0; i < length;++i) {
            if(gas[i] - cost[i] + fuel < 0) {
                start = i + 1;
                fuel = 0;
            } else {
                fuel += gas[i] - cost[i];
            }
        }
        return start;
    }
}