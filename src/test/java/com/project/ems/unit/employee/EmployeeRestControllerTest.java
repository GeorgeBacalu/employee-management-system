package com.project.ems.unit.employee;

import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeRestController;
import com.project.ems.employee.EmployeeService;
import com.project.ems.exception.InvalidRequestException;
import com.project.ems.wrapper.PageWrapper;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.EmployeeMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    private List<EmployeeDto> activeEmployeeDtos;
    private List<EmployeeDto> employeeDtosPage1;
    private List<EmployeeDto> employeeDtosPage2;
    private List<EmployeeDto> employeeDtosPage3;

    @BeforeEach
    void setUp() {
        employeeDto1 = getMockedEmployeeDto1();
        employeeDto2 = getMockedEmployeeDto2();
        employeeDtos = getMockedEmployeeDtos();
        activeEmployeeDtos = getMockedActiveEmployeeDtos();
        employeeDtosPage1 = getMockedEmployeeDtosPage1();
        employeeDtosPage2 = getMockedEmployeeDtosPage2();
        employeeDtosPage3 = getMockedEmployeeDtosPage3();
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
    void findAllActive_test() {
        given(employeeService.findAllActive()).willReturn(activeEmployeeDtos);
        ResponseEntity<List<EmployeeDto>> response = employeeRestController.findAllActive();
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(activeEmployeeDtos);
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

    @ParameterizedTest
    @CsvSource({"0, ${EMPLOYEE_FILTER_KEY}", "1, ${EMPLOYEE_FILTER_KEY}", "2, ${EMPLOYEE_FILTER_KEY}", "0, ''", "1, ''", "2, ''"})
    void findAllByKey_test(int page, String key) {
        Pair<Pageable, List<EmployeeDto>> pageableEmployeeDtosPair = switch (page) {
            case 0 -> Pair.of(PAGEABLE_PAGE1, employeeDtosPage1);
            case 1 -> Pair.of(PAGEABLE_PAGE2, employeeDtosPage2);
            case 2 -> Pair.of(PAGEABLE_PAGE3, key.trim().isEmpty() ? employeeDtosPage3 : Collections.emptyList());
            default -> throw new InvalidRequestException(INVALID_PAGE_NUMBER + page);
        };
        Page<EmployeeDto> filteredEmployeeDtosPage = new PageImpl<>(pageableEmployeeDtosPair.getRight());
        given(employeeService.findAllByKey(any(Pageable.class), eq(key))).willReturn(filteredEmployeeDtosPage);
        ResponseEntity<PageWrapper<EmployeeDto>> response = employeeRestController.findAllByKey(pageableEmployeeDtosPair.getLeft(), key);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(filteredEmployeeDtosPage);
    }

    @ParameterizedTest
    @CsvSource({"0, ${EMPLOYEE_FILTER_KEY}", "1, ${EMPLOYEE_FILTER_KEY}", "0, ''", "1, ''"})
    void findAllActiveByKey_test(int page, String key) {
        Pair<Pageable, List<EmployeeDto>> pageableActiveEmployeeDtosPair = switch (page) {
            case 0 -> Pair.of(PAGEABLE_PAGE1, employeeDtosPage1);
            case 1 -> Pair.of(PAGEABLE_PAGE3, key.trim().isEmpty() ? employeeDtosPage2 : Collections.emptyList());
            default -> throw new InvalidRequestException(INVALID_PAGE_NUMBER + page);
        };
        Page<EmployeeDto> filteredActiveEmployeeDtosPage = new PageImpl<>(pageableActiveEmployeeDtosPair.getRight());
        given(employeeService.findAllActiveByKey(any(Pageable.class), eq(key))).willReturn(filteredActiveEmployeeDtosPage);
        ResponseEntity<PageWrapper<EmployeeDto>> response = employeeRestController.findAllActiveByKey(pageableActiveEmployeeDtosPair.getLeft(), key);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(filteredActiveEmployeeDtosPage);
    }
}
