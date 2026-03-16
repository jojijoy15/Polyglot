package com.problems.learning.algo.simple;

import java.util.ArrayList;
import java.util.List;

public class PrimeFactors {

    // O(√X) time | O(log X) space — at most log₂(X) prime factors
    public static List<Integer> primeFactors(int x) {
        List<Integer> factors = new ArrayList<>();

        if (x <= 1) return factors; // 0 and 1 have no prime factors

        // Step 1: Divide out all 2s first (handles even numbers fast)
        while (x % 2 == 0) {
            factors.add(2);
            x /= 2;
        }

        // Step 2: Try odd divisors from 3 up to √x
        // No need to check even numbers — already removed all 2s
        for (int i = 3; i * i <= x; i += 2) {
            while (x % i == 0) {
                factors.add(i);
                x /= i;
            }
        }

        // Step 3: If x > 1 here, it's a prime factor itself
        // e.g. x=13 won't divide by anything up to √13 ≈ 3.6
        if (x > 1) {
            factors.add(x);
        }

        return factors;
    }
}