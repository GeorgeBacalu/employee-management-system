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

    @GetMapping("/save")
    public String getSavePage(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "user/save-user";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute UserDto userDto) {
        userService.save(userDto);
        return "redirect:/users";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(Model model, @PathVariable Integer id) {
        model.addAttribute("userDto", userService.findById(id));
        return "user/update-user";
    }

    @PostMapping("/update/{id}")
    public String updateById(@ModelAttribute UserDto userDto, @PathVariable Integer id) {
        userService.updateById(userDto, id);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String disableById(@PathVariable Integer id) {
        userService.disableById(id);
        return "redirect:/users";
    }
}
