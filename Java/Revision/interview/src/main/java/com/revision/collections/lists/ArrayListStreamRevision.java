package com.revision.collections.lists;

import com.revision.algorithms.PrimeNumberAlgorithms;
import com.revision.model.SalariedEmployee;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ArrayListStreamRevision {

  /*
    Given a list of integers partition them by prime and non prime
  */
  public Map<Boolean, List<Integer>> partitionPrimeAndComposite(List<Integer> numbers) {
    return numbers.stream()
        .filter(Objects::nonNull)
        .collect(Collectors.partitioningBy(PrimeNumberAlgorithms::isPrime));
  }

  /*
    Generate Prime number below a given number using Sieve of Eratosthenes algorithm
  */
  public List<Integer> generatePrimeNumber(int number) {
    return PrimeNumberAlgorithms.findAllPrimesUpTo(number);
  }

  /*
   Sort salariedEmployees bases on salary
   */
  public List<SalariedEmployee> sortEmployeesBasedOnSalary(List<SalariedEmployee> salariedEmployees) {
    return salariedEmployees.stream().sorted(Comparator.comparing(SalariedEmployee::salary))
        .toList();
  }

  /*
    Partition number based on a predicate
  */
  public Map<Boolean, List<Integer>> partitionNumbersBasedOnPredicate(List<Integer> numbers,
      Predicate<Integer> predicate) {
    return numbers.stream().collect(Collectors.partitioningBy(predicate));
  }

  /*
    Remove duplicates
  */
  public <T> List<T> removeDuplicates(List<T> elements) {
    return new HashSet<>(elements).stream().toList();
  }

  /*
    Count occurrences of words
  */
  public <T> Map<T, Long> countOccurrencesInList(List<T> elements) {
    return elements.stream()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }

  /*
    Reverse Decimals list
  */
  public List<BigDecimal> reverseDecimals(List<BigDecimal> decimals) {
    return decimals.stream()
        .sorted((e1, e2) -> -1 * e1.compareTo(e2))
        .toList();
  }

  /*
    Join words using prefix and suffix
  */
  public String joinWordsUsingPrefixSuffix(List<String> words, String prefix, String suffix, String delimiter) {
    return words.stream().map(e -> prefix + " " + e +  " " + suffix)
        .collect(Collectors.joining(delimiter));
  }

  /*
    Divisibility by N
  */
  public List<Integer> divisibilityByN(List<Integer> numbers, int divisor) {
    return numbers.stream().filter(number -> number % divisor == 0).toList();
  }

  /*
    Find Max from list
  */
  public Integer findMax(List<Integer> numbers) {
    return numbers.stream().max(Comparator.naturalOrder()).orElse(Integer.MIN_VALUE);
  }

  /*
    Find Min from list
  */
  public Integer findMin(List<Integer> numbers) {
    return numbers.stream().min(Comparator.naturalOrder()).orElse(Integer.MAX_VALUE);
  }

  /*
    Merge two list into one in sorted form
  */
  public List<Integer> mergeAsSortedList(List<Integer> list1, List<Integer> list2) {
    return Stream.concat(list1.stream(), list2.stream())
        .sorted(Comparator.naturalOrder()).toList();
  }

  /*
    Merge two list into one in sorted form but only distinct elements
  */
  public List<Integer> mergeAsSortedListWithDistinct(List<Integer> list1, List<Integer> list2) {
    return Stream.concat(list1.stream(), list2.stream())
        .distinct()
        .sorted(Comparator.naturalOrder())
        .toList();
  }

  public Integer findSum(List<Integer> numbers) {
    return numbers.stream().mapToInt(e -> e).sum();
  }

  public Double findAverage(List<Integer> numbers) {
    return numbers.stream().mapToInt(e -> e).average().getAsDouble();
  }

  public List<String> sortWordsBasedOnLength(List<String> words) {
    return words.stream().sorted(Comparator.comparing(String::length)).toList();
  }

  public <T> List<T> findIntersection(List<T> list1, List<T> list2) {
    return list1.stream().filter(list2::contains).toList();
  }

  public <T> List<T> getDuplicates(List<T> list) {
    Map<T, Long> frequency = list.stream()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    return frequency.entrySet().stream()
        .filter(e -> e.getValue() > 1).map(Entry::getKey).toList();
  }

  public <T> List<T> mostRepeatedElement(List<T> list) {
    Map<T, Long> frequency = list.stream()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    Long maxCount = frequency.entrySet().stream()
        .max(Entry.comparingByValue())
        .orElse(Map.entry((T) new Object(), 0L)).getValue(); // hack Object
    return frequency.entrySet().stream().filter(e -> e.getValue().equals(maxCount)).map(Entry::getKey).toList();
  }

  /*
   Given a sequence of consecutive numbers find missing number
  */
  public int findMissingNumber(int start, int end, List<Integer> numbers) {
    int sum = numbers.stream().mapToInt(Integer::intValue).sum();
    int expectedSum = IntStream.rangeClosed(start, end).sum();
    return expectedSum - sum;
  }
}
