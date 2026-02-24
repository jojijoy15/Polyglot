package recursion;

import java.util.ArrayList;
import java.util.List;

public class SubSetProblems {

  /*
    Time: O(n * 2^n)
    Space: O(n), result container is not considered
   */
  public List<List<Integer>> findAllDistinctSubSetsWithoutDups(int[] elements) {
    //[1, 2, 3] => [ [], [1], [2], [3], [1,2], [1, 3], [2, 3], [1, 2, 3] ]
    final ArrayList<List<Integer>> container = new ArrayList<>();
    createSubsetsLoop(0, elements, new ArrayList<>(), container);
    return container;
  }

  private void createSubsets(int index, int[] elements, List<Integer> subSet, List<List<Integer>> container) {
    if(index >= elements.length) {
      container.add(new ArrayList<>(subSet));
      return;
    }

    subSet.add(elements[index]);
    createSubsets(index + 1, elements, subSet, container);
    subSet.remove(subSet.size() - 1); // Need to remove the last element to create new subset without that element
    createSubsets(index + 1, elements, subSet, container);
  }

  private void createSubsetsLoop(int index, int[] elements, List<Integer> subSet, List<List<Integer>> container) {
    container.add(new ArrayList<>(subSet));
    for(int i = index; i < elements.length; i++) {
      subSet.add(elements[i]);
      createSubsetsLoop(i + 1, elements, subSet, container);
      subSet.remove(subSet.size() - 1); // Need to remove the last element to create new subset without that element
    }
  }
}
