package com.problems.learning.algo.math;

/**
 * Count Distinct Anagrams of a string s (LeetCode 2514).
 *
 * For each word, the number of distinct permutations is:
 *     word.length! / (freq[c1]! * freq[c2]! * ... * freq[cn]!)
 *
 * Multiply across all words, modulo 10^9 + 7.
 *
 * Division under modulo uses modular inverse: a/b mod p = a * b^(p-2) mod p
 * (Fermat's little theorem, since p is prime).
 *
 * Time: O(n) where n is total length of string
 * Space: O(n) for factorial precomputation
 */
public class CountDistinctAnagrams {

    private static final long MOD = 1_000_000_007;

    public int countAnagrams(String s) {
        String[] words = s.split(" ");
        int totalLen = s.length();

        // Precompute factorials up to totalLen
        long[] factorial = new long[totalLen + 1];
        factorial[0] = 1;
        for (int i = 1; i <= totalLen; i++) {
            factorial[i] = factorial[i - 1] * i % MOD;
        }

        long result = 1;

        for (String word : words) {
            int len = word.length();

            // Count frequency of each character
            int[] freq = new int[26];
            for (char c : word.toCharArray()) {
                freq[c - 'a']++;
            }

            // Numerator: len!
            long numerator = factorial[len];

            // Denominator: product of freq[c]! for all characters
            long denominator = 1;
            for (int f : freq) {
                if (f > 1) {
                    denominator = denominator * factorial[f] % MOD;
                }
            }

            // Division under modulo: numerator * modInverse(denominator)
            result = result * numerator % MOD;
            result = result * modInverse(denominator, MOD) % MOD;
        }

        return (int) result;
    }

    // Fermat's little theorem: a^(-1) mod p = a^(p-2) mod p (p must be prime)
    private long modInverse(long a, long mod) {
        return modPow(a, mod - 2, mod);
    }

    // Fast exponentiation: base^exp mod mod
    private long modPow(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = result * base % mod;
            }
            exp >>= 1;
            base = base * base % mod;
        }
        return result;
    }
}

