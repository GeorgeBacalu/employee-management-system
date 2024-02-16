package com.project.ems.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.project.ems.constants.Constants.*;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public String findAllActivePage(Model model) {
        model.addAttribute(EMPLOYEES_ATTRIBUTE, employeeService.convertToEntities(employeeService.findAllActive()));
        return EMPLOYEES_VIEW;
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
    public String disableById(@PathVariable Integer id) {
        employeeService.disableById(id);
        return REDIRECT_EMPLOYEES_VIEW;
    }
}
