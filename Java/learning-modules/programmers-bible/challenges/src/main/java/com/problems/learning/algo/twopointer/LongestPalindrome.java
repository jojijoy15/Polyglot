package com.problems.learning.algo.twopointer;

public class LongestPalindrome {
    /*
        Middle out pattern
        Space: O(1)
        Time: O(n²)

        {@link com.problems.learning.algo.advanced.palindrome.ManachersLongestPalindrome.java Manacher's}
     */
    public int[] getLongestPalindrome(String s) {
        //Even 10 =>  0 1 2 3 4 5 6 7 8 9
        //Odd 9 => 0 1 2 3 4 5 6 7 8

        int longestPalindromeLength = 0;
        int longestPalindromeStart = 0;
        for (int i = 0; i < s.length(); i++) {
            //Even Length
            int left = i;
            int right = i + 1;
            int[] even = checkPalindrome(s, left, right, longestPalindromeLength, longestPalindromeStart);
            longestPalindromeStart = even[0];
            longestPalindromeLength = even[1];
            // Odd Length
            left = i;
            right = i;
            int[] odd = checkPalindrome(s, left, right, longestPalindromeLength, longestPalindromeStart);
            longestPalindromeStart = odd[0];
            longestPalindromeLength = odd[1];
        }
        return new int[]{longestPalindromeStart, longestPalindromeLength};

    }

    private int[] checkPalindrome(String s, int left, int right, int longestPalindromeLength, int longestPalindromeStart) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            if(right - left + 1 > longestPalindromeLength) {
                longestPalindromeLength = (right - left + 1);
                longestPalindromeStart = left;
            }
            left--;
            right++;
        }
        return new int[]{longestPalindromeStart, longestPalindromeLength};
    }
}
