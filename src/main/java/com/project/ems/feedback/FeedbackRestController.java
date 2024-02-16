package com.project.ems.feedback;

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
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackRestController implements FeedbackApi {

    private final FeedbackService feedbackService;

    @Override @GetMapping
    public ResponseEntity<List<FeedbackDto>> findAll() {
        return ResponseEntity.ok(feedbackService.findAll());
    }

    @Override @GetMapping("/pagination")
    public ResponseEntity<Page<FeedbackDto>> findAllByKey(@PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        return ResponseEntity.ok(feedbackService.findAllByKey(pageable, key));
    }

    @Override @GetMapping("/{id}")
    public ResponseEntity<FeedbackDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(feedbackService.findById(id));
    }

    @Override @PostMapping
    public ResponseEntity<FeedbackDto> save(@RequestBody @Valid FeedbackDto feedbackDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.save(feedbackDto));
    }

    @Override @PutMapping("/{id}")
    public ResponseEntity<FeedbackDto> updateById(@RequestBody @Valid FeedbackDto feedbackDto, @PathVariable Integer id) {
        return ResponseEntity.ok(feedbackService.updateById(feedbackDto, id));
    }

    @Override @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        feedbackService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
