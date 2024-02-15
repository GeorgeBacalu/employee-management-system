package com.project.ems.unit.employee;

import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeRestController;
import com.project.ems.employee.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.EmployeeMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EmployeeRestControllerTest {

    @InjectMocks
    private EmployeeRestController employeeRestController;

    @Mock
    private EmployeeService employeeService;

    @Spy
    private ModelMapper modelMapper;

    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;
    private List<EmployeeDto> employeeDtos;

    @BeforeEach
    void setUp() {
        employeeDto1 = getMockedEmployeeDto1();
        employeeDto2 = getMockedEmployeeDto2();
        employeeDtos = getMockedEmployeeDtos();
    }

    @Test
    void findAll_test() {
        given(employeeService.findAll()).willReturn(employeeDtos);
        ResponseEntity<List<EmployeeDto>> response = employeeRestController.findAll();
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(employeeDtos);
    }

    @Test
    void findById_test() {
        given(employeeService.findById(VALID_ID)).willReturn(employeeDto1);
        ResponseEntity<EmployeeDto> response = employeeRestController.findById(VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(employeeDto1);
    }

    @Test
    void save_test() {
        given(employeeService.save(employeeDto1)).willReturn(employeeDto1);
        ResponseEntity<EmployeeDto> response = employeeRestController.save(employeeDto1);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        then(response.getBody()).isEqualTo(employeeDto1);
    }

    @Test
    void updateById_test() {
        given(employeeService.updateById(employeeDto2, VALID_ID)).willReturn(employeeDto2);
        ResponseEntity<EmployeeDto> response = employeeRestController.updateById(employeeDto2, VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(employeeDto2);
    }

    @Test
    void disableById_test() {
        given(employeeService.disableById(VALID_ID)).willReturn(employeeDto1);
        ResponseEntity<EmployeeDto> response = employeeRestController.disableById(VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(employeeDto1);
    }
}
