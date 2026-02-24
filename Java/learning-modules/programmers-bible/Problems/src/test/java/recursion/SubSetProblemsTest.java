package recursion;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class SubSetProblemsTest {

  private SubSetProblems ssp = new SubSetProblems();

  @Test
  void findAllDistinctSubSetsWithoutDups() {
    int[] elements = new int[]{1, 2, 3};
    final List<List<Integer>> allDistinctSubSets = ssp.findAllDistinctSubSetsWithoutDups(elements);
    assertThat(allDistinctSubSets)
        .containsExactlyInAnyOrderElementsOf(
            List.of(
                List.of(), List.of(1), List.of(2), List.of(3),
                List.of(1,2), List.of(1, 3), List.of(2, 3), List.of(1,2, 3)
            )
        );
  }
}