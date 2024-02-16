package com.project.ems.user;

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
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController implements UserApi {

    private final UserService userService;

    @Override @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Override @GetMapping("/active")
    public ResponseEntity<List<UserDto>> findAllActive() {
        return ResponseEntity.ok(userService.findAllActive());
    }

    @Override @GetMapping("/pagination")
    public ResponseEntity<Page<UserDto>> findAllByKey(@PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        return ResponseEntity.ok(userService.findAllByKey(pageable, key));
    }

    @Override @GetMapping("/active/pagination")
    public ResponseEntity<Page<UserDto>> findAllActiveByKey(@PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        return ResponseEntity.ok(userService.findAllActiveByKey(pageable, key));
    }

    @Override @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Override @PostMapping
    public ResponseEntity<UserDto> save(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDto));
    }

    @Override @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateById(@RequestBody @Valid UserDto userDto, @PathVariable Integer id) {
        return ResponseEntity.ok(userService.updateById(userDto, id));
    }

    @Override @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> disableById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.disableById(id));
    }
}
