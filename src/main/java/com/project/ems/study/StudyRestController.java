package com.project.ems.study;

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
@RequestMapping("/api/studies")
@RequiredArgsConstructor
public class StudyRestController implements StudyApi {

    private final StudyService studyService;

    @Override @GetMapping
    public ResponseEntity<List<StudyDto>> findAll() {
        return ResponseEntity.ok(studyService.findAll());
    }

    @Override @GetMapping("/pagination")
    public ResponseEntity<Page<StudyDto>> findAllByKey(@PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        return ResponseEntity.ok(studyService.findAllByKey(pageable, key));
    }

    @Override @GetMapping("/{id}")
    public ResponseEntity<StudyDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(studyService.findById(id));
    }

    @Override @PostMapping
    public ResponseEntity<StudyDto> save(@RequestBody @Valid StudyDto studyDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studyService.save(studyDto));
    }

    @Override @PutMapping("/{id}")
    public ResponseEntity<StudyDto> updateById(@RequestBody @Valid StudyDto studyDto, @PathVariable Integer id) {
        return ResponseEntity.ok(studyService.updateById(studyDto, id));
    }

    @Override @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        studyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
