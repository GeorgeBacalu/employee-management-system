package com.project.ems.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.project.ems.constants.Constants.*;

@Controller
@RequestMapping("/authorities")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService;

    @GetMapping
    public String findAllPage(Model model) {
        model.addAttribute(AUTHORITIES_ATTRIBUTE, authorityService.convertToEntities(authorityService.findAll()));
        return AUTHORITIES_VIEW;
    }

    @GetMapping("/{id}")
    public String findByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute(AUTHORITY_ATTRIBUTE, authorityService.findEntityById(id));
        return AUTHORITY_DETAILS_VIEW;
    }

    @GetMapping("/save")
    public String getSavePage(Model model) {
        model.addAttribute(AUTHORITY_DTO_ATTRIBUTE, new AuthorityDto());
        return SAVE_AUTHORITY_VIEW;
    }

    @PostMapping("/save")
    public String save(@ModelAttribute AuthorityDto authorityDto) {
        authorityService.save(authorityDto);
        return REDIRECT_AUTHORITIES_VIEW;
    }
}
