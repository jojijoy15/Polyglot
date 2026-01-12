package model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Employee {

  private String name;
  private BigDecimal salary;
  private String department;

}
