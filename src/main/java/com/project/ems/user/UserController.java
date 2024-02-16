package com.project.ems.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.project.ems.constants.Constants.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String findAllActivePage(Model model) {
        model.addAttribute(USERS_ATTRIBUTE, userService.convertToEntities(userService.findAllActive()));
        return USERS_VIEW;
    }

    @GetMapping("/{id}")
    public String findByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute(USER_ATTRIBUTE, userService.findEntityById(id));
        return USER_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSavePage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute(USER_DTO_ATTRIBUTE, id == -1 ? new UserDto() : userService.findById(id));
        return SAVE_USER_VIEW;
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute UserDto userDto, @PathVariable Integer id) {
        if (id == -1) {
            userService.save(userDto);
        } else {
            userService.updateById(userDto, id);
        }
        return REDIRECT_USERS_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String disableById(@PathVariable Integer id) {
        userService.disableById(id);
        return REDIRECT_USERS_VIEW;
    }
}
