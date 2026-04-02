package data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import model.Employee;
import model.OrderItem;
import net.datafaker.Faker;
import org.junit.jupiter.api.Order;

public class Generator {

  public static Employee getEmployee() {
    Faker faker = new Faker();
    String name = faker.name().firstName();
    BigDecimal salary = new BigDecimal(faker.number().randomDouble(2, 0, 300));
    String department = faker.commerce().department();
    return new Employee(name, salary, department);
  }

    public static List<Employee> getEmployees() {
        List <Employee> employees = new ArrayList<>();
        employees.add(new Employee("Jason", new BigDecimal(1500), "finance"));
        employees.add(new Employee("Mason", new BigDecimal(1210), "ecommerce"));
        employees.add(new Employee("Brittany", new BigDecimal(1100), "finance"));
        employees.add(new Employee("Martin", new BigDecimal(1600), "ecommerce"));
        employees.add(new Employee("Betty", new BigDecimal(1200), "technology"));
        employees.add(new Employee("Jane", new BigDecimal(1300), "technology"));
        return employees;
    }

  public static String getRandomWords() {
    Faker faker = new Faker();
    return faker.word().adjective();
  }

  public static List<OrderItem> getOrderItems() {
    Faker faker = new Faker();

    List<OrderItem> products = IntStream.range(0, 10)
        .mapToObj( index -> {
          String productName = faker.commerce().productName();
          Integer quant = faker.number().numberBetween(10, 100);
          return new OrderItem(productName, quant);
        })
        .toList();

    return products;
  }

}
