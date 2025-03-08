package com.practical.problems.dto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.entry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.practical.model.Person;
import com.revision.utils.FileOps;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonConverstionsTest {

  private JsonConverstions jsonConverstions;

  @BeforeEach
  void setUp() {
    jsonConverstions = new JsonConverstions();
  }

  @Test
  void test_convertJsonToGivenType() {
    //Act
    Person person = jsonConverstions
        .convertJsonToGivenType(
            FileOps.getFileContents("src/test/resources/jsonSamples/personSample.json"),
            Person.class
        );
    //Assert
    assertThat(person)
      .satisfies(p -> {
        assertThat(p.name()).isEqualTo("Joji");
        assertThat(p.age()).isEqualTo(30);
        assertThat(p.id()).isEqualTo("af6ca338-df37-477c-bc34-44d64556a6b2");
        assertThat(p.identifiedAs().charValue()).isEqualTo('M');
      });
  }

  @Test
  void test_convertJsonToGiven_LinkedHashMapType() {
    //Act
    LinkedHashMap<String, Object> person = jsonConverstions
        .convertJsonToGivenType(
            FileOps.getFileContents("src/test/resources/jsonSamples/personSample.json"),
            LinkedHashMap.class
        );
    //Assert
    org.assertj.core.api.AssertionsForInterfaceTypes
        .assertThat(person).containsAllEntriesOf(
            Map.ofEntries(
                entry("id", "af6ca338-df37-477c-bc34-44d64556a6b2"),
                entry("name", "Joji"), entry("age", 30), entry("identifiedAs", "M")
            ));
  }

  @Test
  void test_convertJsonToGiven_ListType() {
    //Act
    List<Person> persons = jsonConverstions
        .convertJsonToTypeRef(
            FileOps.getFileContents("src/test/resources/jsonSamples/persons.json"),
            new TypeReference<List<Person>>(){}
        );
    //Assert
    org.assertj.core.api.AssertionsForInterfaceTypes
        .assertThat(persons).isNotEmpty().hasSize(3);
  }

  @Test
  void test_convertGivenTypeToJson() {
    //Arrange
    Person testPerson = new Person("1", "Joji", 31, 'M');
    String personJson = jsonConverstions.convertGivenTypeToJson(testPerson);
    //Assert
    JsonPathQuery jPath = new JsonPathQuery();
    Map<String, Object> result = jPath
        .getJsonElementsAsVariables(personJson, List.of("id", "name", "age", "identifiedAs"));
    org.assertj.core.api.AssertionsForInterfaceTypes
        .assertThat(result).containsAllEntriesOf(
            Map.ofEntries(
              entry("id", "1"), entry("name", "Joji"),
              entry("age", 31), entry("identifiedAs", "M")
        ));
  }
}