package com.project.ems.trainer;

import com.project.ems.wrapper.PageWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.ems.converter.PageWrapperConverter.convertToWrapper;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
public class TrainerRestController implements TrainerApi {

    private final TrainerService trainerService;

    @Override @GetMapping
    public ResponseEntity<List<TrainerDto>> findAll() {
        return ResponseEntity.ok(trainerService.findAll());
    }

    @Override @GetMapping("/active")
    public ResponseEntity<List<TrainerDto>> findAllActive() {
        return ResponseEntity.ok(trainerService.findAllActive());
    }

    @Override @GetMapping("/pagination")
    public ResponseEntity<PageWrapper<TrainerDto>> findAllByKey(@PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        return ResponseEntity.ok(convertToWrapper(trainerService.findAllByKey(pageable, key)));
    }

    @Override @GetMapping("/active/pagination")
    public ResponseEntity<PageWrapper<TrainerDto>> findAllActiveByKey(@PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        return ResponseEntity.ok(convertToWrapper(trainerService.findAllActiveByKey(pageable, key)));
    }

    @Override @GetMapping("/{id}")
    public ResponseEntity<TrainerDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(trainerService.findById(id));
    }

    @Override @PostMapping
    public ResponseEntity<TrainerDto> save(@RequestBody @Valid TrainerDto trainerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trainerService.save(trainerDto));
    }

    @Override @PutMapping("/{id}")
    public ResponseEntity<TrainerDto> updateById(@RequestBody @Valid TrainerDto trainerDto, @PathVariable Integer id) {
        return ResponseEntity.ok(trainerService.updateById(trainerDto, id));
    }

    @Override @DeleteMapping("/{id}")
    public ResponseEntity<TrainerDto> disableById(@PathVariable Integer id) {
        return ResponseEntity.ok(trainerService.disableById(id));
    }
}
