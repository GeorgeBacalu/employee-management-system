package com.project.ems.employee;

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
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public String findAllActivePage(Model model, @PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        Page<EmployeeDto> employeeDtosPage = employeeService.findAllActiveByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrEmployees = employeeDtosPage.getTotalElements();
        int nrPages = employeeDtosPage.getTotalPages();
        model.addAttribute(EMPLOYEES_ATTRIBUTE, employeeService.convertToEntities(employeeDtosPage.getContent()));
        model.addAttribute("nrEmployees", nrEmployees);
        model.addAttribute("nrPages", nrPages);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("field", field);
        model.addAttribute("direction", direction);
        model.addAttribute("key", key);
        model.addAttribute("pageStartIndex", getPageStartIndex(page, size));
        model.addAttribute("pageEndIndex", getPageEndIndex(page, size, nrEmployees));
        model.addAttribute("pageNavigationStartIndex", getPageNavigationStartIndex(page, nrPages));
        model.addAttribute("pageNavigationEndIndex", getPageNavigationEndIndex(page, nrPages));
        model.addAttribute("searchRequest", new SearchRequest(page, size, field + "," + direction, key));
        return EMPLOYEES_VIEW;
    }

    @PostMapping("/search")
    public String findAllActiveByKey(@ModelAttribute SearchRequest searchRequest, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("page", searchRequest.getPage());
        redirectAttributes.addAttribute("size", searchRequest.getSize());
        redirectAttributes.addAttribute("sort", searchRequest.getSort());
        redirectAttributes.addAttribute("key", searchRequest.getKey());
        return REDIRECT_EMPLOYEES_VIEW;
    }

    @GetMapping("/{id}")
    public String findByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute(EMPLOYEE_ATTRIBUTE, employeeService.findEntityById(id));
        return EMPLOYEE_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSavePage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute(EMPLOYEE_DTO_ATTRIBUTE, id == -1 ? new EmployeeDto() : employeeService.findById(id));
        return SAVE_EMPLOYEE_VIEW;
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute EmployeeDto employeeDto, @PathVariable Integer id) {
        if (id == -1) {
            employeeService.save(employeeDto);
        } else {
            employeeService.updateById(employeeDto, id);
        }
        return REDIRECT_EMPLOYEES_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String disableById(@PathVariable Integer id, RedirectAttributes redirectAttributes, @PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        employeeService.disableById(id);
        Page<EmployeeDto> employeeDtosPage = employeeService.findAllActiveByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        redirectAttributes.addAttribute("page", Math.max(0, page - (employeeDtosPage.hasContent() ? 1 : 0)));
        redirectAttributes.addAttribute("size", size);
        redirectAttributes.addAttribute("sort", field + "," + direction);
        redirectAttributes.addAttribute("key", key);
        return REDIRECT_EMPLOYEES_VIEW;
    }
}
