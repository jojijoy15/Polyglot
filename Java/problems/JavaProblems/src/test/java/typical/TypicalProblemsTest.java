package typical;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junitpioneer.jupiter.json.JsonSource;
import org.junitpioneer.jupiter.json.Property;

class TypicalProblemsTest {

  TypicalProblems problems = new TypicalProblems();

  @ParameterizedTest
  @JsonSource({"""
       [
         {"word" :"hello", "status": false},
         {"word" :"heleh", "status": true},
         {"word" :"abaccaba", "status": true},
         {"word" :"", "status": true},
         {"word" :"a", "status": true}
       ]
      """
  })
  void checkWhetherStringIsPalindrome(@Property("word") String word, @Property("status") Boolean status) {
    final boolean result = problems.checkWhetherStringIsPalindrome(word);
    assertThat(result).isEqualTo(status);
  }

  @ParameterizedTest
  @JsonSource({"""
       [
         {"word" :"hello", "reverse": "olleh"},
         {"word" :"", "reverse": ""},
         {"word" :"a", "reverse": "a"},
         {"word" :"ab", "reverse": "ba"}
       ]
      """
  })
  void reverse(@Property("word") String word, @Property("reverse")String reverse) {
    final String actual = problems.reverse(word);
    assertThat(actual).isEqualTo(reverse);
  }

  @Test
  void removeConsecutiveDuplicates() {
    final List<Integer> actual = problems.removeConsecutiveDuplicates(Arrays.asList(1, 3, 4, 4, 3, 3, 2, 5, 5));
    assertThat(actual).containsExactly(1, 3, 4, 3, 2, 5);

  }
}