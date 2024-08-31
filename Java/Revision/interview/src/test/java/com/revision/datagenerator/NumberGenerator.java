package com.revision.datagenerator;

import com.revision.datagenerator.configurations.FakerConfiguration;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class NumberGenerator {

  public static List<Integer> generateBoundedIntegers(int limit, int start, int end) {
    return IntStream.generate(() -> new Random().nextInt(end))
        .filter(e -> e > start)
        .limit(limit)
        .mapToObj(e -> e)
        .toList();
  }

  public static List<Integer> generateNRandomIntegers(int limit) {
    return IntStream.generate(() -> new Random().nextInt())
        .limit(limit)
        .mapToObj(e -> e).toList();
  }
}
