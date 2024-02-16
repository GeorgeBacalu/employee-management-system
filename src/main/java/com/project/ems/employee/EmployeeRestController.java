package com.project.ems.employee;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @Override @GetMapping("/active")
    public ResponseEntity<List<EmployeeDto>> findAllActive() {
        return ResponseEntity.ok(employeeService.findAllActive());
    }

    @Override @GetMapping("/pagination")
    public ResponseEntity<Page<EmployeeDto>> findAllByKey(@PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        return ResponseEntity.ok(employeeService.findAllByKey(pageable, key));
    }

    @Override @GetMapping("/active/pagination")
    public ResponseEntity<Page<EmployeeDto>> findAllActiveByKey(@PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        return ResponseEntity.ok(employeeService.findAllActiveByKey(pageable, key));
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
