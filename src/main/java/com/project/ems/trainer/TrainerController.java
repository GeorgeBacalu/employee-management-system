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

    @GetMapping("/save/{id}")
    public String getSavePage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute("trainerDto", id == -1 ? new TrainerDto() : trainerService.findById(id));
        return "trainer/save-trainer";
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute TrainerDto trainerDto, @PathVariable Integer id) {
        if (id == -1) {
            trainerService.save(trainerDto);
        } else {
            trainerService.updateById(trainerDto, id);
        }
        return "redirect:/trainers";
    }

    @GetMapping("/delete/{id}")
    public String disableById(@PathVariable Integer id) {
        trainerService.disableById(id);
        return "redirect:/trainers";
    }
}
