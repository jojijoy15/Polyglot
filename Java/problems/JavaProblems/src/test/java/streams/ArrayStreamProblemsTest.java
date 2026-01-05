package streams;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class ArrayStreamProblemsTest {

  private final ArrayStreamProblems ap = new ArrayStreamProblems();

  @Test
  void denormalize2DArrays() {
    int[][] arrayOfArrays = new int[][]{
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9},
    };
    final List<Integer> integers = ap.denormalize2DArrays(arrayOfArrays);
    assertThat(integers).hasSize(9)
        .hasSameElementsAs(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
  }
}