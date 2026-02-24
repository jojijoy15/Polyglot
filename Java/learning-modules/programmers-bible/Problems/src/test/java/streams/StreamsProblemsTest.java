package streams;

import static org.assertj.core.api.Assertions.assertThat;

import data.Generator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import model.Employee;
import model.OrderItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StreamsProblemsTest {

  StreamsProblems sp = new StreamsProblems();

  @Test
  void squareOfIntegers() {
    final List<Integer> integers = sp.squareOfIntegers(List.of(1, 2, 3, 4, 5));
    assertThat(integers).hasSize(2).hasSameSizeAs(List.of(4, 16));
  }

  @ParameterizedTest
  @ValueSource(strings = {"swiss", "akakssbbw", "w"})
  void firstNonRepeatingCharacter(String word) {
    final Optional<Character> nonRepeatingCharacter = sp.firstNonRepeatingCharacter(word);
    assertThat(nonRepeatingCharacter).isPresent().get()
        .isEqualTo('w');
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

  @Test
  void sum() {
    final List<Integer> integers = List.of(12, 14, 20, 21);
    final Integer sum = sp.sum(integers);
    assertThat(sum).isEqualTo(67);
  }


  @Test
  void averageSalaryByDepartment() {
    // Prepare
    List<Employee> employees = IntStream.rangeClosed(1, 15)
        .mapToObj(e -> Generator.getEmployee())
        .toList();
    final Map<String, List<Employee>> employeesByDepartment = sp.groupEmployeesByDepartment(employees);
    // Finding expected result by long route
    final Map<String, Double> expectedAverageSalaryByDepartment = employeesByDepartment.entrySet().stream()
        .flatMap(e -> {
          List<Employee> emps = e.getValue();
          Double average = emps.stream().mapToDouble(es -> es.getSalary().doubleValue()).average().getAsDouble();
          return Stream.of(Map.entry(e.getKey(), average));
        }).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    // Act
    final Map<String, Double> actualAvgSalaryByDepartment = sp.averageSalaryByDepartment(employees);
    // Assert
    assertThat(actualAvgSalaryByDepartment)
        .containsAllEntriesOf(expectedAverageSalaryByDepartment);
  }

  @Test
  void divisibleBy17And19() {
    final Stream<Integer> iterate = Stream.iterate(1, (a) -> a + 1);
    final Integer i = sp.divisibleBy17And19(iterate);
    assertThat(i).isNotEqualTo(-1).isEqualTo(323);
  }

  @Test
  void topNElements() {
    List<Integer> numbers = List.of(343, -34, 21, 45, 17, 32);
    final List<Integer> top3Elements = sp.topNElements(numbers, 3);
    assertThat(top3Elements)
        .containsExactly(343, 45, 32);
  }

  @Test
  void findLongestWordBasedOnFirstCharacter() {
    List<String> words = List.of("abracadabra", "cat", "kettle", "kite", "caliber", "apple");
    final Map<Character, String> longestWordBasedOnFirstCharacter = sp
        .findLongestWordBasedOnFirstCharacter(words);
    assertThat(longestWordBasedOnFirstCharacter)
        .containsAllEntriesOf(
            Map.ofEntries(
            Map.entry('a', "abracadabra"),
            Map.entry('c', "caliber"),
            Map.entry('k', "kettle")
        ));
  }

  @Test
  void prepareMapFromLists() {
    List<String> words = List.of("hello", "world");
    List<Integer> integers = List.of(1, 2);
    final Map<String, Integer> preparedMap = sp.prepareMapFromLists(words, integers);
    assertThat(preparedMap)
        .containsAllEntriesOf(
            Map.ofEntries(
                Map.entry("hello", 1),
                Map.entry("world", 2)
            )
        );
  }

  @Test
  void mostFrequentElement() {
    List<String> words = List.of("apple", "orange", "Cat", "Dog", "apple");
    final String mostFrequentElement = sp.mostFrequentElement(words);
    assertThat(mostFrequentElement).isEqualTo("apple");
  }

  @Test
  void sortByFrequency() {
    int[] numbers = new int[]{10, 23, 42, 12, 10, 42, 35, 23, 10};
    final List<Integer> actuals = sp.sortByFrequency(numbers);
    assertThat(actuals).containsExactly(10, 10, 10, 23, 23, 42, 42, 12, 35);
  }

  @Test
  void findMostPurchasedOrderItems() {
    List<OrderItem> orderItems = Generator.getOrderItems();
    final List<OrderItem> mostPurchasedOrderItems = sp.findMostPurchasedOrderItems(orderItems);
  }
}