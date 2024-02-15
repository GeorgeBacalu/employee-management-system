package com.project.ems.role;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleRestController implements RoleApi {

    private final RoleService roleService;

    @Override @GetMapping
    public ResponseEntity<List<RoleDto>> findAll() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @Override @GetMapping("/{id}")
    public ResponseEntity<RoleDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @Override @PostMapping
    public ResponseEntity<RoleDto> save(@RequestBody @Valid RoleDto roleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.save(roleDto));
    }
}
