package com.project.ems.integration.employee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeRestController;
import com.project.ems.employee.EmployeeService;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.wrapper.PageWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.EmployeeMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(EmployeeRestController.class)
class EmployeeRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;
    private List<EmployeeDto> employeeDtos;
    private List<EmployeeDto> activeEmployeeDtos;
    private List<EmployeeDto> employeeDtosListPage1;
    private List<EmployeeDto> employeeDtosListPage2;
    private List<EmployeeDto> employeeDtosListPage3;

    @BeforeEach
    void setUp() {
        employeeDto1 = getMockedEmployeeDto1();
        employeeDto2 = getMockedEmployeeDto2();
        employeeDtos = getMockedEmployeeDtos();
        activeEmployeeDtos = getMockedActiveEmployeeDtos();
        employeeDtosListPage1 = getMockedEmployeeDtosPage1();
        employeeDtosListPage2 = getMockedEmployeeDtosPage2();
        employeeDtosListPage3 = getMockedEmployeeDtosPage3();
    }

    @Test
    void findAll_test() throws Exception {
        given(employeeService.findAll()).willReturn(employeeDtos);
        ResultActions actions = mockMvc.perform(get(API_EMPLOYEES)).andExpect(status().isOk());
        for (int i = 0; i < employeeDtos.size(); ++i) {
            assertEmployeeDto(actions, "$[" + i + "]", employeeDtos.get(i));
        }
        List<EmployeeDto> result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        then(result).isEqualTo(employeeDtos);
    }

    @Test
    void findAllActive_test() throws Exception {
        given(employeeService.findAllActive()).willReturn(activeEmployeeDtos);
        ResultActions actions = mockMvc.perform(get(API_EMPLOYEES + "/active")).andExpect(status().isOk());
        for (int i = 0; i < activeEmployeeDtos.size(); ++i) {
            assertEmployeeDto(actions, "$[" + i + "]", activeEmployeeDtos.get(i));
        }
        List<EmployeeDto> result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        then(result).isEqualTo(activeEmployeeDtos);
    }

    @Test
    void findById_validId_test() throws Exception {
        given(employeeService.findById(VALID_ID)).willReturn(employeeDto1);
        ResultActions actions = mockMvc.perform(get(API_EMPLOYEES + "/{id}", VALID_ID)).andExpect(status().isOk());
        assertEmployeeDtoJson(actions, employeeDto1);
        EmployeeDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), EmployeeDto.class);
        then(result).isEqualTo(employeeDto1);
    }

    @Test
    void findById_invalidId_test() throws Exception {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        given(employeeService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(API_EMPLOYEES + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_test() throws Exception {
        given(employeeService.save(any(EmployeeDto.class))).willReturn(employeeDto1);
        ResultActions actions = mockMvc.perform(post(API_EMPLOYEES)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(employeeDto1)))
              .andExpect(status().isCreated());
        assertEmployeeDtoJson(actions, employeeDto1);
        EmployeeDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), EmployeeDto.class);
        then(result).isEqualTo(employeeDto1);
    }

    @Test
    void updateById_validId_test() throws Exception {
        EmployeeDto updatedEmployeeDto = employeeDto2;
        updatedEmployeeDto.setId(VALID_ID);
        given(employeeService.updateById(employeeDto2, VALID_ID)).willReturn(updatedEmployeeDto);
        ResultActions actions = mockMvc.perform(put(API_EMPLOYEES + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(employeeDto2)))
              .andExpect(status().isOk());
        assertEmployeeDtoJson(actions, updatedEmployeeDto);
        EmployeeDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), EmployeeDto.class);
        then(result).isEqualTo(updatedEmployeeDto);
    }

    @Test
    void updateById_invalidId_test() throws Exception {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        given(employeeService.updateById(employeeDto2, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(put(API_EMPLOYEES + "/{id}", INVALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(employeeDto2)))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void disableById_validId_test() throws Exception {
        EmployeeDto disabledEmployeeDto = employeeDto1;
        disabledEmployeeDto.setIsActive(false);
        given(employeeService.disableById(VALID_ID)).willReturn(disabledEmployeeDto);
        ResultActions actions = mockMvc.perform(delete(API_EMPLOYEES + "/{id}", VALID_ID)).andExpect(status().isOk());
        assertEmployeeDtoJson(actions, disabledEmployeeDto);
        EmployeeDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), EmployeeDto.class);
        then(result).isEqualTo(disabledEmployeeDto);
    }

    @Test
    void disableById_invalidId_test() throws Exception {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        given(employeeService.disableById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(delete(API_EMPLOYEES + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void findAllByKey_test(int page, int size, String sortField, String sortDirection, String key, Page<EmployeeDto> expectedPage) throws Exception {
        given(employeeService.findAllByKey(any(Pageable.class), eq(key))).willReturn(expectedPage);
        ResultActions actions = mockMvc.perform(get(API_EMPLOYEES + API_PAGINATION, page, size, sortField, sortDirection, key)).andExpect(status().isOk());
        List<EmployeeDto> expectedPageContent = expectedPage.getContent();
        for (int i = 0; i < expectedPageContent.size(); ++i) {
            assertEmployeeDto(actions, "$.content[" + i + "]", expectedPageContent.get(i));
        }
        PageWrapper<EmployeeDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        then(response.getContent()).isEqualTo(expectedPageContent);
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void findAllActiveByKey_test(int page, int size, String sortField, String sortDirection, String key, Page<EmployeeDto> expectedPage) throws Exception {
        given(employeeService.findAllActiveByKey(any(Pageable.class), eq(key))).willReturn(expectedPage);
        ResultActions actions = mockMvc.perform(get(API_EMPLOYEES + API_ACTIVE_PAGINATION, page, size, sortField, sortDirection, key)).andExpect(status().isOk());
        List<EmployeeDto> expectedPageActiveContent = expectedPage.getContent();
        for (int i = 0; i < expectedPageActiveContent.size(); ++i) {
            assertEmployeeDto(actions, "$.content[" + i + "]", expectedPageActiveContent.get(i));
        }
        PageWrapper<EmployeeDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        then(response.getContent()).isEqualTo(expectedPageActiveContent);
    }

    private Stream<Arguments> paginationArguments() {
        Page<EmployeeDto> employeeDtosPage1 = new PageImpl<>(employeeDtosListPage1);
        Page<EmployeeDto> employeeDtosPage2 = new PageImpl<>(employeeDtosListPage2);
        Page<EmployeeDto> employeeDtosPage3 = new PageImpl<>(employeeDtosListPage3);
        Page<EmployeeDto> emptyPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", "asc", EMPLOYEE_FILTER_KEY, employeeDtosPage1),
              Arguments.of(1, 2, "id", "asc", EMPLOYEE_FILTER_KEY, employeeDtosPage2),
              Arguments.of(2, 2, "id", "asc", EMPLOYEE_FILTER_KEY, emptyPage),
              Arguments.of(0, 2, "id", "asc", "", employeeDtosPage1),
              Arguments.of(1, 2, "id", "asc", "", employeeDtosPage2),
              Arguments.of(2, 2, "id", "asc", "", employeeDtosPage3));
    }

    private void assertEmployeeDto(ResultActions actions, String prefix, EmployeeDto employeeDto) throws Exception {
        actions.andExpect(jsonPath(prefix + ".id").value(employeeDto.getId()))
              .andExpect(jsonPath(prefix + ".name").value(employeeDto.getName()))
              .andExpect(jsonPath(prefix + ".email").value(employeeDto.getEmail()))
              .andExpect(jsonPath(prefix + ".password").value(employeeDto.getPassword()))
              .andExpect(jsonPath(prefix + ".mobile").value(employeeDto.getMobile()))
              .andExpect(jsonPath(prefix + ".address").value(employeeDto.getAddress()))
              .andExpect(jsonPath(prefix + ".birthday").value(employeeDto.getBirthday().toString()))
              .andExpect(jsonPath(prefix + ".roleId").value(employeeDto.getRoleId()))
              .andExpect(jsonPath(prefix + ".isActive").value(employeeDto.getIsActive()))
              .andExpect(jsonPath(prefix + ".employmentType").value(employeeDto.getEmploymentType().name()))
              .andExpect(jsonPath(prefix + ".position").value(employeeDto.getPosition().name()))
              .andExpect(jsonPath(prefix + ".grade").value(employeeDto.getGrade().name()))
              .andExpect(jsonPath(prefix + ".salary").value(employeeDto.getSalary()))
              .andExpect(jsonPath(prefix + ".hiredAt").value(employeeDto.getHiredAt().toString()))
              .andExpect(jsonPath(prefix + ".trainerId").value(employeeDto.getTrainerId()));
        for (int i = 0; i < employeeDto.getAuthoritiesIds().size(); ++i) {
            actions.andExpect(jsonPath(prefix + ".authoritiesIds[" + i + "]").value(employeeDto.getAuthoritiesIds().get(i)));
        }
        for (int i = 0; i < employeeDto.getExperiencesIds().size(); ++i) {
            actions.andExpect(jsonPath(prefix + ".experiencesIds[" + i + "]").value(employeeDto.getExperiencesIds().get(i)));
        }
        for (int i = 0; i < employeeDto.getStudiesIds().size(); ++i) {
            actions.andExpect(jsonPath(prefix + ".studiesIds[" + i + "]").value(employeeDto.getStudiesIds().get(i)));
        }
    }

    private void assertEmployeeDtoJson(ResultActions actions, EmployeeDto employeeDto) throws Exception {
        actions.andExpect(jsonPath("$.id").value(employeeDto.getId()))
              .andExpect(jsonPath("$.name").value(employeeDto.getName()))
              .andExpect(jsonPath("$.email").value(employeeDto.getEmail()))
              .andExpect(jsonPath("$.password").value(employeeDto.getPassword()))
              .andExpect(jsonPath("$.mobile").value(employeeDto.getMobile()))
              .andExpect(jsonPath("$.address").value(employeeDto.getAddress()))
              .andExpect(jsonPath("$.birthday").value(employeeDto.getBirthday().toString()))
              .andExpect(jsonPath("$.roleId").value(employeeDto.getRoleId()))
              .andExpect(jsonPath("$.isActive").value(employeeDto.getIsActive()))
              .andExpect(jsonPath("$.employmentType").value(employeeDto.getEmploymentType().name()))
              .andExpect(jsonPath("$.position").value(employeeDto.getPosition().name()))
              .andExpect(jsonPath("$.grade").value(employeeDto.getGrade().name()))
              .andExpect(jsonPath("$.salary").value(employeeDto.getSalary()))
              .andExpect(jsonPath("$.hiredAt").value(employeeDto.getHiredAt().toString()))
              .andExpect(jsonPath("$.trainerId").value(employeeDto.getTrainerId()));
        for (int i = 0; i < employeeDto.getAuthoritiesIds().size(); ++i) {
            actions.andExpect(jsonPath("$.authoritiesIds[" + i + "]").value(employeeDto.getAuthoritiesIds().get(i)));
        }
        for (int i = 0; i < employeeDto.getExperiencesIds().size(); ++i) {
            actions.andExpect(jsonPath("$.experiencesIds[" + i + "]").value(employeeDto.getExperiencesIds().get(i)));
        }
        for (int i = 0; i < employeeDto.getStudiesIds().size(); ++i) {
            actions.andExpect(jsonPath("$.studiesIds[" + i + "]").value(employeeDto.getStudiesIds().get(i)));
        }
    }
}
