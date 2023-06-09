package com.project.ems.unit.employee;

import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeRestController;
import com.project.ems.employee.EmployeeService;
import com.project.ems.wrapper.PageWrapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
    private List<EmployeeDto> employeeDtosPage1;
    private List<EmployeeDto> employeeDtosPage2;
    private List<EmployeeDto> employeeDtosPage3;

    @BeforeEach
    void setUp() {
        employeeDto1 = convertToDto(modelMapper, getMockedEmployee1());
        employeeDto2 = convertToDto(modelMapper, getMockedEmployee2());
        employeeDtos = convertToDtoList(modelMapper, getMockedEmployees());
        employeeDtosPage1 = convertToDtoList(modelMapper, getMockedEmployeesPage1());
        employeeDtosPage2 = convertToDtoList(modelMapper, getMockedEmployeesPage2());
        employeeDtosPage3 = convertToDtoList(modelMapper, getMockedEmployeesPage3());
    }

    @Test
    void findAll_shouldReturnListOfEmployees() {
        given(employeeService.findAll()).willReturn(employeeDtos);
        ResponseEntity<List<EmployeeDto>> response = employeeRestController.findAll();
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(employeeDtos);
    }

    @Test
    void findById_shouldReturnEmployeeWithGivenId() {
        given(employeeService.findById(anyInt())).willReturn(employeeDto1);
        ResponseEntity<EmployeeDto> response = employeeRestController.findById(VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(employeeDto1);
    }

    @Test
    void save_shouldAddEmployeeToList() {
        given(employeeService.save(any(EmployeeDto.class))).willReturn(employeeDto1);
        ResponseEntity<EmployeeDto> response = employeeRestController.save(employeeDto1);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(employeeDto1);
    }

    @Test
    void updateById_shouldUpdateEmployeeWithGivenId() {
        EmployeeDto employeeDto = employeeDto2; employeeDto.setId(VALID_ID);
        given(employeeService.updateById(any(EmployeeDto.class), anyInt())).willReturn(employeeDto);
        ResponseEntity<EmployeeDto> response = employeeRestController.updateById(employeeDto2, VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(employeeDto);
    }

    @Test
    void deleteById_shouldRemoveEmployeeWithGivenIdFromList() {
        ResponseEntity<Void> response = employeeRestController.deleteById(VALID_ID);
        verify(employeeService).deleteById(VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfEmployeesFilteredByKeyPage1() {
        PageImpl<EmployeeDto> filteredEmployeeDtosPage = new PageImpl<>(employeeDtosPage1);
        given(employeeService.findAllByKey(pageable, EMPLOYEE_FILTER_KEY)).willReturn(filteredEmployeeDtosPage);
        ResponseEntity<PageWrapper<EmployeeDto>> response = employeeRestController.findAllByKey(pageable, EMPLOYEE_FILTER_KEY);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredEmployeeDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfEmployeesFilteredByKeyPage2() {
        PageImpl<EmployeeDto> filteredEmployeeDtosPage = new PageImpl<>(employeeDtosPage2);
        given(employeeService.findAllByKey(pageable2, EMPLOYEE_FILTER_KEY)).willReturn(filteredEmployeeDtosPage);
        ResponseEntity<PageWrapper<EmployeeDto>> response = employeeRestController.findAllByKey(pageable2, EMPLOYEE_FILTER_KEY);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredEmployeeDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfEmployeesFilteredByKeyPage3() {
        PageImpl<EmployeeDto> filteredEmployeeDtosPage = new PageImpl<>(Collections.emptyList());
        given(employeeService.findAllByKey(pageable3, EMPLOYEE_FILTER_KEY)).willReturn(filteredEmployeeDtosPage);
        ResponseEntity<PageWrapper<EmployeeDto>> response = employeeRestController.findAllByKey(pageable3, EMPLOYEE_FILTER_KEY);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredEmployeeDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfEmployeesPage1() {
        PageImpl<EmployeeDto> filteredEmployeeDtosPage = new PageImpl<>(employeeDtosPage1);
        given(employeeService.findAllByKey(pageable, "")).willReturn(filteredEmployeeDtosPage);
        ResponseEntity<PageWrapper<EmployeeDto>> response = employeeRestController.findAllByKey(pageable, "");
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredEmployeeDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfEmployeesPage2() {
        PageImpl<EmployeeDto> filteredEmployeeDtosPage = new PageImpl<>(employeeDtosPage2);
        given(employeeService.findAllByKey(pageable2, "")).willReturn(filteredEmployeeDtosPage);
        ResponseEntity<PageWrapper<EmployeeDto>> response = employeeRestController.findAllByKey(pageable2, "");
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredEmployeeDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfEmployeesPage3() {
        PageImpl<EmployeeDto> filteredEmployeeDtosPage = new PageImpl<>(employeeDtosPage3);
        given(employeeService.findAllByKey(pageable3, "")).willReturn(filteredEmployeeDtosPage);
        ResponseEntity<PageWrapper<EmployeeDto>> response = employeeRestController.findAllByKey(pageable3, "");
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredEmployeeDtosPage.getContent()));
    }
}
