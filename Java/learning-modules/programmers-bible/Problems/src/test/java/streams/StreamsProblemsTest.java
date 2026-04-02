package streams;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

import data.Generator;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;
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
                                entry("apple", 2L),
                                entry("banana", 2L),
                                entry("orange", 1L)
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
                                entry("cat", 1),
                                entry("banana", 2),
                                entry("apple", 2),
                                entry("orange", 3)
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
        final List<Employee> employees = Generator.getEmployees();

        final List<String> departments = employees.stream()
                .map(Employee::getDepartment).toList();
        final Map<String, Employee> highestPaid = sp.highestPaidEmployeePerDepartment(employees);
        assertThat(highestPaid).containsOnlyKeys(departments);
        assertThat(highestPaid)
                .usingRecursiveComparison()
                .isEqualTo(
                        Map.ofEntries(
                                entry("finance", new Employee("Jason", new BigDecimal(1500), "finance")),
                                entry("ecommerce", new Employee("Martin", new BigDecimal(1600), "ecommerce")),
                                entry("technology", new Employee("Jane", new BigDecimal(1300), "technology"))
                        )
                );
    }

    @Test
    void partitionEvenAndOdd() {
        final List<Integer> integers = List.of(10, 24, 3, 123, 34);
        final Map<Boolean, List<Integer>> oddEvenPartitioned = sp.partitionEvenAndOdd(integers);
        assertThat(oddEvenPartitioned)
                .containsAllEntriesOf(Map.ofEntries(
                        entry(false, List.of(10, 24, 34)),
                        entry(true, List.of(3, 123))
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
                    return Stream.of(entry(e.getKey(), average));
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
                                entry('a', "abracadabra"),
                                entry('c', "caliber"),
                                entry('k', "kettle")
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
                                entry("hello", 1),
                                entry("world", 2)
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

    @Test
    void calculateAverageMarks() {
        String[][] studentsMark = new String[][]{
                {"Bobby", "87"},
                {"Charles", "100"},
                {"Eric", "64"},
                {"Charles", "22"}
        };
        Double averageMarks = sp.calculateAverageMarks(studentsMark,
                Map.Entry.<String, Double>comparingByValue().reversed());
        assertThat(averageMarks).isEqualTo(87);
    }

    @Test
    void calculateAverageNegativeMarks() {
        String[][] studentsMark = new String[][]{
                {"Bobby", "-64"},
                {"Charles", "-65"},
                {"Eric", "-10"},
                {"Charles", "-66"}
        };
        Double averageMarks = sp.calculateAverageMarks(studentsMark, Map.Entry.<String, Double>comparingByValue());
        assertThat(averageMarks).isEqualTo(-66);

    }

    @Test
    void caseInSensitiveWordFrequency() {
        String statement = "Apples are not apples if they are bad in taste";
        Map<String, Long> stringLongMap = sp.caseInSensitiveWordFrequency(statement);
        assertThat(stringLongMap).contains(
                entry("apples", 2L),
                entry("not", 1L),
                entry("if", 1L),
                entry("they", 1L),
                entry("bad", 1L),
                entry("in", 1L),
                entry("taste", 1L)
        );
    }

    @Test
    void createNestedObject() {

        Map<String, Integer> zip = Map.of("primary", 10001, "secondary", 2001);
        Map<String, Object> address = Map.of("city", "New York", "zip", zip);
        Map<String, String> name = Map.of("first", "Alice", "last", "Johnson");

        Map<String, Object> nested = new HashMap<>();
        nested.put("id", 1);
        nested.put("name", name);
        nested.put("address", address);

        Map<String, Object> flattened = sp.flattenObject(nested);
        assertThat(flattened)
                .contains(
                        entry("id", 1),
                        entry("name.first", "Alice"),
                        entry("name.last", "Johnson"),
                        entry("address.city", "New York"),
                        entry("address.zip.primary", 10001),
                        entry("address.zip.secondary", 2001)
                );
    }



    @Test
    void performMerge() {
        List<Map<String, Object>> unmergedJohns = new ArrayList<>();
        Map<String, Object> johnWithAddressCity = new LinkedHashMap<>();
        johnWithAddressCity.put("name", "John");
        johnWithAddressCity.put("address", new LinkedHashMap<>(Map.of("city", "NY")));
        Map<String, Object> johnWithAddressZip = new LinkedHashMap<>();
        johnWithAddressZip.put("name", "John");
        johnWithAddressZip.put("address", new LinkedHashMap<>(Map.of("zip", 50021)));

        unmergedJohns.add(johnWithAddressCity);
        unmergedJohns.add(johnWithAddressZip);
        List<Map<String, Object>> merged = sp.merge(unmergedJohns);

        Map<String, Object> johnWithAddressCityAndZip = new LinkedHashMap<>();
        johnWithAddressCityAndZip.put("name", "John");
        johnWithAddressCityAndZip.put("address", new LinkedHashMap<>(Map.of("city", "NY", "zip", 50021)));

        assertThat(merged)
                .usingRecursiveComparison()
                .isEqualTo(List.of(johnWithAddressCityAndZip));
    }

    @Test
    void mergeArrays() {

        //Basic
        List<Map<String, Object>> usersBasic = new ArrayList<>();

        Map<String, Object> user1 = new LinkedHashMap<>();
        user1.put("id", 1);
        user1.put("name", "Alice");
        user1.put("age", 25);

        Map<String, Object> user2 = new LinkedHashMap<>();
        user2.put("id", 2);
        user2.put("name", "Bob");
        user2.put("age", null);

        Map<String, Object> user3 = new LinkedHashMap<>();
        user3.put("id", 3);
        user3.put("name", "Charlie");
        user3.put("age", 30);

        usersBasic.add(user1);
        usersBasic.add(user2);
        usersBasic.add(user3);

        //Extras
        List<Map<String, Object>> usersExtra = new ArrayList<>();

        Map<String, Object> extra1 = new LinkedHashMap<>();
        extra1.put("id", 1);
        extra1.put("email", "alice@gmail.com");
        extra1.put("country", "USA");

        Map<String, Object> extra2 = new LinkedHashMap<>();
        extra2.put("id", 2);
        extra2.put("email", null);
        extra2.put("country", "Canada");

        Map<String, Object> extra3 = new LinkedHashMap<>();
        extra3.put("id", 3);
        extra3.put("email", "charlie@gmail.com");
        extra3.put("country", null);

        usersExtra.add(extra1);
        usersExtra.add(extra2);
        usersExtra.add(extra3);

        //

        List<Map<String, Object>> actual = sp.mergeArrays(usersBasic, usersExtra);

        //Result Expected

        List<Map<String, Object>> expected = new ArrayList<>();

        Map<String, Object> resultUser1 = new LinkedHashMap<>();
        resultUser1.put("id", 1);
        resultUser1.put("name", "Alice");
        resultUser1.put("age", 25);
        resultUser1.put("email", "alice@gmail.com");
        resultUser1.put("country", "USA");

        Map<String, Object> resultUser2 = new LinkedHashMap<>();
        resultUser2.put("id", 2);
        resultUser2.put("name", "Bob");
        resultUser2.put("country", "Canada");

        Map<String, Object> resultUser3 = new LinkedHashMap<>();
        resultUser3.put("id", 3);
        resultUser3.put("name", "Charlie");
        resultUser3.put("age", 30);
        resultUser3.put("email", "charlie@gmail.com");

        expected.add(resultUser1);
        expected.add(resultUser2);
        expected.add(resultUser3);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}