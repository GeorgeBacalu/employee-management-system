package com.project.ems.unit.employee;

import com.project.ems.employee.*;
import com.project.ems.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;

import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.EmployeeMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @Spy
    private Model model;

    @Spy
    private ModelMapper modelMapper;

    private Employee employee;
    private List<Employee> activeEmployees;
    private EmployeeDto employeeDto;
    private List<EmployeeDto> activeEmployeeDtos;

    @BeforeEach
    void setUp() {
        employee = getMockedEmployee1();
        activeEmployees = getMockedActiveEmployees();
        employeeDto = getMockedEmployeeDto1();
        activeEmployeeDtos = getMockedActiveEmployeeDtos();
    }

    @Test
    void findAllActivePage_test() {
        given(employeeService.findAllActive()).willReturn(activeEmployeeDtos);
        given(employeeService.convertToEntities(activeEmployeeDtos)).willReturn(activeEmployees);
        given(model.getAttribute(EMPLOYEES_ATTRIBUTE)).willReturn(activeEmployees);
        String viewName = employeeController.findAllActivePage(model);
        then(viewName).isEqualTo(EMPLOYEES_VIEW);
        then(model.getAttribute(EMPLOYEES_ATTRIBUTE)).isEqualTo(activeEmployees);
    }

    @Test
    void findByIdPage_validId_test() {
        given(employeeService.findEntityById(VALID_ID)).willReturn(employee);
        given(model.getAttribute(EMPLOYEE_ATTRIBUTE)).willReturn(employee);
        String viewName = employeeController.findByIdPage(model, VALID_ID);
        then(viewName).isEqualTo(EMPLOYEE_DETAILS_VIEW);
        then(model.getAttribute(EMPLOYEE_ATTRIBUTE)).isEqualTo(employee);
    }

    @Test
    void findByIdPage_invalidId_test() {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        given(employeeService.findEntityById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> employeeController.findByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void getSavePage_negativeId_test() {
        given(model.getAttribute("id")).willReturn(-1);
        given(model.getAttribute(EMPLOYEE_DTO_ATTRIBUTE)).willReturn(new EmployeeDto());
        String viewName = employeeController.getSavePage(model, -1);
        then(viewName).isEqualTo(SAVE_EMPLOYEE_VIEW);
        then(model.getAttribute("id")).isEqualTo(-1);
        then(model.getAttribute(EMPLOYEE_DTO_ATTRIBUTE)).isEqualTo(new EmployeeDto());
    }

    @Test
    void getSavePage_validId_test() {
        given(employeeService.findById(VALID_ID)).willReturn(employeeDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute(EMPLOYEE_DTO_ATTRIBUTE)).willReturn(employeeDto);
        String viewName = employeeController.getSavePage(model, VALID_ID);
        then(viewName).isEqualTo(SAVE_EMPLOYEE_VIEW);
        then(model.getAttribute("id")).isEqualTo(VALID_ID);
        then(model.getAttribute(EMPLOYEE_DTO_ATTRIBUTE)).isEqualTo(employeeDto);
    }

    @Test
    void getSavePage_invalidId_test() {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        given(employeeService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> employeeController.getSavePage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void save_negativeId_test() {
        String viewName = employeeController.save(employeeDto, -1);
        then(viewName).isEqualTo(REDIRECT_EMPLOYEES_VIEW);
        verify(employeeService).save(employeeDto);
    }

    @Test
    void save_validId_test() {
        String viewName = employeeController.save(employeeDto, VALID_ID);
        then(viewName).isEqualTo(REDIRECT_EMPLOYEES_VIEW);
        verify(employeeService).updateById(employeeDto, VALID_ID);
    }

    @Test
    void save_invalidId_test() {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        given(employeeService.updateById(employeeDto, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> employeeController.save(employeeDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void disableById_validId_test() {
        String viewName = employeeController.disableById(VALID_ID);
        then(viewName).isEqualTo(REDIRECT_EMPLOYEES_VIEW);
    }

    @Test
    void disableById_invalidId_test() {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(employeeService).disableById(INVALID_ID);
        thenThrownBy(() -> employeeController.disableById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
