package com.revision.concurrency;

import static org.assertj.core.api.Assertions.assertThat;

import com.revision.datagenerator.FileOps;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ExecutorRevisionTest {

  private final ExecutorRevision instanceUnderTest = new ExecutorRevision();

  @Test
  void test_should_return_all_anagrams_in_given_list() {
    List<String> words = FileOps.readFileLines("src/test/resources/anagrams/inputs.txt");
    Map<String, String> anagrams = instanceUnderTest.findAnagrams(words);
    assertThat(anagrams)
        .containsAllEntriesOf(FileOps.csvReader("src/test/resources/anagrams/expectedResult.csv"));
  }
}