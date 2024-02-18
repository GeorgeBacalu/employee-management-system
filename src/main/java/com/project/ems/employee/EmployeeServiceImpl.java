package com.project.ems.employee;

import com.project.ems.authority.Authority;
import com.project.ems.authority.AuthorityService;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.role.RoleService;
import com.project.ems.study.Study;
import com.project.ems.study.StudyService;
import com.project.ems.trainer.TrainerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.ems.constants.Constants.EMPLOYEE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleService roleService;
    private final AuthorityService authorityService;
    private final ExperienceService experienceService;
    private final StudyService studyService;
    private final TrainerService trainerService;
    private final ModelMapper modelMapper;

    @Override
    public List<EmployeeDto> findAll() {
        return convertToDtos(employeeRepository.findAll());
    }

    @Override
    public List<EmployeeDto> findAllActive() {
        return convertToDtos(employeeRepository.findAllByIsActiveTrue());
    }

    @Override
    public Page<EmployeeDto> findAllByKey(Pageable pageable, String key) {
        Page<Employee> employeesPage = key.trim().isEmpty() ? employeeRepository.findAll(pageable) : employeeRepository.findAllByKey(pageable, key.toLowerCase());
        return employeesPage.hasContent() ? employeesPage.map(this::convertToDto) : Page.empty();
    }

    @Override
    public Page<EmployeeDto> findAllActiveByKey(Pageable pageable, String key) {
        Page<Employee> activeEmployeesPage = key.trim().isEmpty() ? employeeRepository.findAllByIsActiveTrue(pageable) : employeeRepository.findAllActiveByKey(pageable, key.toLowerCase());
        return activeEmployeesPage.hasContent() ? activeEmployeesPage.map(this::convertToDto) : Page.empty();
    }

    @Override
    public EmployeeDto findById(Integer id) {
        return convertToDto(findEntityById(id));
    }

    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {
        Employee employeeToSave = convertToEntity(employeeDto);
        employeeToSave.setIsActive(true);
        return convertToDto(employeeRepository.save(employeeToSave));
    }

    @Override
    public EmployeeDto updateById(EmployeeDto employeeDto, Integer id) {
        Employee employeeToUpdate = findEntityById(id);
        updateEntityFromDto(employeeToUpdate, employeeDto);
        return convertToDto(employeeRepository.save(employeeToUpdate));
    }

    @Override
    public EmployeeDto disableById(Integer id) {
        Employee employeeToDisable = findEntityById(id);
        employeeToDisable.setIsActive(false);
        return convertToDto(employeeRepository.save(employeeToDisable));
    }

    @Override
    public List<EmployeeDto> convertToDtos(List<Employee> employees) {
        return employees.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<Employee> convertToEntities(List<EmployeeDto> employeeDtos) {
        return employeeDtos.stream().map(this::convertToEntity).toList();
    }

    @Override
    public EmployeeDto convertToDto(Employee employee) {
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        employeeDto.setAuthoritiesIds(employee.getAuthorities().stream().map(Authority::getId).toList());
        employeeDto.setExperiencesIds(employee.getExperiences().stream().map(Experience::getId).toList());
        employeeDto.setStudiesIds(employee.getStudies().stream().map(Study::getId).toList());
        return employeeDto;
    }

    @Override
    public Employee convertToEntity(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        employee.setRole(roleService.findEntityById(employeeDto.getRoleId()));
        employee.setAuthorities(employeeDto.getAuthoritiesIds().stream().map(authorityService::findEntityById).toList());
        employee.setExperiences(employeeDto.getExperiencesIds().stream().map(experienceService::findEntityById).toList());
        employee.setStudies(employeeDto.getStudiesIds().stream().map(studyService::findEntityById).toList());
        employee.setTrainer(trainerService.findEntityById(employeeDto.getTrainerId()));
        return employee;
    }

    @Override
    public Employee findEntityById(Integer id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND, id)));
    }

    private void updateEntityFromDto(Employee employee, EmployeeDto employeeDto) {
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPassword(employeeDto.getPassword());
        employee.setMobile(employeeDto.getMobile());
        employee.setAddress(employeeDto.getAddress());
        employee.setBirthday(employeeDto.getBirthday());
        employee.setRole(roleService.findEntityById(employeeDto.getRoleId()));
        employee.setAuthorities(employeeDto.getAuthoritiesIds().stream().map(authorityService::findEntityById).collect(Collectors.toList()));
        employee.setEmploymentType(employeeDto.getEmploymentType());
        employee.setPosition(employeeDto.getPosition());
        employee.setGrade(employeeDto.getGrade());
        employee.setSalary(employeeDto.getSalary());
        employee.setHiredAt(employeeDto.getHiredAt());
        employee.setExperiences(employeeDto.getExperiencesIds().stream().map(experienceService::findEntityById).collect(Collectors.toList()));
        employee.setStudies(employeeDto.getStudiesIds().stream().map(studyService::findEntityById).collect(Collectors.toList()));
        employee.setTrainer(trainerService.findEntityById(employeeDto.getTrainerId()));
    }
}
