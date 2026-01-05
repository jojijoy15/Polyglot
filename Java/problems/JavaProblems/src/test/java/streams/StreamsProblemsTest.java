package streams;


import static org.assertj.core.api.Assertions.assertThat;

import data.Generator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import model.Employee;
import org.junit.jupiter.api.Test;

class StreamsProblemsTest {

  StreamsProblems sp = new StreamsProblems();

  @Test
  void squareOfIntegers() {
    final List<Integer> integers = sp.squareOfIntegers(List.of(1, 2, 3, 4, 5));
    assertThat(integers).hasSize(2).hasSameSizeAs(List.of(4, 16));
  }

  @Test
  void wordsFrequency() {
    List<String> words = List.of("apple", "banana", "apple", "orange", "banana");
    final Map<String, Long> frequencyMap = sp.wordsFrequency(words);
    assertThat(frequencyMap).hasSize(3)
        .containsExactlyInAnyOrderEntriesOf(
            Map.ofEntries(
              Map.entry("apple", 2L),
              Map.entry("banana", 2L),
              Map.entry("orange", 1L)
            )
        );
  }

  @Test
  void sortByValue() {
    //Note : Had to linked hashmap to preserve input order for assertion
    LinkedHashMap<String, Integer> words = new LinkedHashMap<>(
        Map.of("apple", 2, "banana", 2, "cat", 1, "orange", 3)
    );
    final LinkedHashMap<String, Integer> frequencyMap = (LinkedHashMap<String, Integer>) sp.sortByValue(words);
    assertThat(frequencyMap).hasSize(4)
        .containsAllEntriesOf(
            Map.ofEntries(
                Map.entry("cat", 1),
                Map.entry("banana", 2),
                Map.entry("apple", 2),
                Map.entry("orange", 3)
            )
        );
  }

  @Test
  void secondHighestNumber() {
    Integer integer = sp.secondHighestNumber(List.of(21, 2, 13, 44, 13, 15));
    assertThat(integer).isEqualTo(21);

  }

  @Test
  void groupEmployeesByDepartment() {
    final List<Employee> employees = IntStream.range(1, 10)
        .mapToObj(e -> Generator.getEmployee())
        .collect(Collectors.toList());

    final List<String> departments = employees.stream()
        .map(Employee::getDepartment).toList();
    final Map<String, List<Employee>> employeesGrpByDept = sp.groupEmployeesByDepartment(employees);
    assertThat(employeesGrpByDept).containsOnlyKeys(departments);
  }

  @Test
  void highestPaidEmployeePerDepartment() {
    final List<Employee> employees = IntStream.rangeClosed(1, 15)
        .mapToObj(e -> Generator.getEmployee())
        .collect(Collectors.toList());

    final List<String> departments = employees.stream()
        .map(Employee::getDepartment).toList();
    final Map<String, String> highestPaid = sp.highestPaidEmployeePerDepartment(employees);
    assertThat(highestPaid).containsOnlyKeys(departments);
  }

  @Test
  void partitionEvenAndOdd() {
    final List<Integer> integers = List.of(10, 24, 3, 123, 34);
    final Map<Boolean, List<Integer>> oddEvenPartitioned = sp.partitionEvenAndOdd(integers);
    assertThat(oddEvenPartitioned)
        .containsAllEntriesOf(Map.ofEntries(
            Map.entry(false, List.of(10, 24, 34)),
            Map.entry(true, List.of(3, 123))
        ));
  }

  @Test
  void joinUpperCase() {
    List<String> words = IntStream.rangeClosed(0, 15)
        .mapToObj(e -> Generator.getRandomWords())
        .collect(Collectors.toCollection(ArrayList::new));

    words.add(3, null);
    words.add(7, null);

    final String joinedUpperCaseWords = sp.joinUpperCase(words);
    assertThat(joinedUpperCaseWords).doesNotContainPattern("\\*null\\*");
  }


}