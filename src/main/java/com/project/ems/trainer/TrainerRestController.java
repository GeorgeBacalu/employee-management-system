package com.project.ems.trainer;

import jakarta.validation.Valid;
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
    public ResponseEntity<List<TrainerDto>> findAll() {
        return ResponseEntity.ok(trainerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(trainerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TrainerDto> save(@RequestBody @Valid TrainerDto trainerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trainerService.save(trainerDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainerDto> updateById(@RequestBody @Valid TrainerDto trainerDto, @PathVariable Integer id) {
        return ResponseEntity.ok(trainerService.updateById(trainerDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TrainerDto> disableById(@PathVariable Integer id) {
        return ResponseEntity.ok(trainerService.disableById(id));
    }
}
