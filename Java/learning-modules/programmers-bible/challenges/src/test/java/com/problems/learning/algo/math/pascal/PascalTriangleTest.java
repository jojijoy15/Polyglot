package com.problems.learning.algo.math.pascal;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PascalTriangleTest {

    PascalTriangle triangle = new PascalTriangle();

    @Test
    void testPascalTriangleSmall() {
        int row = 3;
        int col = 2;

        int element = triangle.nthRow(row, col);
        assertThat(element).isEqualTo(3);
    }

    @Test
    void testPascalTriangleLarge() {
        int row = 10;
        int col = 5;

        int element = triangle.nthRow(row, col);
        assertThat(element).isEqualTo(252);
    }


}