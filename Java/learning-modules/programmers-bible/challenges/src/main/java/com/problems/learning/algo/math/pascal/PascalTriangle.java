package com.problems.learning.algo.math.pascal;

public class PascalTriangle {

    public int nthRow(int r, int c) {

        int[] row = new int[ r + 1];
        row[0] = 1;
        int val = 1;

        /*
           k = 1
           r = 3
           val = 1
           r - k + 1 = 3
           val = 1 * 3 / 1 = 3

           k = 2
           r = 3
           val = 3
           r - k + 1 = 2
           val = 3 * 2 / 2 = 3

           k = 3
           r = 3
           val = 3
           r - k + 1 = 1
           val = 1 * 3 / 3 = 1

           R = 3, ans = [ 1 3 3 1 ]
         */
        for (int k = 1; k <= r; k++) {
            val = val * (r - k + 1) / k;  // Calculate binomial coefficient formula without factorial
            row[k] = val;
        }
        return row[c];
    }
}
