package com.project.ems.employee;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> findAll();

    EmployeeDto findById(Integer id);

    EmployeeDto save(EmployeeDto employeeDto);

    EmployeeDto updateById(EmployeeDto employeeDto, Integer id);

    EmployeeDto disableById(Integer id);

    List<EmployeeDto> convertToDtos(List<Employee> employees);

    List<Employee> convertToEntities(List<EmployeeDto> employeeDtos);

    EmployeeDto convertToDto(Employee employee);

    Employee convertToEntity(EmployeeDto employeeDto);

    Employee findEntityById(Integer id);
}
