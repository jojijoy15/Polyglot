package com.revision.collections.lists;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatricesStreamRevisionTest {

  private MatricesStreamRevision instanceUnderTest;

  @BeforeEach
  void setUp() {
    this.instanceUnderTest = new MatricesStreamRevision();
  }

  @Test
  void should_find_max_element_in_each_row_of_matrices() {
    Integer[][] matrices = {
      {1, 10, 4},
      {-11, -12, -14},
      {11, 100, 104}
    };
    List<Integer> maxElements = instanceUnderTest.findMaxElementInEachRow(matrices);
    assertThat(maxElements).containsAll(List.of(10, -11, 104));
  }

}