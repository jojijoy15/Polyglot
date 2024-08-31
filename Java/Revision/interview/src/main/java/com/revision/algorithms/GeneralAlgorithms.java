package com.revision.algorithms;

import java.time.LocalDate;
import java.time.Period;
import java.util.stream.IntStream;

public class GeneralAlgorithms {

  public int calculateAge(LocalDate date){
    return date.isBefore(LocalDate.now())
        ? Period.between(date, LocalDate.now()).getYears()
        : -1;
  }

  public long factorial(int factorialOf) {
    return IntStream.rangeClosed(1, factorialOf)
        .reduce(1, (a, b) -> a * b);
  }
}
