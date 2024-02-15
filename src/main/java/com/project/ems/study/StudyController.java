package com.project.ems.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/studies")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    @GetMapping
    public String findAllPage(Model model) {
        model.addAttribute("studies", studyService.convertToEntities(studyService.findAll()));
        return "study/studies";
    }

    @GetMapping("/{id}")
    public String finByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("study", studyService.findEntityById(id));
        return "study/study-details";
    }

    @GetMapping("/save/{id}")
    public String getSavePage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute("studyDto", id == -1 ? new StudyDto() : studyService.findById(id));
        return "study/save-study";
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute StudyDto studyDto, @PathVariable Integer id) {
        if (id == -1) {
            studyService.save(studyDto);
        } else {
            studyService.updateById(studyDto, id);
        }
        return "redirect:/studies";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        studyService.deleteById(id);
        return "redirect:/studies";
    }
}
