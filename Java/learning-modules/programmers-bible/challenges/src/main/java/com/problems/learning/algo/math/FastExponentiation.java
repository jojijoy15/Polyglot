package com.problems.learning.algo.math;

public class FastExponentiation {

    public double pow(double a, int n) {
        // Convert to long to handle the edge case where n = Integer.MIN_VALUE
        // which cannot be negated as an int without overflowing.
        long N = n; 
        
        if (N < 0) {
            a = 1 / a;
            N = -N;
        }
        
        return fastPow(a, N);
    }

    private double fastPow(double a, long n) {
        // Base case
        if (n == 0) {
            return 1.0;
        }
        
        // Divide: compute the result for half the exponent
        double half = fastPow(a, n / 2);
        
        // Conquer: combine the results
        if (n % 2 == 0) {
            return half * half;     // Even
        } else {
            return half * half * a; // Odd
        }
    }

}