package com.project.ems.trainer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/trainers")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    @GetMapping
    public String findAllActivePage(Model model) {
        model.addAttribute("trainers", trainerService.convertToEntities(trainerService.findAllActive()));
        return "trainer/trainers";
    }

    @GetMapping("/{id}")
    public String findByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("trainer", trainerService.findEntityById(id));
        return "trainer/trainer-details";
    }

    @GetMapping("/save")
    public String getSavePage(Model model) {
        model.addAttribute("trainerDto", new TrainerDto());
        return "trainer/save-trainer";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute TrainerDto trainerDto) {
        trainerService.save(trainerDto);
        return "redirect:/trainers";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(Model model, @PathVariable Integer id) {
        model.addAttribute("trainerDto", trainerService.findById(id));
        return "trainer/update-trainer";
    }

    @PostMapping("/update/{id}")
    public String updateById(@ModelAttribute TrainerDto trainerDto, @PathVariable Integer id) {
        trainerService.updateById(trainerDto, id);
        return "redirect:/trainers";
    }

    @GetMapping("/delete/{id}")
    public String disableById(@PathVariable Integer id) {
        trainerService.disableById(id);
        return "redirect:/trainers";
    }
}
