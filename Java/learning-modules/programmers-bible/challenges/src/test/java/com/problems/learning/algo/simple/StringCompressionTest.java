package com.problems.learning.algo.simple;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StringCompressionTest {

    StringCompression compression = new StringCompression();

    @Test
    void compressDigits() {
        String str = "122222";
        String compressed = compression.compress(str);
        assertThat(compressed).isEqualTo("1125");
    }

    @Test
    void compressAlpha() {
        String str = "aaabbbccc";
        String compressed = compression.compress(str);
        assertThat(compressed).isEqualTo("a3b3c3");
    }
}