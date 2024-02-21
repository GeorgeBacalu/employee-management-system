package com.project.ems.integration.employee;

import com.project.ems.employee.*;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.wrapper.SearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.EmployeeMock.*;
import static com.project.ems.util.PageUtil.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private Employee employee;
    private List<Employee> activeEmployeesPage1;
    private EmployeeDto employeeDto;
    private List<EmployeeDto> activeEmployeeDtosPage1;

    @BeforeEach
    void setUp() {
        employee = getMockedEmployee1();
        activeEmployeesPage1 = getMockedEmployeesPage1();
        employeeDto = getMockedEmployeeDto1();
        activeEmployeeDtosPage1 = getMockedEmployeeDtosPage1();
    }

    @Test
    void findAllActivePage_test() throws Exception {
        Page<EmployeeDto> employeeDtosPage = new PageImpl<>(activeEmployeeDtosPage1);
        given(employeeService.findAllActiveByKey(PAGEABLE, EMPLOYEE_FILTER_KEY)).willReturn(employeeDtosPage);
        given(employeeService.convertToEntities(employeeDtosPage.getContent())).willReturn(activeEmployeesPage1);
        int page = PAGEABLE.getPageNumber();
        int size = PAGEABLE.getPageSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        long nrEmployees = employeeDtosPage.getTotalElements();
        int nrPages = employeeDtosPage.getTotalPages();
        mockMvc.perform(get(EMPLOYEES + PAGINATION, page, size, field, direction, EMPLOYEE_FILTER_KEY).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(EMPLOYEES_VIEW))
              .andExpect(model().attribute(EMPLOYEES_ATTRIBUTE, activeEmployeesPage1))
              .andExpect(model().attribute("nrEmployees", nrEmployees))
              .andExpect(model().attribute("nrPages", nrPages))
              .andExpect(model().attribute("page", page))
              .andExpect(model().attribute("size", size))
              .andExpect(model().attribute("field", field))
              .andExpect(model().attribute("direction", direction))
              .andExpect(model().attribute("key", EMPLOYEE_FILTER_KEY))
              .andExpect(model().attribute("pageStartIndex", getPageStartIndex(page, size)))
              .andExpect(model().attribute("pageEndIndex", getPageEndIndex(page, size, nrEmployees)))
              .andExpect(model().attribute("pageNavigationStartIndex", getPageNavigationStartIndex(page, nrPages)))
              .andExpect(model().attribute("pageNavigationEndIndex", getPageNavigationEndIndex(page, nrPages)))
              .andExpect(model().attribute("searchRequest", new SearchRequest(page, size, field + ',' + direction, EMPLOYEE_FILTER_KEY)));
    }

    @Test
    void findAllActiveByKey_test() throws Exception {
        Page<EmployeeDto> employeeDtosPage = new PageImpl<>(activeEmployeeDtosPage1);
        int page = employeeDtosPage.getNumber();
        int size = employeeDtosPage.getSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        mockMvc.perform(post(EMPLOYEES + "/search" + PAGINATION, page, size, field, direction, EMPLOYEE_FILTER_KEY).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EMPLOYEES_VIEW))
              .andExpect(redirectedUrlPattern(EMPLOYEES + "?page=*&size=*&sort=*&key=*"));
    }

    @Test
    void findByIdPage_validId_test() throws Exception {
        given(employeeService.findEntityById(VALID_ID)).willReturn(employee);
        mockMvc.perform(get(EMPLOYEES + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(EMPLOYEE_DETAILS_VIEW))
              .andExpect(model().attribute(EMPLOYEE_ATTRIBUTE, employee));
    }

    @Test
    void findByIdPage_invalidId_test() throws Exception {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        given(employeeService.findEntityById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(EMPLOYEES + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void getSavePage_negativeId_test() throws Exception {
        mockMvc.perform(get(EMPLOYEES + "/save/{id}", -1).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_EMPLOYEE_VIEW))
              .andExpect(model().attribute("id", -1))
              .andExpect(model().attribute(EMPLOYEE_DTO_ATTRIBUTE, new EmployeeDto()));
    }

    @Test
    void getSavePage_validId_test() throws Exception {
        given(employeeService.findById(VALID_ID)).willReturn(employeeDto);
        mockMvc.perform(get(EMPLOYEES + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_EMPLOYEE_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute(EMPLOYEE_DTO_ATTRIBUTE, employeeDto));
    }

    @Test
    void getSavePage_invalidId_test() throws Exception {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        given(employeeService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(EMPLOYEES + "/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_negativeId_test() throws Exception {
        mockMvc.perform(post(EMPLOYEES + "/save/{id}", -1).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(employeeDto)))
              .andExpect(status().isFound())
              .andExpect(redirectedUrl(EMPLOYEES));
        verify(employeeService).save(any(EmployeeDto.class));
    }

    @Test
    void save_validId_test() throws Exception {
        mockMvc.perform(post(EMPLOYEES + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(employeeDto)))
              .andExpect(status().isFound())
              .andExpect(redirectedUrl(EMPLOYEES));
        verify(employeeService).updateById(any(EmployeeDto.class), anyInt());
    }

    @Test
    void save_invalidId_test() throws Exception {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        given(employeeService.updateById(any(EmployeeDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(EMPLOYEES + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(employeeDto)))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void disableById_validId_test() throws Exception {
        Page<EmployeeDto> employeeDtosPage = new PageImpl<>(activeEmployeeDtosPage1);
        given(employeeService.findAllActiveByKey(PAGEABLE, EMPLOYEE_FILTER_KEY)).willReturn(employeeDtosPage);
        int page = employeeDtosPage.getNumber();
        int size = employeeDtosPage.getSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        mockMvc.perform(get(EMPLOYEES + "/delete/{id}" + PAGINATION, VALID_ID, page, size, field, direction, EMPLOYEE_FILTER_KEY).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EMPLOYEES_VIEW))
              .andExpect(redirectedUrlPattern(EMPLOYEES + "?page=*&size=*&sort=*&key=*"));
    }

    @Test
    void disableById_invalidId_test() throws Exception {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(employeeService).disableById(INVALID_ID);
        mockMvc.perform(get(EMPLOYEES + "/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    private MultiValueMap<String, String> convertToMultiValueMap(EmployeeDto employeeDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", employeeDto.getName());
        params.add("email", employeeDto.getEmail());
        params.add("password", employeeDto.getPassword());
        params.add("mobile", employeeDto.getMobile());
        params.add("address", employeeDto.getAddress());
        params.add("birthday", employeeDto.getBirthday().toString());
        params.add("roleId", employeeDto.getRoleId().toString());
        params.add("authoritiesIds", employeeDto.getAuthoritiesIds().stream().map(String::valueOf).collect(Collectors.joining(", ")));
        params.add("isActive", employeeDto.getIsActive().toString());
        params.add("employmentType", employeeDto.getEmploymentType().name());
        params.add("position", employeeDto.getPosition().name());
        params.add("grade", employeeDto.getGrade().name());
        params.add("salary", employeeDto.getSalary().toString());
        params.add("hiredAt", employeeDto.getHiredAt().toString());
        params.add("experiencesIds", employeeDto.getExperiencesIds().stream().map(String::valueOf).collect(Collectors.joining(", ")));
        params.add("studiesIds", employeeDto.getStudiesIds().stream().map(String::valueOf).collect(Collectors.joining(", ")));
        params.add("trainerId", employeeDto.getTrainerId().toString());
        return params;
    }
}
