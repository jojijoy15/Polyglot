package com.problems.learning.algo.string;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountAndSayTest {

    CountAndSay countAndSay = new CountAndSay();

    @Test
    void countAndSay4() {
        String countAndSay = this.countAndSay.countAndSay(4);
        assertThat(countAndSay).isEqualTo("1211");

    }

    /*
       [count][element]...
        1
        11
        21
        1211
        111221
        312211
     */
    @Test
    void countAndSay6() {
        String countAndSay = this.countAndSay.countAndSay(6);
        assertThat(countAndSay).isEqualTo("312211");

    }
}