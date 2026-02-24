package recursion;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SimpleProblemsTest {

  SimpleProblems sp = new SimpleProblems();

  @Test
  void calculateLength() {
    final int length = sp.calculateLength("sample");
    assertThat(length).isEqualTo(6);
  }
}