package com.project.ems.controller;

import com.project.ems.entity.Authority;
import com.project.ems.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authorities")
@RequiredArgsConstructor
public class AuthorityRestController {

    private final AuthorityService authorityService;

    @GetMapping
    public ResponseEntity<List<Authority>> findAll() {
        return ResponseEntity.ok(authorityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Authority> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(authorityService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Authority> save(@RequestBody Authority authority) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorityService.save(authority));
    }
}
