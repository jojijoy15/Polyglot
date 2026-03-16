package com.problems.learning.algo.string;

public class RemoveDuplicates {

    public String removeDuplicates(String s) {
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
}