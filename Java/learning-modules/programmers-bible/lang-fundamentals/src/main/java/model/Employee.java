package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Employee {

  private String name;
  private BigDecimal salary;
  private String department;

}
