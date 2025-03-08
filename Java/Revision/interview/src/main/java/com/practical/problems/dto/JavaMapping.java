package com.practical.problems.dto;

import com.practical.mapper.PersonToEmployeeMapper;
import com.practical.model.Employee;
import com.practical.model.Person;

public class JavaMapping {

  public Employee mapPersonToEmployee(Person toBeMapped) {
    return PersonToEmployeeMapper.MAPPER.fromPersonToEmployee(toBeMapped);
  }
}
