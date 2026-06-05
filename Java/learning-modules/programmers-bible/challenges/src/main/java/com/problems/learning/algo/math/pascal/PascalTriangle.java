package com.problems.learning.algo.math.pascal;

import com.problems.learning.tags.Medium;

import java.util.ArrayList;
import java.util.List;

@Medium
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

    /**
     * Print Pascal's Triangle for a given number of rows.
     * Each row is computed using the previous row:
     *   row[j] = prevRow[j-1] + prevRow[j]
     *
     * Example for numRows = 5:
     *     [1]
     *    [1,1]
     *   [1,2,1]
     *  [1,3,3,1]
     * [1,4,6,4,1]
     */
    public List<List<Integer>> generateTriangle(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            row.add(1); // first element is always 1

            if (i > 0) {
                List<Integer> prevRow = triangle.get(i - 1);
                for (int j = 1; j < i; j++) {
                    row.add(prevRow.get(j - 1) + prevRow.get(j));
                }
                row.add(1); // last element is always 1
            }

            triangle.add(row);
        }

        return triangle;
    }
}
