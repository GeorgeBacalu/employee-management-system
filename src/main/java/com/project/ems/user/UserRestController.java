package com.project.ems.user;

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
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserDto> save(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateById(@RequestBody UserDto userDto, @PathVariable Integer id) {
        return ResponseEntity.ok(userService.updateById(userDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> disableById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.disableById(id));
    }
}
