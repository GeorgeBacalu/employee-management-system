package com.project.ems.employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    Employee findById(Integer id);

    Employee save(Employee employee);

    Employee updateById(Employee employee, Integer id);

    Employee disableById(Integer id);
}
