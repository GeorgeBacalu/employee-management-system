package com.project.ems.experience;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experiences")
@RequiredArgsConstructor
public class ExperienceRestController implements ExperienceApi {

    private final ExperienceService experienceService;

    @Override @GetMapping
    public ResponseEntity<List<ExperienceDto>> findAll() {
        return ResponseEntity.ok(experienceService.findAll());
    }

    @Override @GetMapping("/{id}")
    public ResponseEntity<ExperienceDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(experienceService.findById(id));
    }

    @Override @PostMapping
    public ResponseEntity<ExperienceDto> save(@RequestBody @Valid ExperienceDto experienceDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(experienceService.save(experienceDto));
    }

    @Override @PutMapping("/{id}")
    public ResponseEntity<ExperienceDto> updateById(@RequestBody @Valid ExperienceDto experienceDto, @PathVariable Integer id) {
        return ResponseEntity.ok(experienceService.updateById(experienceDto, id));
    }

    @Override @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        experienceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
