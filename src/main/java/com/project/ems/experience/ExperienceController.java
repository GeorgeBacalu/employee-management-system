package com.project.ems.experience;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.project.ems.constants.Constants.*;

@Controller
@RequestMapping("/experiences")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping
    public String findAllPage(Model model) {
        model.addAttribute(EXPERIENCES_ATTRIBUTE, experienceService.convertToEntities(experienceService.findAll()));
        return EXPERIENCES_VIEW;
    }

    @GetMapping("/{id}")
    public String findByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute(EXPERIENCE_ATTRIBUTE, experienceService.findEntityById(id));
        return EXPERIENCE_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSavePage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute(EXPERIENCE_DTO_ATTRIBUTE, id == -1 ? new ExperienceDto() : experienceService.findById(id));
        return SAVE_EXPERIENCE_VIEW;
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute ExperienceDto experienceDto, @PathVariable Integer id) {
        if (id == -1) {
            experienceService.save(experienceDto);
        } else {
            experienceService.updateById(experienceDto, id);
        }
        return REDIRECT_EXPERIENCES_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        experienceService.deleteById(id);
        return REDIRECT_EXPERIENCES_VIEW;
    }
}
