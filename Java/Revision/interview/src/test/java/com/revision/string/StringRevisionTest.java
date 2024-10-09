package com.revision.string;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.revision.datagenerator.FileOps;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringRevisionTest {

  private StringRevision instanceUnderTest;

  @BeforeEach
  void setUp() {
    instanceUnderTest = new StringRevision();
  }

  @Test
  void test_should_return_empty_map_for_empty_word() {
    String word = "";
    Map<Character, Long> occurrences = instanceUnderTest
        .countCharacterOccurrencesInWord(word);
    Assertions.assertThat(occurrences).isEmpty();
  }

  @Test
  void test_should_return_map_of_character_and_its_count_in_word() {
    String word = "Dummy Word";
    Map<Character, Long> occurrences = instanceUnderTest
        .countCharacterOccurrencesInWord(word);
    Assertions.assertThat(occurrences).containsAllEntriesOf(
        Map.of('D', 1L, 'u', 1L,
               'm', 2L, 'y', 1L,
               'W', 1L, 'o', 1L,
               'r', 1L, 'd', 1L,
               ' ', 1L
        ));
  }

  @Test
  void test_should_check_if_two_given_strings_are_anagram() {
    var first = "program";
    var second = "grmproa";
    assertThat(instanceUnderTest.checkAnagram(first, second)).isTrue();
    assertThat(instanceUnderTest.checkAnagram(first, "")).isFalse();
  }

  @Test
  void test_should_detect_if_two_given_strings_are_anagram() {
    var first = "listen";
    var second = "silent";
    assertThat(instanceUnderTest.detectAnagram(first, second)).isTrue();
    assertThat(instanceUnderTest.detectAnagram(first, "")).isFalse();
    assertThat(instanceUnderTest.detectAnagram("a", "a")).isTrue();
  }

  @Test
  void test_should_correctly_check_palindrome() {
    assertThat(instanceUnderTest.checkIfPalindrome("malayalam")).isTrue();
    assertThat(instanceUnderTest.checkIfPalindrome("")).isTrue();
    assertThat(instanceUnderTest.checkIfPalindrome("a")).isTrue();
    assertThat(instanceUnderTest.checkIfPalindrome("abba")).isTrue();
    assertThat(instanceUnderTest.checkIfPalindrome("ab")).isFalse();
  }

  @Test
  void test_should_find_words_with_max_length() {
    List<String> lines = FileOps.readFileLines("src/test/resources/sample.txt");
    List<String> words = lines.stream().flatMap(e -> Arrays.stream(e.split("[\\s.,;:!?\"]+")))
          .distinct()
          .toList();
    assertThat(instanceUnderTest.getLongestWords(words))
        .isEqualTo(List.of("grasshopper"));
    assertThat(instanceUnderTest.getLongestWords(Collections.emptyList()))
        .isEqualTo(Collections.emptyList());
  }
}