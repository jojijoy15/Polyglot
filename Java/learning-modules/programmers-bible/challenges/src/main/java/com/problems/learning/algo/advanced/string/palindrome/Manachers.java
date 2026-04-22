package com.problems.learning.algo.advanced.string.palindrome;

public class Manachers {

    /**
     * Note:
     * Linear time Manacher's algorithm to find longest palindromic substring.
     * There are 4 cases to handle
     * Case 1 : Right side palindrome is totally contained under current palindrome. In this case do not consider this as center.
     * Case 2 : Current palindrome is proper suffix of input. Terminate the loop in this case. No better palindrom will be found on right.
     * Case 3 : Right side palindrome is proper suffix and its corresponding left side palindrome is proper prefix of current palindrome. Make largest such point as
     * next center.
     * Case 4 : Right side palindrome is proper suffix but its left corresponding palindrome is be beyond current palindrome. Do not consider this
     * as center because it will not extend at all.
     * To handle even size palindromes replace input string with one containing $ between every input character and in start and end.
     */
    public int longestPalindrome(String s) {
        char[] manacher = new char[2 * s.length() + 1];
        int index = 0;
        // Handle the odd length of string
        for (int i = 0; i < manacher.length; i++) {
            if (i % 2 == 1) {
                manacher[i] = s.charAt(index++);
            } else {
                manacher[i] = '$';
            }
        }
        int[] palLen = new int[manacher.length];
        int start = 0;
        int end = 0;
        for (int i = 0; i < manacher.length; ) {
            // Expand around center
            while (start > 0 && end < manacher.length - 1 && manacher[start - 1] == manacher[end + 1]) {
                start--;
                end++;
            }
            palLen[i] = end - start + 1;
            if (end == palLen.length - 1) {
                break;
            }
            int newCenter = end + (i % 2 == 0 ? 1 : 0);

            for(int j = i + 1; j <= end; j++) {

                //i - (j - i) is left mirror. Its possible left mirror might go beyond current center palindrome. So take minimum
                //of either left side palindrome or distance of j to end.
                palLen[j] = Math.min(palLen[i - (j - i)], 2 * (end - j) + 1);
                //Only proceed if we get case 3. This check is to make sure we do not pick j as new center for case 1 or case 4
                //As soon as we find a center lets break out of this inner while loop.
                if(j + palLen[i - (j - i)]/2 == end) {
                    newCenter = j;
                    break;
                }
                i = newCenter;
                end = i + palLen[i]/2;
                start = i - palLen[i]/2;
            }
        }

        int max = Integer.MIN_VALUE;
        for(int i = 0; i < palLen.length; i++) {
            int val = palLen[i]/2;
            max = Math.max(max, val);
        }
        return max;
    }
}
