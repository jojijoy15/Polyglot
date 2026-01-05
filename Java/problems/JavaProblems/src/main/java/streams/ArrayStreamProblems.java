package streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayStreamProblems {

  public List<Integer> denormalize2DArrays(int[][] array) {
    return Arrays.stream(array)
        .flatMapToInt(e -> Arrays.stream(e))
        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
  }
}
