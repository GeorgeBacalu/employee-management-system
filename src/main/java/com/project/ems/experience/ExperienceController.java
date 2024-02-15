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

    @GetMapping("/save")
    public String getSavePage(Model model) {
        model.addAttribute("experienceDto", new ExperienceDto());
        return "experience/save-experience";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ExperienceDto experienceDto) {
        experienceService.save(experienceDto);
        return "redirect:/experiences";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(Model model, @PathVariable Integer id) {
        model.addAttribute("experienceDto", experienceService.findById(id));
        return "experience/update-experience";
    }

    @PostMapping("/update/{id}")
    public String updateById(@ModelAttribute ExperienceDto experienceDto, @PathVariable Integer id) {
        experienceService.updateById(experienceDto, id);
        return "redirect:/experiences";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        experienceService.deleteById(id);
        return "redirect:/experiences";
    }
}
