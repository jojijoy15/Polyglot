package com.problems.learning.algo.simple;

public class FractionAdder {

    // Euclidean algorithm: GCD(48,18) → GCD(18,12) → GCD(12,6) → GCD(6,0) = 6
    static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // LCM via GCD: LCM(4,6) = (4*6) / GCD(4,6) = 24/2 = 12
    static int lcm(int a, int b) {
        return Math.abs(a * b) / gcd(a, b);
    }

    // ── Main function ──────────────────────────────────────────
    public static int[] addFractions(int[] f1, int[] f2) {
        int num1 = f1[0], den1 = f1[1];
        int num2 = f2[0], den2 = f2[1];

        // Step 1: Find common denominator
        int commonDen = lcm(den1, den2);

        // Step 2: Scale numerators to common denominator
        int scaledNum1 = num1 * (commonDen / den1);
        int scaledNum2 = num2 * (commonDen / den2);

        // Step 3: Add numerators
        int resultNum = scaledNum1 + scaledNum2;
        int resultDen = commonDen;

        // Step 4: Simplify by dividing both by GCD
        int common = gcd(resultNum, resultDen);
        resultNum /= common;
        resultDen /= common;

        // Convention: keep denominator positive
        if (resultDen < 0) {
            resultNum = -resultNum;
            resultDen = -resultDen;
        }

        return new int[]{resultNum, resultDen};
    }
}