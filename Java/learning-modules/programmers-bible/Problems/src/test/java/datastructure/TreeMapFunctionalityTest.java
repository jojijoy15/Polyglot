package datastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TreeMapFunctionalityTest {

  private TreeMap<String, Integer> dictionary;

  @BeforeEach
  public void setUp() {
    dictionary = new TreeMap<>();

    dictionary.put("apple", 1);
    dictionary.put("anagram", 2);
    dictionary.put("bat", 3);
    dictionary.put("zebra", 26);
    dictionary.put("parrot", 16);
  }

  @Test
  public void orderOfKeys() {
    //Lexicographical ordering
    final Set<String> actual = dictionary.keySet();
    assertThat(actual)
        .containsExactly("anagram", "apple", "bat", "parrot", "zebra");
    dictionary.remove("apple");
    assertThat(actual)
        .containsExactly("anagram", "bat", "parrot", "zebra");

  }

  @Test
  public void orderOfValues() {
    //As per Lexicographical ordering of keys
    assertThat(dictionary.values())
        .containsExactly(2, 1, 3, 16, 26);
  }

  @Test
  public void ceilingEntry() {
    //As per Lexicographical ordering of keys
    final Entry<String, Integer> ceilingEntryOfApple = dictionary.ceilingEntry("app"); //closest key
    assertThat(ceilingEntryOfApple)
        .isEqualTo(Map.entry("apple", 1));
    assertThatThrownBy(() -> ceilingEntryOfApple.setValue(100))
        .isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  public void floorEntry() {
    //As per Lexicographical ordering of keys
    final Entry<String, Integer> ceilingEntryOfApple = dictionary.floorEntry("app"); //closest key
    assertThat(ceilingEntryOfApple)
        .isEqualTo(Map.entry("anagram", 2));
  }

  @Test
  public void floorKey() {
    //As per Lexicographical ordering of keys
    String key = dictionary.floorKey("ap"); //closest key
    assertThat(key)
        .isEqualTo("anagram");
  }

  @Test
  public void retrieveNaturalOrderComparator() {
    //Null Comparator when using natural order
    final Comparator<? super String> comparator = dictionary.comparator();//comparator
    assertThat(comparator)
        .isNull();
  }

  @Test
  public void computeIfAbsentForExistingKey() {
    final Integer existingValue = dictionary
        .computeIfAbsent("apple", e -> 3);//comparator
    assertThat(existingValue)
        .isEqualTo(1);
  }

  @Test
  public void computeIfAbsentForNonExistingKey() {
    final Integer existingValue = dictionary
        .computeIfAbsent("lion", e -> 21);//comparator
    assertThat(existingValue)
        .isEqualTo(21);
    assertThat(dictionary).containsEntry("lion", 21);
  }

  @Test
  public void computeIfPresentForNonExistingKey() {
    final Integer existingValue = dictionary
        .computeIfPresent("lion", (e, v) -> v + 1);//comparator
    assertThat(existingValue)
        .isNull();
    assertThat(dictionary).doesNotContainEntry("lion", 22);
  }

  @Test
  public void computeIfPresentForExistingKey() {
    final Integer existingValue = dictionary
        .computeIfPresent("apple", (e, v) -> v + 1);//comparator
    assertThat(existingValue)
        .isEqualTo(2);
    assertThat(dictionary).containsEntry("apple", 2);
  }

  @Test
  public void descendingOrderOfKeys() {
    assertThat(dictionary.descendingKeySet())
        .containsExactly("zebra", "parrot", "bat", "apple", "anagram");
  }

  @Test
  public void descendingMap() {
    final NavigableMap<String, Integer> actual = dictionary.descendingMap();
    final Map<String, Integer> expected = new LinkedHashMap<>();
    expected.put("zebra", 26);
    expected.put("parrot", 16);
    expected.put("bat", 3);
    expected.put("apple", 1);
    expected.put("anagram", 2);
    assertThat(actual).containsExactlyEntriesOf(expected);
  }

  @Test
  public void headMap() {
    final SortedMap<String, Integer> headMap = dictionary.headMap("arctic");
    Map<String, Integer> expected = new LinkedHashMap<>();
    expected.put("anagram", 2);
    expected.put("apple", 1);
    assertThat(headMap).containsExactlyEntriesOf(expected);
    assertThat(new TreeMap<>().headMap("apple")).isEmpty(); //empty if no value
  }

  @Test
  public void higherEntry() {
    final Entry<String, Integer> higherEntry = dictionary.higherEntry("arctic");
    assertThat(higherEntry).isEqualTo(Map.entry("bat", 3));
    final Entry<String, Integer> zEntry = dictionary.higherEntry("zip");
    assertThat(zEntry).isEqualTo(null);

  }

  @Test
  public void merge() {
    final Integer mergedValue = dictionary
        .merge("apple", 24, (k, v) -> v + 10);
    assertThat(mergedValue).isEqualTo(34); // existing value replace with given value and re-map
    final Integer mergedNotExistingValue = dictionary
        .merge("frappe", 100, (k, v) -> v + 10);
    assertThat(mergedNotExistingValue).isEqualTo(100); //No remapping called
  }

  @Test
  public void subMapOpenClosed() {
    Map<String, Integer> subMap = dictionary
        .subMap("bat", "zebra"); // "zebra" key not included
    Map<String, Integer> expected = new LinkedHashMap<>();
    expected.put("bat", 3);
    expected.put("parrot", 16);
    assertThat(subMap).containsExactlyEntriesOf(expected);
  }

  @Test
  public void notComparableKeyMap() {
    Map<Employee, String> map = new TreeMap<>();
    assertThatThrownBy(() -> map.put(new Employee("Joji", new BigDecimal(10.0), "commerce"), "JE"))
        .isInstanceOf(ClassCastException.class);
  }
}
