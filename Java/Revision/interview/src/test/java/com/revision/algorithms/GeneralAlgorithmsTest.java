package com.revision.algorithms;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
    assertThat(instanceUnderTest.calculateAge(LocalDate.of(1967, 2, 5)))
        .isEqualTo(57);
    assertThat(instanceUnderTest.calculateAge(LocalDate.of(1961, 7, 14)))
        .isEqualTo(63);
    assertThat(instanceUnderTest.calculateAge(LocalDate.of(2099, 7, 14)))
        .isEqualTo(-1);
  }

  @Test
  void test_factorial() {
    assertThat(instanceUnderTest.factorial(5)).isEqualTo(120);
    assertThat(instanceUnderTest.factorial(1)).isEqualTo(1);
    assertThat(instanceUnderTest.factorial(0)).isEqualTo(1);
  }

  @Test
  void test_binary_of_integer() {
    assertThat(instanceUnderTest.binaryOfInteger(5)).isEqualTo("00000000000000000000000000000101");
    assertThat(instanceUnderTest.binaryOfInteger(0)).isEqualTo("00000000000000000000000000000000");
    assertThat(instanceUnderTest.binaryOfInteger(-0)).isEqualTo("00000000000000000000000000000000");
    assertThat(instanceUnderTest.binaryOfInteger(-5)).isEqualTo("11111111111111111111111111111011");
  }
}