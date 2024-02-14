package com.project.ems.controller;

import com.project.ems.entity.Employee;
import com.project.ems.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Employee> save(@RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.save(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateById(@RequestBody Employee employee, @PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.updateById(employee, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> disableById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.disableById(id));
    }
}
