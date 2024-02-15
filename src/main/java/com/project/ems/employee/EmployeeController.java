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

    @GetMapping("/save")
    public String getSavePage(Model model) {
        model.addAttribute("employeeDto", new EmployeeDto());
        return "employee/save-employee";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute EmployeeDto employeeDto) {
        employeeService.save(employeeDto);
        return "redirect:/employees";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(Model model, @PathVariable Integer id) {
        model.addAttribute("employeeDto", employeeService.findById(id));
        return "employee/update-employee";
    }

    @PostMapping("/update/{id}")
    public String updateById(@ModelAttribute EmployeeDto employeeDto, @PathVariable Integer id) {
        employeeService.updateById(employeeDto, id);
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String disableById(@PathVariable Integer id) {
        employeeService.disableById(id);
        return "redirect:/employees";
    }
}
