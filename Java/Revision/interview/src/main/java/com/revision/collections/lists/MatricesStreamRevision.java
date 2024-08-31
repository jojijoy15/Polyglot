package com.revision.collections.lists;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MatricesStreamRevision {

  public List<Integer> findMaxElementInEachRow(Integer[][] matrices)  {
    return Arrays.stream(matrices)
        .map(e -> Arrays.stream(e).max(Comparator.comparingInt(Integer::intValue)).orElse(Integer.MIN_VALUE))
        .toList();
    //O(n*m)
  }

}
