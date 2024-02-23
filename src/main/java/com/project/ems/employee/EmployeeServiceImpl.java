package com.project.ems.employee;

import com.project.ems.authority.AuthorityService;
import com.project.ems.base.BaseUserServiceImpl;
import com.project.ems.experience.ExperienceService;
import com.project.ems.role.RoleService;
import com.project.ems.study.StudyService;
import com.project.ems.trainer.Trainer;
import com.project.ems.trainer.TrainerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends BaseUserServiceImpl<Employee, EmployeeDto, EmployeeRepository> implements EmployeeService {

    private final TrainerService trainerService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, RoleService roleService, AuthorityService authorityService, ExperienceService experienceService, StudyService studyService, TrainerService trainerService, ModelMapper modelMapper) {
        super(employeeRepository, roleService, authorityService, experienceService, studyService, modelMapper);
        this.trainerService = trainerService;
    }

    @Override
    public EmployeeDto convertToDto(Employee employee) {
        EmployeeDto employeeDto = super.convertToDto(employee);
        Trainer trainer = employee.getTrainer();
        employeeDto.setTrainerId(trainer != null ? trainer.getId() : null);
        return employeeDto;
    }

    @Override
    public Employee convertToEntity(EmployeeDto employeeDto) {
        Employee employee = super.convertToEntity(employeeDto);
        Integer trainerId = employeeDto.getTrainerId();
        employee.setTrainer(trainerId != null ? trainerService.findEntityById(trainerId) : null);
        return employee;
    }

    @Override
    public void updateEntityFromDto(Employee employee, EmployeeDto employeeDto) {
        super.updateEntityFromDto(employee, employeeDto);
        Integer trainerId = employeeDto.getTrainerId();
        employee.setTrainer(trainerId != null ? trainerService.findEntityById(trainerId) : null);
    }
}
