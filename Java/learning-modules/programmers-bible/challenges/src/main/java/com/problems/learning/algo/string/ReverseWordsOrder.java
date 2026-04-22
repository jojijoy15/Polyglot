package com.problems.learning.algo.string;

public class ReverseWordsOrder {

    // O(n) time, O(n) space — three-step in-place reverse, no split/trim
    public String reverseWords(String s) {
        if (s == null || s.isEmpty()) return "";

        char[] chars = s.toCharArray();
        int n = chars.length;

        // Step 1: Reverse entire string
        reverse(chars, 0, n - 1);

        // Step 2: Reverse each word back
        int start = 0;
        for (int i = 0; i <= n; i++) {
            if (i == n || chars[i] == ' ') {
                reverse(chars, start, i - 1);
                start = i + 1;
            }
        }

        // Step 3: Clean up spaces (leading, trailing, multiple)
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (chars[i] != ' ') {
                if (k > 0 && chars[k - 1] != ' ' && chars[i - 1] == ' ') {
                    chars[k++] = ' ';  // single space before new word
                }
                chars[k++] = chars[i];
            }
        }

        // Remove trailing space if any
        if (k > 0 && chars[k - 1] == ' ') k--;

        return new String(chars, 0, k);
    }

    private void reverse(char[] chars, int left, int right) {
        while (left < right) {
            char temp = chars[left];
            chars[left++] = chars[right];
            chars[right--] = temp;
        }
    }
}
