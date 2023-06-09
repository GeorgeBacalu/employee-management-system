package com.project.ems.user;

import com.project.ems.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_USERS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_USER_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.USERS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.USER_DETAILS_VIEW;
import static com.project.ems.mapper.UserMapper.convertToEntity;
import static com.project.ems.mapper.UserMapper.convertToEntityList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllUsersPage(Model model) {
        model.addAttribute("users", convertToEntityList(modelMapper, userService.findAll(), roleService));
        return USERS_VIEW;
    }

    @GetMapping("/{id}")
    public String getUserByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("user", convertToEntity(modelMapper, userService.findById(id), roleService));
        return USER_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSaveUserPage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute("userDto", id == -1 ? new UserDto() : userService.findById(id));
        return SAVE_USER_VIEW;
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute UserDto userDto, @PathVariable Integer id) {
        if(id == -1) {
            userService.save(userDto);
        } else {
            userService.updateById(userDto, id);
        }
        return REDIRECT_USERS_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        userService.deleteById(id);
        return REDIRECT_USERS_VIEW;
    }
}
