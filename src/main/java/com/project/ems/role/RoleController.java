package com.project.ems.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public String findAllPage(Model model) {
        model.addAttribute("roles", roleService.convertToEntities(roleService.findAll()));
        return "role/roles";
    }

    @GetMapping("/{id}")
    public String findByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("role", roleService.findEntityById(id));
        return "role/role-details";
    }

    @GetMapping("/save")
    public String getSavePage(Model model) {
        model.addAttribute("roleDto", new RoleDto());
        return "role/save-role";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute RoleDto roleDto) {
        roleService.save(roleDto);
        return "redirect:/roles";
    }
}
