package com.revision.algorithms;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class PrimeNumberAlgorithms {

  /* Normal algorithm*/
  public static boolean isPrime(Integer number) {
    return IntStream.rangeClosed(2, (int) Math.sqrt(number))
        .filter(e -> number % e == 0)
        .boxed().findFirst().isEmpty();
  }

  /** Sieve of Eratosthenes prime number generation algorithm
  * @see <a href="https://stackoverflow.com/questions/74143898/how-is-this-eratosthenes-sieve-implementation-is-working-internally">StreamHackySolution</a>
  * */
  public static List<Integer> findAllPrimesUpTo(Integer number) {
    int[] elements = IntStream.rangeClosed(2, number)
        .toArray();
    for(int i = 2; i < Math.sqrt(number); ++i) {
     for(int j = 2 ; i*j < elements.length + 2; ++j) { // removing all composite
       int index = i*j - 2;
       Objects.checkIndex(index, elements.length);
       elements[index] = 0;
     }
    }

    return Arrays.stream(elements).filter(e -> e != 0).boxed().toList();
  }
}


