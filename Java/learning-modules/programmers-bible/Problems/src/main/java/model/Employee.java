package model;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Employee {

  private String name;
  private BigDecimal salary;
  private String department;

}
