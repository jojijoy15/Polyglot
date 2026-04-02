package com.problems.learning.algo.string;

public class RemoveDuplicates {

    public String removeConsecutiveDuplicates(String s) {
        StringBuilder sb = new StringBuilder(s);
        removeRecursively(sb, sb.length());
        return sb.toString();
    }

    private void removeRecursively(StringBuilder s, int totalLength) {
        int k = 0;
        for(int i = 0; i < totalLength; i++) {
            if(i < totalLength - 1 && s.charAt(i) == s.charAt(i + 1)) {
                i++;
            } else {
                s.setCharAt(k++, s.charAt(i));
            }
        }
        s.setLength(k);
        if(k != totalLength) {
            removeRecursively(s, k);
        }
    }

    // O(n²) time, O(n) space — iterative loop with cascading (replaces recursion with while)
    public String removeConsecutiveDuplicatesIteratively(String s) {
        if (s == null || s.isEmpty()) return "";

        StringBuilder sb = new StringBuilder(s);
        boolean found = true;

        while (found) {
            found = false;
            int k = 0;
            for (int i = 0; i < sb.length(); i++) {
                if (i < sb.length() - 1 && sb.charAt(i) == sb.charAt(i + 1)) {
                    i++;
                    found = true;
                } else {
                    sb.setCharAt(k++, sb.charAt(i));
                }
            }
            sb.setLength(k);
        }

        return sb.toString();
    }

    // O(n) time, O(n) space — single pass using stack with cascading
    public String removeConsecutiveDuplicatesUsingStack(String s) {
        if (s == null || s.isEmpty()) return "";

        java.util.Deque<Character> stack = new java.util.ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (!stack.isEmpty() && stack.peek() == c) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        StringBuilder result = new StringBuilder();
        for (char c : stack) {
            result.append(c);
        }
        return result.reverse().toString();
    }

    // O(n) time, O(1) extra space — use char array itself as a stack
    public String removeDuplicatesInPlace(String s) {
        if (s == null || s.isEmpty()) return "";

        char[] chars = s.toCharArray();
        int k = 0; // stack top pointer
        for (char c : chars) {
            if (k > 0 && chars[k - 1] == c) {
                k--;  // pop — same as stack top, remove both
            } else {
                chars[k++] = c;  // push — write at stack top
            }
        }

        return new String(chars, 0, k);
    }
}