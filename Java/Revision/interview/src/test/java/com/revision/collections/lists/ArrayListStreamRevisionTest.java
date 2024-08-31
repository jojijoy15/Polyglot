package com.revision.collections.lists;

import static org.assertj.core.api.Assertions.assertThat;

import com.revision.datagenerator.EmployeeGenerator;
import com.revision.datagenerator.NumberGenerator;
import com.revision.datagenerator.StringGenerator;
import com.revision.model.Employee;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ArrayListStreamRevisionTest {

  private ArrayListStreamRevision instanceUnderTest;

  @BeforeEach
  void setUp() {
    this.instanceUnderTest = new ArrayListStreamRevision();
  }

  @Test
  void test_should_partition_prime_and_composite_numbers() {
    var elements = new ArrayList<>(List.of(1, 2, 3, 5, 10, 23, 600, 1001, 2000));
    elements.add(null);
    Map<Boolean, List<Integer>> result = this.instanceUnderTest.partitionPrimeAndComposite(elements);
    assertThat(result.get(true)).doesNotContainNull().hasSameElementsAs(List.of(1, 2, 3, 5, 23));
    assertThat(result.get(false)).doesNotContainNull().hasSameElementsAs(List.of(10, 600, 1001, 2000));
  }

  @ParameterizedTest
  @MethodSource("primeGenerationUpToResultVerification")
  void test_should_return_prime_number_till_N(int number, List<Integer> expectedResultant) {
    List<Integer> result = this.instanceUnderTest.generatePrimeNumber(number);
    assertThat(result).isEqualTo(expectedResultant);
  }

  private static Stream<Arguments> primeGenerationUpToResultVerification() {
    return Stream.of(
        Arguments.arguments(10, List.of(2, 3, 5, 7)),
        Arguments.arguments(0, Collections.emptyList()),
        Arguments.arguments(1, Collections.emptyList()),
        Arguments.arguments(53, List.of(2, 3, 5, 7, 11, 13, 17, 19,
                                        23, 29, 31, 37, 41, 43, 47, 53))
    );
  }

  @Test
  void test_should_sort_employees_by_salary() {
    List<Employee> employees = EmployeeGenerator.generateEmployees();
    List<Employee> result = this.instanceUnderTest.sortEmployeesBasedOnSalary(employees);
    assertThat(result).isNotSameAs(employees);
    assertThat(result).isSortedAccordingTo(Comparator.comparing(Employee::salary));
  }

  @Test
  void test_should_partition_numbers_based_on_odd_even_predicate() {
    List<Integer> integers = NumberGenerator.generateBoundedIntegers(10, 20, 40);
    Map<Boolean, List<Integer>> result = this.instanceUnderTest
        .partitionNumbersBasedOnPredicate(integers, e -> (e & 1) == 0);
    //Even numbers assertions
    assertThat(result.get(true)).containsAll(integers.stream().filter( e -> (e & 1) ==0).toList());
    //Odd numbers assertions
    assertThat(result.get(false)).containsAll(integers.stream().filter( e -> (e & 1) !=0).toList());
  }

  @Test
  void test_should_remove_duplicate_words_from_given_list() {
    List<String> words = new ArrayList<>(StringGenerator.generateLorenIpsumWords(35));
    int size = words.size();
    words.addAll(words); //simulating duplicates
    assertThat(words).hasSize(2*size);
    List<String> result = this.instanceUnderTest.removeDuplicates(words);
    assertThat(result).doesNotHaveDuplicates();
  }

  @Test
  void test_should_return_empty_map_for_empty_list() {
    Map<String, Long> frequency = this.instanceUnderTest
        .countOccurrencesInList(Collections.emptyList());
    assertThat(frequency).isEmpty();
  }

  @Test
  void test_should_return_correct_count_of_occurrences_in_list() {
    List<String> words = List.of("Apple", "World", "Jewels", "Planet", "World", "Jewellery", "Apple");
    Map<String, Long> frequency = this.instanceUnderTest.countOccurrencesInList(words);
    assertThat(frequency).containsAllEntriesOf(
      Map.of("Apple", 2L,
             "World", 2L,
             "Jewels", 1L,
             "Jewellery", 1L,
             "Planet", 1L
      )
    );
  }

  @Test
  void test_should_reverse_list_of_decimals() {
    List<BigDecimal> decimals = List.of(new BigDecimal(1020.33432), new BigDecimal(100),
            new BigDecimal(4),  new BigDecimal(121.32),
            new BigDecimal(110), new BigDecimal(223.12)
    );
    List<BigDecimal> result = this.instanceUnderTest.reverseDecimals(decimals);
    assertThat(result).isSortedAccordingTo((e, f)-> -e.compareTo(f));
  }

  @Test
  void test_should_join_words_with_prefix_suffix_delimiter() {
    List<String> words = List.of("words", "are", "there");
    String joinedWord = this.instanceUnderTest.joinWordsUsingPrefixSuffix(words, "{", "}", ",");
    assertThat(joinedWord).isEqualTo("{ words },{ are },{ there }");
  }

  @Test
  void test_should_return_divisibility_by_5() {
    List<Integer> numbers = List.of(10, 4, 32, 51, 50, 400, 21);
    List<Integer> result = this.instanceUnderTest.divisibilityByN(numbers, 5);
    assertThat(result).hasSameElementsAs(List.of(10, 50, 400));
  }

  @Test
  void test_should_return_max() {
    List<Integer> numbers = List.of(10, 32, 53, 53, 4, 12, 10, -5, -2);
    assertThat(this.instanceUnderTest.findMax(numbers)).isEqualTo(53);
    assertThat(this.instanceUnderTest.findMax(Collections.emptyList())).isEqualTo(Integer.MIN_VALUE);
  }

  @Test
  void test_should_return_min() {
    List<Integer> numbers = List.of(10, 32, 53, 4, 12, 10, -5, -5, -2);
    assertThat(this.instanceUnderTest.findMin(numbers)).isEqualTo(-5);
    assertThat(this.instanceUnderTest.findMin(Collections.emptyList())).isEqualTo(Integer.MAX_VALUE);

  }

  @Test
  void test_should_merge_two_unsorted_list_in_sorted_order() {
    List<Integer> list1 = List.of(10, 5, 12, 34, 0, -10, 41, 14);
    List<Integer> list2 = List.of(25, 54, 5, 12, 100, 3, 32, -1);
    List<Integer> result = this.instanceUnderTest.mergeAsSortedList(list1, list2);
    assertThat(result).isSortedAccordingTo(Comparator.naturalOrder())
        .isEqualTo(List.of(-10, -1, 0, 3, 5, 5, 10, 12, 12, 14, 25, 32, 34, 41, 54, 100));
  }

  @Test
  void test_should_merge_two_unsorted_list_in_sorted_order_with_unique_elements() {
    List<Integer> list1 = List.of(10, 5, 12, 34, 0, -10, 41, 14);
    List<Integer> list2 = List.of(25, 54, 5, 12, 100, 3, 32, -1);
    List<Integer> result = this.instanceUnderTest.mergeAsSortedListWithDistinct(list1, list2);
    assertThat(result).isSortedAccordingTo(Comparator.naturalOrder())
        .isEqualTo(List.of(-10, -1, 0, 3, 5, 10, 12, 14, 25, 32, 34, 41, 54, 100));
  }

  @Test
  void test_should_return_expected_sum() {
    List<Integer> numbers = IntStream.rangeClosed(1, 10).boxed().toList();
    assertThat(this.instanceUnderTest.findSum(numbers)).isEqualTo(55);
  }

  @Test
  void test_should_return_zero_for_empty_list() {
    List<Integer> numbers = Collections.emptyList();
    assertThat(this.instanceUnderTest.findSum(numbers)).isEqualTo(0);
  }

  @Test
  void test_should_return_words_sorted_as_per_length() {
    List<String> words = StringGenerator.generateLorenIpsumWords(10);
    List<String> result = this.instanceUnderTest.sortWordsBasedOnLength(words);
    assertThat(result).isSortedAccordingTo(Comparator.comparing(String::length));
  }

  @Test
  void test_should_return_expected_average() {
    List<Integer> numbers = IntStream.rangeClosed(1, 10).boxed().toList();
    assertThat(this.instanceUnderTest.findAverage(numbers)).isEqualTo(5.5);
  }

  @Test
  void test_should_return_common_elements() {
    List<String> wordle1 = List.of("Apple", "Banana", "Aeroplane");
    List<String> wordle2 = List.of("Elephant", "Banana", "Apple");
    List<String> result1 = this.instanceUnderTest.findIntersection(wordle1, wordle2);
    assertThat(result1).hasSameElementsAs(List.of("Apple", "Banana"));
    assertThat(this.instanceUnderTest.findIntersection(wordle1, Collections.emptyList())).isEmpty();
  }

  @Test
  void test_should_return_duplicates() {
    List<String> fruits = List.of("Apple", "Banana", "Orange", "Kiwi", "Banana", "Orange");
    List<Integer> numbers = List.of(12, 42, 22, 41, 42, 44, 54, 12);
    List<String> result1 = this.instanceUnderTest.getDuplicates(fruits);
    List<Integer> result2 = this.instanceUnderTest.getDuplicates(numbers);
    assertThat(result1).hasSameElementsAs(List.of("Banana", "Orange"));
    assertThat(result2).hasSameElementsAs(List.of(12, 42));
  }

  @Test
  void test_should_return_most_frequent_element() {
    List<String> fruits = List.of("Apple", "Orange", "Kiwi", "Apple", "Banana");
    List<String> result = this.instanceUnderTest.mostRepeatedElement(fruits);
    assertThat(result).isEqualTo(List.of("Apple"));
    List<String> emptyResult = this.instanceUnderTest.mostRepeatedElement(Collections.emptyList());
    assertThat(emptyResult).isEqualTo(Collections.emptyList());

  }

  @Test
  void test_should_return_most_frequent_element_when_multiple_elements_are_frequent() {
    List<String> fruits = List.of("Apple", "Orange", "Pears", "Orange", "Orange", "Apple", "Apple");
    List<String> result = this.instanceUnderTest.mostRepeatedElement(fruits);
    assertThat(result).isEqualTo(List.of("Apple", "Orange"));
  }

}