package com.project.ems.user;

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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String findAllActivePage(Model model, @PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        Page<UserDto> userDtosPage = userService.findAllActiveByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrUsers = userDtosPage.getTotalElements();
        int nrPages = userDtosPage.getTotalPages();
        model.addAttribute(USERS_ATTRIBUTE, userService.convertToEntities(userDtosPage.getContent()));
        model.addAttribute("nrUsers", nrUsers);
        model.addAttribute("nrPages", nrPages);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("field", field);
        model.addAttribute("direction", direction);
        model.addAttribute("key", key);
        model.addAttribute("pageStartIndex", getPageStartIndex(page, size));
        model.addAttribute("pageEndIndex", getPageEndIndex(page, size, nrUsers));
        model.addAttribute("pageNavigationStartIndex", getPageNavigationStartIndex(page, nrPages));
        model.addAttribute("pageNavigationEndIndex", getPageNavigationEndIndex(page, nrPages));
        model.addAttribute("searchRequest", new SearchRequest(page, size, field + "," + direction, key));
        return USERS_VIEW;
    }

    @PostMapping("/search")
    public String findAllByKey(@ModelAttribute SearchRequest searchRequest, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("page", searchRequest.getPage());
        redirectAttributes.addAttribute("size", searchRequest.getSize());
        redirectAttributes.addAttribute("sort", searchRequest.getSort());
        redirectAttributes.addAttribute("key", searchRequest.getKey());
        return REDIRECT_USERS_VIEW;
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
    public String disableById(@PathVariable Integer id, RedirectAttributes redirectAttributes, @PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        userService.disableById(id);
        Page<UserDto> userDtosPage = userService.findAllActiveByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        redirectAttributes.addAttribute("page", Math.max(0, page - (userDtosPage.hasContent() ? 1 : 0)));
        redirectAttributes.addAttribute("size", size);
        redirectAttributes.addAttribute("sort", field + "," + direction);
        redirectAttributes.addAttribute("key", key);
        return REDIRECT_USERS_VIEW;
    }
}
