package com.revision.algorithms;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GeneralAlgorithmsTest {

  GeneralAlgorithms instanceUnderTest;

  @BeforeEach
  void setUp() {
    this.instanceUnderTest = new GeneralAlgorithms();
  }

  @Test
  void test_calculate_age() {
    assertThat(this.instanceUnderTest.calculateAge(LocalDate.of(1967, 2, 5)))
        .isEqualTo(57);
    assertThat(this.instanceUnderTest.calculateAge(LocalDate.of(1961, 7, 14)))
        .isEqualTo(63);
    assertThat(this.instanceUnderTest.calculateAge(LocalDate.of(2099, 7, 14)))
        .isEqualTo(-1);
  }

  @Test
  void test_factorial() {
    assertThat(this.instanceUnderTest.factorial(5)).isEqualTo(120);
    assertThat(this.instanceUnderTest.factorial(1)).isEqualTo(1);
    assertThat(this.instanceUnderTest.factorial(0)).isEqualTo(1);
  }
}