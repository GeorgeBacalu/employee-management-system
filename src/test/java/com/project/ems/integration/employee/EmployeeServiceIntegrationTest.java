package com.project.ems.integration.employee;

import com.project.ems.authority.Authority;
import com.project.ems.authority.AuthorityService;
import com.project.ems.employee.*;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.role.Role;
import com.project.ems.role.RoleService;
import com.project.ems.study.Study;
import com.project.ems.study.StudyService;
import com.project.ems.trainer.Trainer;
import com.project.ems.trainer.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;
import java.util.Optional;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorities1;
import static com.project.ems.mock.EmployeeMock.*;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.StudyMock.getMockedStudies1;
import static com.project.ems.mock.TrainerMock.getMockedTrainer1;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private RoleService roleService;

    @MockBean
    private AuthorityService authorityService;

    @MockBean
    private ExperienceService experienceService;

    @MockBean
    private StudyService studyService;

    @MockBean
    private TrainerService trainerService;

    @SpyBean
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Employee> employeeCaptor;

    private Employee employee1;
    private Employee employee2;
    private List<Employee> employees;
    private List<Employee> activeEmployees;
    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;
    private List<EmployeeDto> employeeDtos;
    private List<EmployeeDto> activeEmployeeDtos;
    private Role role;
    private List<Authority> authorities;
    private List<Experience> experiences;
    private List<Study> studies;
    private Trainer trainer;

    @BeforeEach
    void setUp() {
        employee1 = getMockedEmployee1();
        employee2 = getMockedEmployee2();
        employees = getMockedEmployees();
        activeEmployees = getMockedActiveEmployees();
        employeeDto1 = getMockedEmployeeDto1();
        employeeDto2 = getMockedEmployeeDto2();
        employeeDtos = getMockedEmployeeDtos();
        activeEmployeeDtos = getMockedActiveEmployeeDtos();
        role = getMockedRole1();
        authorities = getMockedAuthorities1();
        experiences = getMockedExperiences1();
        studies = getMockedStudies1();
        trainer = getMockedTrainer1();
    }

    @Test
    void findAll_test() {
        given(employeeRepository.findAll()).willReturn(employees);
        List<EmployeeDto> result = employeeService.findAll();
        then(result).isEqualTo(employeeDtos);
    }

    @Test
    void findAllActive_test() {
        given(employeeRepository.findAllByIsActiveTrue()).willReturn(activeEmployees);
        List<EmployeeDto> result = employeeService.findAllActive();
        then(result).isEqualTo(activeEmployeeDtos);
    }

    @Test
    void findById_validId_test() {
        given(employeeRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(employee1));
        EmployeeDto result = employeeService.findById(VALID_ID);
        then(result).isEqualTo(employeeDto1);
    }

    @Test
    void findById_invalidId_test() {
        thenThrownBy(() -> employeeService.findById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_test() {
        employeeDto1.getAuthoritiesIds().forEach(id -> given(authorityService.findEntityById(id)).willReturn(authorities.get(id - 1)));
        employeeDto1.getExperiencesIds().forEach(id -> given(experienceService.findEntityById(id)).willReturn(experiences.get(id - 1)));
        employeeDto1.getStudiesIds().forEach(id -> given(studyService.findEntityById(id)).willReturn(studies.get(id - 1)));
        given(roleService.findEntityById(VALID_ID)).willReturn(role);
        given(trainerService.findEntityById(VALID_ID)).willReturn(trainer);
        given(employeeRepository.save(any(Employee.class))).willReturn(employee1);
        EmployeeDto result = employeeService.save(employeeDto1);
        verify(employeeRepository).save(employeeCaptor.capture());
        then(result).isEqualTo(employeeService.convertToDto(employeeCaptor.getValue()));
    }

    @Test
    void updateById_validId_test() {
        Employee updatedEmployee = employee2;
        updatedEmployee.setId(VALID_ID);
        given(employeeRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(employee1));
        given(employeeRepository.save(any(Employee.class))).willReturn(updatedEmployee);
        EmployeeDto result = employeeService.updateById(employeeDto2, VALID_ID);
        verify(employeeRepository).save(employeeCaptor.capture());
        then(result).isEqualTo(employeeService.convertToDto(updatedEmployee));
    }

    @Test
    void updateById_invalidId_test() {
        thenThrownBy(() -> employeeService.updateById(employeeDto2, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID));
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void disableById_validId_test() {
        Employee disabledEmployee = employee1;
        disabledEmployee.setIsActive(false);
        given(employeeRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(employee1));
        given(employeeRepository.save(any(Employee.class))).willReturn(disabledEmployee);
        EmployeeDto result = employeeService.disableById(VALID_ID);
        verify(employeeRepository).save(employeeCaptor.capture());
        then(result).isEqualTo(employeeService.convertToDto(disabledEmployee));
    }

    @Test
    void disableById_invalidId_test() {
        thenThrownBy(() -> employeeService.disableById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID));
        verify(employeeRepository, never()).save(any(Employee.class));
    }
}
