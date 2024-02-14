package com.project.ems.employee;

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
    public ResponseEntity<List<EmployeeDto>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> save(@RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.save(employeeDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateById(@RequestBody EmployeeDto employeeDto, @PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.updateById(employeeDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeDto> disableById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.disableById(id));
    }
}
