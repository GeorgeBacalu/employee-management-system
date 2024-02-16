package com.project.ems.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.project.ems.constants.Constants.*;

@Controller
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public String findAllPage(Model model) {
        model.addAttribute(ROLES_ATTRIBUTE, roleService.convertToEntities(roleService.findAll()));
        return ROLES_VIEW;
    }

    @GetMapping("/{id}")
    public String findByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute(ROLE_ATTRIBUTE, roleService.findEntityById(id));
        return ROLE_DETAILS_VIEW;
    }

    @GetMapping("/save")
    public String getSavePage(Model model) {
        model.addAttribute(ROLE_DTO_ATTRIBUTE, new RoleDto());
        return SAVE_ROLE_VIEW;
    }

    @PostMapping("/save")
    public String save(@ModelAttribute RoleDto roleDto) {
        roleService.save(roleDto);
        return REDIRECT_ROLES_VIEW;
    }
}
