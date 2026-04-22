package recursion;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleProblemsTest {

  SimpleProblems sp = new SimpleProblems();

  @Test
  void calculateLength() {
    final int length = sp.calculateLength("sample");
    assertThat(length).isEqualTo(6);
  }
}