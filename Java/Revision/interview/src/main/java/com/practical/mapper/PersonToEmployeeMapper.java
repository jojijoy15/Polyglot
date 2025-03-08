package com.practical.mapper;

import com.practical.model.Employee;
import com.practical.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonToEmployeeMapper {

  PersonToEmployeeMapper MAPPER = Mappers.getMapper(PersonToEmployeeMapper.class);

  @Mapping(source = "name", target = "firstName")
  @Mapping(source = "identifiedAs", target = "sex")
  public Employee fromPersonToEmployee(Person person);

}
