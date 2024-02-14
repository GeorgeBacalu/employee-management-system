package com.project.ems.authority;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authorities")
@RequiredArgsConstructor
public class AuthorityRestController implements AuthorityApi {

    private final AuthorityService authorityService;

    @Override @GetMapping
    public ResponseEntity<List<AuthorityDto>> findAll() {
        return ResponseEntity.ok(authorityService.findAll());
    }

    @Override @GetMapping("/{id}")
    public ResponseEntity<AuthorityDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(authorityService.findById(id));
    }

    @Override @PostMapping
    public ResponseEntity<AuthorityDto> save(@RequestBody @Valid AuthorityDto authorityDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorityService.save(authorityDto));
    }
}
