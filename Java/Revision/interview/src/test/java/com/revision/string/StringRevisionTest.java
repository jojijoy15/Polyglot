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
    this.instanceUnderTest = new StringRevision();
  }

  @Test
  void test_should_return_empty_map_for_empty_word() {
    String word = "";
    Map<Character, Long> occurrences = this.instanceUnderTest
        .countCharacterOccurrencesInWord(word);
    Assertions.assertThat(occurrences).isEmpty();
  }

  @Test
  void test_should_return_map_of_character_and_its_count_in_word() {
    String word = "Dummy Word";
    Map<Character, Long> occurrences = this.instanceUnderTest
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
  void test_should_check_if_two_given_string_are_anagram() {
    var first = "program";
    var second = "grmproa";
    assertThat(this.instanceUnderTest.checkAnagram(first, second)).isTrue();
    assertThat(this.instanceUnderTest.checkAnagram(first, "")).isFalse();
  }

  @Test
  void test_should_correctly_detect_palindrome() {
    assertThat(this.instanceUnderTest.checkIfPalindrome("malayalam")).isTrue();
    assertThat(this.instanceUnderTest.checkIfPalindrome("")).isTrue();
    assertThat(this.instanceUnderTest.checkIfPalindrome("a")).isTrue();
    assertThat(this.instanceUnderTest.checkIfPalindrome("abba")).isTrue();
    assertThat(this.instanceUnderTest.checkIfPalindrome("ab")).isFalse();
  }

  @Test
  void test_should_find_words_with_max_length() {
    List<String> lines = FileOps.readFileLines("src/test/resources/sample.txt");
    List<String> words = lines.stream().flatMap(e -> Arrays.stream(e.split("[\\s.,;:!?\"]+")))
          .distinct()
          .toList();
    assertThat(this.instanceUnderTest.getLongestWords(words))
        .isEqualTo(List.of("grasshopper"));
    assertThat(this.instanceUnderTest.getLongestWords(Collections.emptyList()))
        .isEqualTo(Collections.emptyList());
  }
}