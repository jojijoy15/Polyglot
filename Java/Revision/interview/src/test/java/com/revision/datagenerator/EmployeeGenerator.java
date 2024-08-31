package com.revision.datagenerator;

import com.revision.datagenerator.configurations.FakerConfiguration;
import com.revision.model.Employee;
import java.util.List;
import java.util.stream.IntStream;
import net.datafaker.Faker;

public class EmployeeGenerator {

  public static List<Employee> generateEmployees() {
    Faker f = FakerConfiguration.createSimpeFaker();
    List<Employee> employees = IntStream.rangeClosed(1, 10)
        .mapToObj(e -> new Employee(
            f.name().firstName(), f.name().lastName(),
            f.number().numberBetween(18, 50),
            f.number().randomDouble(0, 1000, 5000),
            f.number().numberBetween(1, 10))
        ).toList();
    return employees;
  }

}
