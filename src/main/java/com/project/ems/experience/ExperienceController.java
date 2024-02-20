package com.project.ems.experience;

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
@RequestMapping("/experiences")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping
    public String findAllPage(Model model, @PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        Page<ExperienceDto> experienceDtosPage = experienceService.findAllByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrExperiences = experienceDtosPage.getTotalElements();
        int nrPages = experienceDtosPage.getTotalPages();
        model.addAttribute(EXPERIENCES_ATTRIBUTE, experienceService.convertToEntities(experienceDtosPage.getContent()));
        model.addAttribute("nrExperiences", nrExperiences);
        model.addAttribute("nrPages", nrPages);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("field", field);
        model.addAttribute("direction", direction);
        model.addAttribute("key", key);
        model.addAttribute("pageStartIndex", getPageStartIndex(page, size));
        model.addAttribute("pageEndIndex", getPageEndIndex(page, size, nrExperiences));
        model.addAttribute("pageNavigationStartIndex", getPageNavigationStartIndex(page, nrPages));
        model.addAttribute("pageNavigationEndIndex", getPageNavigationEndIndex(page, nrPages));
        model.addAttribute("searchRequest", new SearchRequest(page, size, field + "," + direction, key));
        return EXPERIENCES_VIEW;
    }

    @PostMapping("/search")
    public String findAllByKey(@ModelAttribute SearchRequest searchRequest, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("page", searchRequest.getPage());
        redirectAttributes.addAttribute("size", searchRequest.getSize());
        redirectAttributes.addAttribute("sort", searchRequest.getSort());
        redirectAttributes.addAttribute("key", searchRequest.getKey());
        return REDIRECT_EXPERIENCES_VIEW;
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
    public String deleteById(@PathVariable Integer id, RedirectAttributes redirectAttributes, @PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        experienceService.deleteById(id);
        Page<ExperienceDto> experienceDtosPage = experienceService.findAllByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        redirectAttributes.addAttribute("page", Math.max(0, page - (experienceDtosPage.hasContent() ? 1 : 0)));
        redirectAttributes.addAttribute("size", size);
        redirectAttributes.addAttribute("sort", field + "," + direction);
        redirectAttributes.addAttribute("key", key);
        return REDIRECT_EXPERIENCES_VIEW;
    }
}
