package com.project.ems.controller;

import com.project.ems.entity.User;
import com.project.ems.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateById(@RequestBody User user, @PathVariable Integer id) {
        return ResponseEntity.ok(userService.updateById(user, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> disableById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.disableById(id));
    }
}
