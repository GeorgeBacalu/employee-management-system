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

    @GetMapping("/save")
    public String getSavePage(Model model) {
        model.addAttribute("studyDto", new StudyDto());
        return "study/save-study";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute StudyDto studyDto) {
        studyService.save(studyDto);
        return "redirect:/studies";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(Model model, @PathVariable Integer id) {
        model.addAttribute("studyDto", studyService.findById(id));
        return "study/update-study";
    }

    @PostMapping("/update/{id}")
    public String updateById(@ModelAttribute StudyDto studyDto, @PathVariable Integer id) {
        studyService.updateById(studyDto, id);
        return "redirect:/studies";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        studyService.deleteById(id);
        return "redirect:/studies";
    }
}
