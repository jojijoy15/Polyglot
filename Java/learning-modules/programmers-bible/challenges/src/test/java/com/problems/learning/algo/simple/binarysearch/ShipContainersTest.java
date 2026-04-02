package com.problems.learning.algo.simple.binarysearch;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ShipContainersTest {

    ShipContainers shipContainers = new ShipContainers();

    @Test
    void minCapacityOfShipEven() {
        int[] weights = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        int capacity = shipContainers.minCapacityOfShip(weights, 5);
        assertThat(capacity).isEqualTo(15);
    }

    @Test
    void minCapacityOfShipOdd() {
        int[] weights = new int[] {3,2,2,4,1};
        int capacity = shipContainers.minCapacityOfShip(weights, 2);
        assertThat(capacity).isEqualTo(7);
    }

}