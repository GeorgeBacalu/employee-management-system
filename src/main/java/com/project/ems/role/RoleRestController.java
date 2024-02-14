package com.project.ems.role;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleRestController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleDto>> findAll() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RoleDto> save(@RequestBody RoleDto roleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.save(roleDto));
    }
}
