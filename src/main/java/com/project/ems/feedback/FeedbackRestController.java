package com.project.ems.feedback;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
