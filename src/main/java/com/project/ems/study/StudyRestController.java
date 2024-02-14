package com.project.ems.study;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/studies")
@RequiredArgsConstructor
public class StudyRestController {

    private final StudyService studyService;

    @GetMapping
    public ResponseEntity<List<StudyDto>> findAll() {
        return ResponseEntity.ok(studyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudyDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(studyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<StudyDto> save(@RequestBody @Valid StudyDto studyDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studyService.save(studyDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudyDto> updateById(@RequestBody @Valid StudyDto studyDto, @PathVariable Integer id) {
        return ResponseEntity.ok(studyService.updateById(studyDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        studyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
