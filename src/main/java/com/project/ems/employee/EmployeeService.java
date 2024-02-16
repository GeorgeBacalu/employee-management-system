package com.project.ems.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> findAll();

    List<EmployeeDto> findAllActive();

    Page<EmployeeDto> findAllByKey(Pageable pageable, String key);

    Page<EmployeeDto> findAllActiveByKey(Pageable pageable, String key);

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
