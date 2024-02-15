package com.project.ems.experience;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/experiences")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping
    public String findAllPage(Model model) {
        model.addAttribute("experiences", experienceService.convertToEntities(experienceService.findAll()));
        return "experience/experiences";
    }

    @GetMapping("/{id}")
    public String finByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("experience", experienceService.findEntityById(id));
        return "experience/experience-details";
    }

    @GetMapping("/save/{id}")
    public String getSavePage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute("experienceDto", id == -1 ? new ExperienceDto() : experienceService.findById(id));
        return "experience/save-experience";
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute ExperienceDto experienceDto, @PathVariable Integer id) {
        if (id == -1) {
            experienceService.save(experienceDto);
        } else {
            experienceService.updateById(experienceDto, id);
        }
        return "redirect:/experiences";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        experienceService.deleteById(id);
        return "redirect:/experiences";
    }
}
