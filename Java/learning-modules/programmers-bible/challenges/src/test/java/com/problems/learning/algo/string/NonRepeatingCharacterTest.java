package com.problems.learning.algo.string;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NonRepeatingCharacterTest {

    NonRepeatingCharacter nr = new NonRepeatingCharacter();

    @Test
    void findNonRepeatingCharacter() {
        Character uniqueCharacter = nr.findNonRepeatingCharacter("abaccd");
        assertThat(uniqueCharacter).isEqualTo('b');
    }
}