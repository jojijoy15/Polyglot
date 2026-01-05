package data;

import java.math.BigDecimal;
import model.Employee;
import net.datafaker.Faker;

public class Generator {

  public static Employee getEmployee() {
    Faker faker = new Faker();
    String name = faker.name().firstName();
    BigDecimal salary = new BigDecimal(faker.number().randomDouble(2, 0, 300));
    String department = faker.commerce().department();
    return new Employee(name, salary, department);
  }

  public static String getRandomWords() {
    Faker faker = new Faker();
    return faker.word().adjective();
  }

}
