package com.project.ems.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public String findAllActivePage(Model model) {
        model.addAttribute("employees", employeeService.convertToEntities(employeeService.findAllActive()));
        return "employee/employees";
    }

    @GetMapping("/{id}")
    public String findByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("employee", employeeService.findEntityById(id));
        return "employee/employee-details";
    }

    @GetMapping("/save/{id}")
    public String getSavePage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute("employeeDto", id == -1 ? new EmployeeDto() : employeeService.findById(id));
        return "employee/save-employee";
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute EmployeeDto employeeDto, @PathVariable Integer id) {
        if (id == -1) {
            employeeService.save(employeeDto);
        } else {
            employeeService.updateById(employeeDto, id);
        }
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String disableById(@PathVariable Integer id) {
        employeeService.disableById(id);
        return "redirect:/employees";
    }
}
