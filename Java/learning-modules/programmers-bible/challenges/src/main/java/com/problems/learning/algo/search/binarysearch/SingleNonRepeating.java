package com.problems.learning.algo.search.binarysearch;

public class SingleNonRepeating {

    /*
        Given a sorted array where every element appears exactly twice except one,
        find the single non-repeating element.

        All duplicates are adjacent.

        Input:  [2, 2, 4, 4, 8, 9, 9]
        Output: 8

        Key Insight:
        Before the single element → pairs start at EVEN index: (0,1), (2,3), (4,5)
        After  the single element → pairs start at ODD  index: (5,6), (7,8)

        At even mid:
          arr[mid] == arr[mid+1] → pair intact → single is RIGHT
          arr[mid] != arr[mid+1] → pair broken → single is LEFT or at mid

        Time: O(log n), Space: O(1)
     */
    public int findSingle(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            // Force mid to be even so we can compare mid with mid+1
            if (mid % 2 == 1) {
                mid--;
            }

            if (arr[mid] == arr[mid + 1]) {
                // Pair is intact at (mid, mid+1) → single element is to the right
                left = mid + 2;
            } else {
                // Pair is broken → single element is at mid or to the left
                right = mid;
            }
        }

        return arr[left];
    }
}

