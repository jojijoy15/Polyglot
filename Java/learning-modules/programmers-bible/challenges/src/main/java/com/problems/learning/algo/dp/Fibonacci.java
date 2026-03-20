package com.problems.learning.algo.dp;

import java.util.HashMap;

public class Fibonacci {

    public int loop(int n) {
        int a = 0;
        int b = 1;
        for (int i = 0; i < n - 1; i++) {
            int sum = a + b;
            b = a;
            a = sum;
        }
        return a;
    }

    public int recursive(int n) {
       return fib(n - 1);
    }

    private int fib(int n) {
       if(n < 2) return n;
       return fib(n - 1) + fib(n - 2);
    }

    public int dp(int n) {
        HashMap<Integer, Integer> map = new HashMap<>();
        return dpFib(n - 1, map);
    }

    private int dpFib(int n,  HashMap<Integer, Integer> map) {
        if(n < 2) return n;
        if(map.containsKey(n))
            return map.get(n);
        else {
            int value = dpFib(n - 1, map) + dpFib(n - 2, map);
            map.put(n, value);
            return value;
        }
    }
}
