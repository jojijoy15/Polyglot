package com.utils.datagenerator;

import com.revision.model.SalariedEmployee;
import com.utils.datagenerator.configurations.FakerConfiguration;
import java.util.List;
import java.util.stream.IntStream;
import net.datafaker.Faker;

public class EmployeeGenerator {

  public static List<SalariedEmployee> generateEmployees() {
    Faker f = FakerConfiguration.createSimpeFaker();
    List<SalariedEmployee> salariedEmployees = IntStream.rangeClosed(1, 10)
        .mapToObj(e -> new SalariedEmployee(
            f.name().firstName(), f.name().lastName(),
            f.number().numberBetween(18, 50),
            f.number().randomDouble(0, 1000, 5000),
            f.number().numberBetween(1, 10))
        ).toList();
    return salariedEmployees;
  }

}
