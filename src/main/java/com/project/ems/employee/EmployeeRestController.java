package com.project.ems.employee;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeRestController implements EmployeeApi {

    private final EmployeeService employeeService;

    @Override @GetMapping
    public ResponseEntity<List<EmployeeDto>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @Override @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @Override @PostMapping
    public ResponseEntity<EmployeeDto> save(@RequestBody @Valid EmployeeDto employeeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.save(employeeDto));
    }

    @Override @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateById(@RequestBody @Valid EmployeeDto employeeDto, @PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.updateById(employeeDto, id));
    }

    @Override @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeDto> disableById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.disableById(id));
    }
}
