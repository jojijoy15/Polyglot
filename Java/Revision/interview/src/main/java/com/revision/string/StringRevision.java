package com.revision.string;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringRevision {

  /*
    Count occurrences of characters
  */
  public Map<Character, Long> countCharacterOccurrencesInWord(String word) {
    return word.chars().mapToObj(c -> (char)c)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }

  /*
    Check Anagrams
  */
  public boolean checkAnagram(String first, String second) {
    var arr1 = first.chars().mapToObj(c -> (char) c).sorted(Comparator.naturalOrder()).toArray(Character[]::new);
    var arr2 = second.chars().mapToObj(c -> (char) c).sorted(Comparator.naturalOrder()).toArray(Character[]::new);
    return Arrays.equals(arr1, arr2);
  }

  /*
    Check Palindrome
  */
  public boolean checkIfPalindrome(String word) {
    return IntStream.range(0, word.length()/2)
        .allMatch( e-> word.charAt(e) == word.charAt(word.length()-1 - e));
  }

  public List<String> getLongestWords(List<String> words) {
    int maxLength = words.stream().mapToInt(String::length)
        .max()
        .orElse(0);
    return maxLength != 0
        ? words.stream().filter(e -> e.length() == maxLength).toList()
        : Collections.emptyList();
  }

}
