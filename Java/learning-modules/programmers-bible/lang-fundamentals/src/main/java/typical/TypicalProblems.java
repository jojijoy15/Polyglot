package typical;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TypicalProblems {

  public boolean checkWhetherStringIsPalindrome(String word) {
    return IntStream.range(0, word.length()/2)
        .allMatch(i -> word.charAt(i) == word.charAt(word.length() - 1 - i));
  }

  public String reverse(String word) {
      return IntStream.range(0, word.length())
      .mapToObj(e -> word.charAt(word.length() - 1 - e))
      .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
      .toString();
  }

  public List<Integer> removeConsecutiveDuplicates(List<Integer> values) {
    AtomicReference<Integer> previous = new AtomicReference<>();
    return values.stream()
        .filter(curr -> {
          Integer prev = previous.getAndSet(curr);
          return prev == null || !prev.equals(curr);
        })
        .collect(Collectors.toList());
  }

}
