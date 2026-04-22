package com.problems.learning.algo.backtracking.permutation;

import java.util.ArrayList;
import java.util.List;

public class StringPermutations {

    /**
     * Backtracking approach using swap technique.
     * "abc" → [abc, acb, bac, bca, cab, cba]
     *
     * Time: O(n! * n), Space: O(n!) for results
     */
    public List<String> permute(String str) {
        List<String> result = new ArrayList<>();
        backtrack(str.toCharArray(), 0, result);
        return result;
    }

    private void backtrack(char[] chars, int index, List<String> result) {
        if (index == chars.length) {
            result.add(new String(chars));
            return;
        }
        for (int i = index; i < chars.length; i++) {
            swap(chars, index, i);        // choose
            backtrack(chars, index + 1, result); // explore
            swap(chars, index, i);        // un-choose (backtrack)
        }
    }

    private void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    /**
     * Iterative approach — same logic as Permutations.permuteIteratively
     * but for strings. Inserts each character at every possible position.
     */
    public List<String> permuteIteratively(String str) {
        List<String> permutations = new ArrayList<>();
        permutations.add("");

        for (char ch : str.toCharArray()) {
            List<String> next = new ArrayList<>();
            for (String perm : permutations) {
                for (int i = 0; i <= perm.length(); i++) {
                    // Insert ch at position i
                    next.add(perm.substring(0, i) + ch + perm.substring(i));
                }
            }
            permutations = next;
        }
        return permutations;
    }

    /**
     * Backtracking using StringBuilder — avoids char[] and array copies.
     * Builds permutations by removing a char, appending to prefix, then restoring.
     *
     * "abc" → [abc, acb, bac, bca, cab, cba]
     */
    public List<String> permuteWithStringBuilder(String str) {
        List<String> result = new ArrayList<>();
        backtrackSB(new StringBuilder(str), new StringBuilder(), result);
        return result;
    }

    private void backtrackSB(StringBuilder remaining, StringBuilder prefix, List<String> result) {
        if (remaining.isEmpty()) {
            result.add(prefix.toString());
            return;
        }
        for (int i = 0; i < remaining.length(); i++) {
            char ch = remaining.charAt(i);

            // choose: remove from remaining, append to prefix
            remaining.deleteCharAt(i);
            prefix.append(ch);

            backtrackSB(remaining, prefix, result);

            // un-choose: restore
            prefix.deleteCharAt(prefix.length() - 1);
            remaining.insert(i, ch);
        }
    }
}
