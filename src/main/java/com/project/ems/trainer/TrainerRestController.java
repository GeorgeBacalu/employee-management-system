package com.project.ems.trainer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
public class TrainerRestController {

    private final TrainerService trainerService;

    @GetMapping
    public ResponseEntity<List<Trainer>> findAll() {
        return ResponseEntity.ok(trainerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trainer> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(trainerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Trainer> save(@RequestBody Trainer trainer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trainerService.save(trainer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trainer> updateById(@RequestBody Trainer trainer, @PathVariable Integer id) {
        return ResponseEntity.ok(trainerService.updateById(trainer, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Trainer> disableById(@PathVariable Integer id) {
        return ResponseEntity.ok(trainerService.disableById(id));
    }
}
