package com.project.ems.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String findAllActivePage(Model model) {
        model.addAttribute("users", userService.convertToEntities(userService.findAllActive()));
        return "user/users";
    }

    @GetMapping("/{id}")
    public String findByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("user", userService.findEntityById(id));
        return "user/user-details";
    }

    @GetMapping("/save/{id}")
    public String getSavePage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute("userDto", id == -1 ? new UserDto() : userService.findById(id));
        return "user/save-user";
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute UserDto userDto, @PathVariable Integer id) {
        if (id == -1) {
            userService.save(userDto);
        } else {
            userService.updateById(userDto, id);
        }
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String disableById(@PathVariable Integer id) {
        userService.disableById(id);
        return "redirect:/users";
    }
}
