package com.revision.interview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class SpecialNumber {

  /*
   * Find special numbers
   * sum of digits -> Keep on adding the digits until its less than 10
   * Special number -> all non-zero digits of factorial of sum of digits of a number should be present in the number
   */
  public List<Boolean> findSpecialNumbers(int[] numbers) {
    int[] factorials = Arrays.stream(numbers)
        .map(this::findSumOfDigitsInSingleDigit)
        .map(this::factorial)
        .toArray();
    List<Boolean> result = new ArrayList<>();
    IntStream.range(0, numbers.length)
        .forEach(i -> {
          boolean status = checkSpecialNumber(numbers[i], factorials[i]);
          result.add(status);
        });
    return result;
  }

  private int findSumOfDigitsInSingleDigit(int number) {
    if (number < 10) {
      return number;
    } else {
      int sum = 0;
      while (0 != number) {
        sum += number % 10;
        number /= 10;
      }
      return findSumOfDigitsInSingleDigit(sum);
    }
  }

  private int factorial(int factorialOf) {
    return IntStream.rangeClosed(1, factorialOf)
        .reduce(1, (a, b) -> a * b);
  }

  private boolean checkSpecialNumber(int number, int factorialResult) {
    List<String> numberDigits = Arrays.stream(String.valueOf(number).split("")).toList();
    List<String> factorialDigits = Arrays.stream(String.valueOf(factorialResult).split(""))
        .toList();
    return numberDigits.stream()
        .filter(e -> !e.equals("0"))
        .allMatch(factorialDigits::contains);
  }

}
