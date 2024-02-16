package com.project.ems.trainer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.project.ems.constants.Constants.*;

@Controller
@RequestMapping("/trainers")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    @GetMapping
    public String findAllActivePage(Model model) {
        model.addAttribute(TRAINERS_ATTRIBUTE, trainerService.convertToEntities(trainerService.findAllActive()));
        return TRAINERS_VIEW;
    }

    @GetMapping("/{id}")
    public String findByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute(TRAINER_ATTRIBUTE, trainerService.findEntityById(id));
        return TRAINER_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSavePage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute(TRAINER_DTO_ATTRIBUTE, id == -1 ? new TrainerDto() : trainerService.findById(id));
        return SAVE_TRAINER_VIEW;
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute TrainerDto trainerDto, @PathVariable Integer id) {
        if (id == -1) {
            trainerService.save(trainerDto);
        } else {
            trainerService.updateById(trainerDto, id);
        }
        return REDIRECT_TRAINERS_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String disableById(@PathVariable Integer id) {
        trainerService.disableById(id);
        return REDIRECT_TRAINERS_VIEW;
    }
}
