package streams;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

  // Given a string, find the first non-repeating character using streams.
  public Optional<Character> firstNonRepeatingCharacter(String word) {
    return word.chars()
        .mapToObj(c -> (char) c)
        .filter(ch -> word.chars().filter(c -> c == ch).count() == 1)
        .findFirst();

    // O(n^2) -> [] O(1)
    // O(n) -> [] O(k) ->  Create an ordered map frequency table and stop as soon as first duplicate character
  }

  // Frequency Count Using Streams
  public Map<String, Long> wordsFrequency(List<String> words) {
    return words.stream().
        collect(Collectors.groupingBy(e -> e, Collectors.counting()));
  }

  // Given a Map<String, Integer>, sort it by values in descending order and return a LinkedHashMap.
  public Map<String, Integer> sortByValue(LinkedHashMap<String, Integer> words) {
    return words.entrySet()
        .stream()
        .sorted(Entry.comparingByValue())
        .collect(
            Collectors.toMap(
                Map.Entry::getKey, Map.Entry::getValue,
                (a, b )-> a, LinkedHashMap::new
            )
        );
  }

  // Given a list of integers, find the second highest distinct number.
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

  // Given a list of integers, partition them into even and odd numbers.
  public Map<Boolean, List<Integer>> partitionEvenAndOdd(List<Integer> integers) {
    return integers.stream()
        .collect(Collectors.partitioningBy(e -> ((e)&1) == 1));
  }

  // Given a list of strings, join them into a single comma-separated string, converting all values to uppercase, skipping nulls.
  public String joinUpperCase(List<String> words) {
    return words.stream().filter(Objects::nonNull)
        .map(String::toUpperCase)
        .collect(Collectors.joining());
  }

  // Given a list of integers, compute their sum
  public Integer sum(List<Integer> integers) {
    return integers.stream()
        .reduce(0, Integer::sum);
  }

  //Given a list of employees, group them by department and compute the average salary per department.
  public Map<String, Double> averageSalaryByDepartment(List<Employee> employees) {
    return employees.stream()
            .collect(
                Collectors.groupingBy(
                    Employee::getDepartment, Collectors.averagingDouble(e -> e.getSalary().doubleValue())
            ));
  }

  public Integer divisibleBy17And19(Stream<Integer> integers) {
    return integers
        .filter(e -> (e % 17 == 0) && (e % 19 == 0))
        .findFirst().orElse(-1);
  }

  /*
  Given a list of integers, find the top 3 distinct largest numbers.
  Constraints:
    - Do not sort the entire list.
    - Return results in descending order.
   */
  public List<Integer> topNElements(List<Integer> integers, int size) {
    return integers.stream()
        .distinct()
        .collect(
            () -> new TreeSet<Integer>(Comparator.reverseOrder()),
            (set, n) -> {
              set.add(n);
              if (set.size() > size) {
                set.pollLast();
              }
            },
            (set1, set2) -> {
                set1.addAll(set2);
                while (set1.size() > size) {
                  set1.pollLast();
                }
            }
        ).stream().toList();
  }

  //Given a list of strings, find the longest word for each starting character.
  public Map<Character, String> findLongestWordBasedOnFirstCharacter(List<String> words) {
    return words.stream()
        .collect(Collectors.toMap(e -> e.charAt(0), Function.identity(),
                  (e1, e2) -> e1.length() >= e2.length() ? e1 : e2));
  }

  // Merge Two Lists into an Ordered Map
  // assume lists are of same size
  public Map<String, Integer> prepareMapFromLists(List<String> words, List<Integer> integers) {
    int len = Math.min(words.size(), integers.size());
    return IntStream.range(0, len)
        .mapToObj(i -> Map.entry(words.get(i), integers.get(i)))
        .collect(Collectors.toMap(
            Map.Entry::getKey, Map.Entry::getValue,
            (e1, e2) -> e1, LinkedHashMap::new
        ));
  }
}
