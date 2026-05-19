package com.problems.learning.algo.twopointer;

import com.problems.learning.tags.Hard;
import com.problems.learning.tags.Medium;

@Medium
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

    @Hard
    public String longestPalindromeDP(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int start = 0, maxLen = 1;

        // every single char is a palindrome
        for (int i = 0; i < n; i++) dp[i][i] = true;

        // check substrings of length 2 to n
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;

                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = (len == 2) || dp[i + 1][j - 1];
                }

                if (dp[i][j] && len > maxLen) {
                    maxLen = len;
                    start = i;
                }
            }
        }
        return s.substring(start, start + maxLen);
    }
}
