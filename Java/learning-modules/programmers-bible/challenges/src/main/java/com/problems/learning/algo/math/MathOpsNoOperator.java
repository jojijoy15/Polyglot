package com.problems.learning.algo.math;

public class MathOpsNoOperator {

    public int sum(int a, int b) {
        while (b != 0) {
            int carry = (a & b) << 1;
            a = a ^ b;
            b = carry;
        }
        return a;
    }

    public int subtract(int a, int b) {
        return sum(a, sum(~b, 1));
    }

    public int multiply(int a, int b) {
        int result = 0;

        while (b != 0) {
            if ((b & 1) != 0) {
                result = sum(result, a);
            }
            a <<= 1;
            b >>= 1;
        }

        return result;
    }

    public int divide(int dividend, int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Divide by zero");
        }

        int sign = ((dividend < 0) ^ (divisor < 0)) ? -1 : 1;

        long dvd = Math.abs((long) dividend);
        long dvs = Math.abs((long) divisor);

        int result = 0;

        while (dvd >= dvs) {
            long temp = dvs;
            int multiple = 1;

            while (dvd >= (temp << 1)) {
                temp <<= 1;
                multiple <<= 1;
            }

            dvd -= temp;
            result += multiple;
        }

        return sign * result;
    }
}
