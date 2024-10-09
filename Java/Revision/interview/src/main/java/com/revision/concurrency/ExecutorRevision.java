package com.revision.concurrency;

import com.revision.string.StringRevision;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecutorRevision {

  /*
    System to check a long list for anagrams are return them
  */
  public Map<String, String> findAnagrams(List<String> words) {
    StringRevision algorithms = new StringRevision();
    Map<String, String> anagrams = new HashMap<>();
    for(int i = 0; i < words.size()-1; ++i) {
      String firstWord = words.get(i);
      for(int j = i + 1; j < words.size(); ++j) {
        String secondWord = words.get(j);
        if(algorithms.detectAnagram(firstWord, secondWord)) {
          anagrams.put(firstWord, secondWord);
        }
      }
    }
    return anagrams;
  }

}
