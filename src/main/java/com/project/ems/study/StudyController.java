package com.project.ems.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.project.ems.constants.Constants.*;

@Controller
@RequestMapping("/studies")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    @GetMapping
    public String findAllPage(Model model) {
        model.addAttribute(STUDIES_ATTRIBUTE, studyService.convertToEntities(studyService.findAll()));
        return STUDIES_VIEW;
    }

    @GetMapping("/{id}")
    public String findByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute(STUDY_ATTRIBUTE, studyService.findEntityById(id));
        return STUDY_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSavePage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute(STUDY_DTO_ATTRIBUTE, id == -1 ? new StudyDto() : studyService.findById(id));
        return SAVE_STUDY_VIEW;
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute StudyDto studyDto, @PathVariable Integer id) {
        if (id == -1) {
            studyService.save(studyDto);
        } else {
            studyService.updateById(studyDto, id);
        }
        return REDIRECT_STUDIES_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        studyService.deleteById(id);
        return REDIRECT_STUDIES_VIEW;
    }
}
