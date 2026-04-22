package com.problems.learning.algo.string;

public class StringCompression {

    public String compress(String str) {
        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 1, count = 1; i < chars.length; i++) {
            while (i < chars.length && chars[i] == chars[i - 1]) {
                count++;
                i++;
            }
            sb.append(chars[i - 1]);
            sb.append(count);
            count = 1;
        }
        return sb.toString();
    }
}
