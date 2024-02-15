package com.project.ems.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authorities")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService;

    @GetMapping
    public String findAllPage(Model model) {
        model.addAttribute("authorities", authorityService.convertToEntities(authorityService.findAll()));
        return "authority/authorities";
    }

    @GetMapping("/{id}")
    public String findByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("authority", authorityService.findEntityById(id));
        return "authority/authority-details";
    }

    @GetMapping("/save")
    public String getSavePage(Model model) {
        model.addAttribute("authorityDto", new AuthorityDto());
        return "authority/save-authority";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute AuthorityDto authorityDto) {
        authorityService.save(authorityDto);
        return "redirect:/authorities";
    }
}
