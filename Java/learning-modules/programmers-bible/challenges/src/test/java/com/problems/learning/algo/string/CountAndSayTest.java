package com.problems.learning.algo.string;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CountAndSayTest {

    CountAndSay countAndSay = new CountAndSay();

    @Test
    void countAndSay() {
        String countAndSay = this.countAndSay.countAndSay(4);
        assertThat(countAndSay).isEqualTo("1211");

    }
}