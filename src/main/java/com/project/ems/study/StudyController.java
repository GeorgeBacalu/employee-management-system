package com.project.ems.study;

import com.project.ems.wrapper.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.util.PageUtil.*;

@Controller
@RequestMapping("/studies")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    @GetMapping
    public String findAllPage(Model model, @PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        Page<StudyDto> studyDtosPage = studyService.findAllByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrStudies = studyDtosPage.getTotalElements();
        int nrPages = studyDtosPage.getTotalPages();
        model.addAttribute(STUDIES_ATTRIBUTE, studyService.convertToEntities(studyDtosPage.getContent()));
        model.addAttribute("nrStudies", nrStudies);
        model.addAttribute("nrPages", nrPages);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("field", field);
        model.addAttribute("direction", direction);
        model.addAttribute("key", key);
        model.addAttribute("pageStartIndex", getPageStartIndex(page, size));
        model.addAttribute("pageEndIndex", getPageEndIndex(page, size, nrStudies));
        model.addAttribute("pageNavigationStartIndex", getPageNavigationStartIndex(page, nrPages));
        model.addAttribute("pageNavigationEndIndex", getPageNavigationEndIndex(page, nrPages));
        model.addAttribute("searchRequest", new SearchRequest(page, size, field + "," + direction, key));
        return STUDIES_VIEW;
    }

    @PostMapping("/search")
    public String findAllByKey(@ModelAttribute SearchRequest searchRequest, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("page", searchRequest.getPage());
        redirectAttributes.addAttribute("size", searchRequest.getSize());
        redirectAttributes.addAttribute("sort", searchRequest.getSort());
        redirectAttributes.addAttribute("key", searchRequest.getKey());
        return REDIRECT_STUDIES_VIEW;
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
    public String deleteById(@PathVariable Integer id, RedirectAttributes redirectAttributes, @PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        studyService.deleteById(id);
        Page<StudyDto> studyDtosPage = studyService.findAllByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        redirectAttributes.addAttribute("page", Math.max(0, page - (studyDtosPage.hasContent() ? 1 : 0)));
        redirectAttributes.addAttribute("size", size);
        redirectAttributes.addAttribute("sort", field + "," + direction);
        redirectAttributes.addAttribute("key", key);
        return REDIRECT_STUDIES_VIEW;
    }
}
