package com.practical.problems.dto;

import static org.assertj.core.api.AssertionsForClassTypes.entry;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.revision.utils.FileOps;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonPathQueryTest {

  private JsonPathQuery jsonPathQuery;

  @BeforeEach
  void setUp() {
    jsonPathQuery = new JsonPathQuery();
  }

  @Test
  void test_get_json_elements_as_variables_when_all_fields_present() {
    String jsonObject = FileOps.getFileContents("src/test/resources/jsonSamples/NestedSample.json");
    List<String> fields = List.of("name", "coffee.country.id", "coffee.region[*].id");
    Map<String, Object> result = jsonPathQuery.getJsonElementsAsVariables(jsonObject, fields);
    assertThat(result).containsAllEntriesOf(
        Map.ofEntries(
          entry("name", "BrewCoffee"),
          entry("id", 2),
          entry("coffeeregionid", List.of(1, 2))
        )
    );
  }

  @Test
  void test_get_json_elements_as_variables_when_some_fields_present() {
    String jsonObject = FileOps.getFileContents("src/test/resources/jsonSamples/NestedSample.json");
    List<String> fields = List.of("unknown", "coffee.country.id");
    Map<String, Object> result = jsonPathQuery.getJsonElementsAsVariables(jsonObject, fields);
    Map<String, Object> expectedResult = new HashMap<>();
    expectedResult.put("id", 2);
    expectedResult.put("unknown", null);
    assertThat(result).containsAllEntriesOf(expectedResult);
  }
}