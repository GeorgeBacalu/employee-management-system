package com.project.ems.service;

import com.project.ems.entity.Employee;
import com.project.ems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Integer id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee with id " + id + " not found"));
    }

    @Override
    public Employee save(Employee employee) {
        employee.setIsActive(true);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateById(Employee employee, Integer id) {
        Employee employeeToUpdate = findById(id);
        employeeToUpdate.setName(employee.getName());
        employeeToUpdate.setEmail(employee.getEmail());
        employeeToUpdate.setPassword(employee.getPassword());
        employeeToUpdate.setMobile(employee.getMobile());
        employeeToUpdate.setAddress(employee.getAddress());
        employeeToUpdate.setBirthday(employee.getBirthday());
        employeeToUpdate.setRole(employee.getRole());
        employeeToUpdate.setAuthorities(employee.getAuthorities());
        employeeToUpdate.setEmploymentType(employee.getEmploymentType());
        employeeToUpdate.setPosition(employee.getPosition());
        employeeToUpdate.setGrade(employee.getGrade());
        employeeToUpdate.setSalary(employee.getSalary());
        employeeToUpdate.setHiredAt(employee.getHiredAt());
        employeeToUpdate.setExperiences(employee.getExperiences());
        employeeToUpdate.setStudies(employee.getStudies());
        employeeToUpdate.setTrainer(employee.getTrainer());
        return employeeRepository.save(employeeToUpdate);
    }

    @Override
    public Employee disableById(Integer id) {
        Employee employeeToDisable = findById(id);
        employeeToDisable.setIsActive(false);
        return employeeRepository.save(employeeToDisable);
    }
}
