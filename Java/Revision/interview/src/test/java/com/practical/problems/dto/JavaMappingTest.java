package com.practical.problems.dto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.practical.model.Employee;
import com.practical.model.Person;
import com.revision.utils.FileOps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JavaMappingTest {

  private JavaMapping mapper;

  @BeforeEach
  void setUp() {
    mapper = new JavaMapping();
  }

  @Test
  void mapPersonToEmployee() {
    Person person = new JsonConverstions().convertJsonToGivenType(
        FileOps.getFileContents("src/test/resources/jsonSamples/personSample.json"),
        Person.class
    );
    final Employee employee = mapper.mapPersonToEmployee(person);
    assertThat(employee)
        .satisfies(e -> {
          assertThat(e.firstName()).isEqualTo("Joji");
          assertThat(e.sex()).isEqualTo("M");
        });
  }
}