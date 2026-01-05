package streams;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.Employee;

public class StreamsProblems {

  /*
    Given a list of integers, return a list of squares of even numbers only.
    Assume input is not null
  */
  public List<Integer> squareOfIntegers(List<Integer> integers) {
    return integers.stream()
        .filter(e -> ((e)&1) == 0 )
        .map(e -> e * e)
        .toList();
  }

  //TODO: Given a string, find the first non-repeating character using streams.
  public Void firstNonRepeatingCharacter(String word) {
    return  null;
  }

  //Frequency Count Using Streams
  public Map<String, Long> wordsFrequency(List<String> words) {
    return words.stream().
        collect(Collectors.groupingBy(e -> e, Collectors.counting()));
  }

  //Given a Map<String, Integer>, sort it by values in descending order and return a LinkedHashMap.
  public Map<String, Integer> sortByValue(LinkedHashMap<String, Integer> words) {
    return words.entrySet()
        .stream()
        .sorted(Comparator.comparing(Entry::getValue))
        .collect(
            Collectors.toMap(
                Map.Entry::getKey, Map.Entry::getValue,
                (a, b )-> a, LinkedHashMap::new
            )
        );
  }

  //Given a list of integers, find the second highest distinct number.
  public Integer secondHighestNumber(List<Integer> integers) {
    return integers
        .stream()
        .distinct()
        .sorted(Comparator.reverseOrder())
        .skip(1).findFirst().orElse(0);
  }

  /*
    Given a List<Employee> where Employee has name, department, and salary
    Group employees by department
  */
  public Map<String, List<Employee>> groupEmployeesByDepartment(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(Employee::getDepartment));
  }

 /*
  Given a List<Employee> where Employee has name, department, and salary
  Find highest paid employee in each department
 */
  public Map<String, String> highestPaidEmployeePerDepartment(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(Employee::getDepartment))
        .entrySet()
        .stream()
        .flatMap( e -> {
            List<Employee> emps = e.getValue();
            Employee emp = emps.stream().max(Comparator.comparing(Employee::getSalary)).get();
            return Stream.of(emp);
        })
        .collect(Collectors.toMap(Employee::getDepartment, Employee::getName));
  }

  //Given a list of integers, partition them into even and odd numbers.
  public Map<Boolean, List<Integer>> partitionEvenAndOdd(List<Integer> integers) {
    return integers.stream()
        .collect(Collectors.partitioningBy(e -> ((e)&1) == 1));
  }

  //Given a list of strings, join them into a single comma-separated string, converting all values to uppercase, skipping nulls.
  public String joinUpperCase(List<String> words) {
    return words.stream().filter(Objects::nonNull)
        .map(String::toUpperCase)
        .collect(Collectors.joining());
  }
}
