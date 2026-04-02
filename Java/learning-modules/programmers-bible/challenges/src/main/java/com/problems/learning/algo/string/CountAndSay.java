package com.problems.learning.algo.string;

public class CountAndSay {

    /**
     * Returns the nth element of the count-and-say sequence.
     *
     * countAndSay(1) = "1"
     * countAndSay(n) = RLE of countAndSay(n-1)
     *
     * Example: n=4
     *   "1" → "11" → "21" → "1211"
     *
     * Time: O(n * m) where m is the length of the string at each level
     * Space: O(m) for the StringBuilder
     */
    public String countAndSay(int n) {
        String current = "1";

        for (int i = 2; i <= n; i++) {
            current = runLengthEncode(current);
        }

        return current;
    }

    private String runLengthEncode(String s) {
        StringBuilder sb = new StringBuilder();
        int i = 0;

        while (i < s.length()) {
            char ch = s.charAt(i);
            int count = 1;

            // Count consecutive identical characters
            while (i + count < s.length() && s.charAt(i + count) == ch) {
                count++;
            }

            sb.append(count).append(ch);
            i += count;
        }

        return sb.toString();
    }
}

