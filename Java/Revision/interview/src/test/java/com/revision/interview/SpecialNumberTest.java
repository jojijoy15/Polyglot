package com.revision.interview;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class SpecialNumberTest {

  private final SpecialNumber instanceUnderTest = new SpecialNumber();

  @Test
  void test_find_all_special_numbers_present() {
    int[] numbers = {10, 43, 122, 73, 100};
    List<Boolean> result = instanceUnderTest.findSpecialNumbers(numbers);
    System.out.println(result);
    assertThat(result).containsExactly(true, false, true, false, true);
  }

}