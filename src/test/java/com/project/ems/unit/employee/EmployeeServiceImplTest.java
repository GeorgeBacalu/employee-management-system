package com.project.ems.unit.employee;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeRepository;
import com.project.ems.employee.EmployeeServiceImpl;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorService;
import com.project.ems.role.Role;
import com.project.ems.role.RoleService;
import com.project.ems.study.Study;
import com.project.ems.study.StudyService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import static com.project.ems.constants.ExceptionMessageConstants.EMPLOYEE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.EMPLOYEE_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.PaginationConstants.pageable2;
import static com.project.ems.constants.PaginationConstants.pageable3;
import static com.project.ems.mapper.EmployeeMapper.convertToDto;
import static com.project.ems.mapper.EmployeeMapper.convertToDtoList;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployees;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeesPage1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeesPage2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeesPage3;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences2;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.StudyMock.getMockedStudies1;
import static com.project.ems.mock.StudyMock.getMockedStudies2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private MentorService mentorService;

    @Mock
    private StudyService studyService;

    @Mock
    private ExperienceService experienceService;

    @Spy
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Employee> employeeCaptor;

    private Employee employee1;
    private Employee employee2;
    private List<Employee> employees;
    private List<Employee> employeesPage1;
    private List<Employee> employeesPage2;
    private List<Employee> employeesPage3;
    private Role role1;
    private Role role2;
    private Mentor mentor1;
    private Mentor mentor2;
    private List<Study> studies1;
    private List<Study> studies2;
    private List<Experience> experiences1;
    private List<Experience> experiences2;
    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;
    private List<EmployeeDto> employeeDtos;
    private List<EmployeeDto> employeeDtosPage1;
    private List<EmployeeDto> employeeDtosPage2;
    private List<EmployeeDto> employeeDtosPage3;

    @BeforeEach
    void setUp() {
        employee1 = getMockedEmployee1();
        employee2 = getMockedEmployee2();
        employees = getMockedEmployees();
        employeesPage1 = getMockedEmployeesPage1();
        employeesPage2 = getMockedEmployeesPage2();
        employeesPage3 = getMockedEmployeesPage3();
        role1 = getMockedRole1();
        role2 = getMockedRole2();
        mentor1 = getMockedMentor1();
        mentor2 = getMockedMentor2();
        studies1 = getMockedStudies1();
        studies2 = getMockedStudies2();
        experiences1 = getMockedExperiences1();
        experiences2 = getMockedExperiences2();
        employeeDto1 = convertToDto(modelMapper, employee1);
        employeeDto2 = convertToDto(modelMapper, employee2);
        employeeDtos = convertToDtoList(modelMapper, employees);
        employeeDtosPage1 = convertToDtoList(modelMapper, employeesPage1);
        employeeDtosPage2 = convertToDtoList(modelMapper, employeesPage2);
        employeeDtosPage3 = convertToDtoList(modelMapper, employeesPage3);
    }

    @Test
    void findAll_shouldReturnListOfEmployees() {
        given(employeeRepository.findAll()).willReturn(employees);
        List<EmployeeDto> result = employeeService.findAll();
        assertThat(result).isEqualTo(employeeDtos);
    }

    @Test
    void findById_withValidId_shouldReturnEmployeeWithGivenId() {
        given(employeeRepository.findById(anyInt())).willReturn(Optional.ofNullable(employee1));
        EmployeeDto result = employeeService.findById(VALID_ID);
        assertThat(result).isEqualTo(employeeDto1);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> employeeService.findById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_shouldAddEmployeeToList() {
        given(roleService.findEntityById(anyInt())).willReturn(role1);
        given(mentorService.findEntityById(anyInt())).willReturn(mentor1);
        employeeDto1.getStudiesIds().forEach(id -> given(studyService.findEntityById(id)).willReturn(studies1.get(id - 1)));
        employeeDto1.getExperiencesIds().forEach(id -> given(experienceService.findEntityById(id)).willReturn(experiences1.get(id - 1)));
        given(employeeRepository.save(any(Employee.class))).willReturn(employee1);
        EmployeeDto result = employeeService.save(employeeDto1);
        verify(employeeRepository).save(employeeCaptor.capture());
        assertThat(result).isEqualTo(convertToDto(modelMapper, employeeCaptor.getValue()));
    }

    @Test
    void updateById_withValidId_shouldUpdateEmployeeWithGivenId() {
        Employee employee = employee2; employee.setId(VALID_ID);
        given(employeeRepository.findById(anyInt())).willReturn(Optional.ofNullable(employee1));
        given(roleService.findEntityById(anyInt())).willReturn(role2);
        given(mentorService.findEntityById(anyInt())).willReturn(mentor2);
        employeeDto2.getStudiesIds().forEach(id -> given(studyService.findEntityById(id)).willReturn(studies2.get(id - 3)));
        employeeDto2.getExperiencesIds().forEach(id -> given(experienceService.findEntityById(id)).willReturn(experiences2.get(id - 3)));
        given(employeeRepository.save(any(Employee.class))).willReturn(employee);
        EmployeeDto result = employeeService.updateById(employeeDto2, VALID_ID);
        verify(employeeRepository).save(employeeCaptor.capture());
        assertThat(result).isEqualTo(convertToDto(modelMapper, employee));
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> employeeService.updateById(employeeDto2, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID));
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void deleteById_withValidId_shouldRemoveEmployeeWithGivenIdFromList() {
        given(employeeRepository.findById(anyInt())).willReturn(Optional.ofNullable(employee1));
        employeeService.deleteById(VALID_ID);
        verify(employeeRepository).delete(employee1);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> employeeService.deleteById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID));
        verify(employeeRepository, never()).delete(any(Employee.class));
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfEmployeesFilteredByKeyPage1() {
        given(employeeRepository.findAllByKey(pageable, EMPLOYEE_FILTER_KEY)).willReturn(new PageImpl<>(employeesPage1));
        Page<EmployeeDto> result = employeeService.findAllByKey(pageable, EMPLOYEE_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(employeeDtosPage1);
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfEmployeesFilteredByKeyPage2() {
        given(employeeRepository.findAllByKey(pageable2, EMPLOYEE_FILTER_KEY)).willReturn(new PageImpl<>(employeesPage2));
        Page<EmployeeDto> result = employeeService.findAllByKey(pageable2, EMPLOYEE_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(employeeDtosPage2);
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfEmployeesFilteredByKeyPage3() {
        given(employeeRepository.findAllByKey(pageable3, EMPLOYEE_FILTER_KEY)).willReturn(new PageImpl<>(Collections.emptyList()));
        Page<EmployeeDto> result = employeeService.findAllByKey(pageable3, EMPLOYEE_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(Collections.emptyList());
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfEmployeesPage1() {
        given(employeeRepository.findAll(pageable)).willReturn(new PageImpl<>(employeesPage1));
        Page<EmployeeDto> result = employeeService.findAllByKey(pageable, "");
        assertThat(result.getContent()).isEqualTo(employeeDtosPage1);
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfEmployeesPage2() {
        given(employeeRepository.findAll(pageable2)).willReturn(new PageImpl<>(employeesPage2));
        Page<EmployeeDto> result = employeeService.findAllByKey(pageable2, "");
        assertThat(result.getContent()).isEqualTo(employeeDtosPage2);
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfEmployeesPage3() {
        given(employeeRepository.findAll(pageable3)).willReturn(new PageImpl<>(employeesPage3));
        Page<EmployeeDto> result = employeeService.findAllByKey(pageable3, "");
        assertThat(result.getContent()).isEqualTo(employeeDtosPage3);
    }
}
