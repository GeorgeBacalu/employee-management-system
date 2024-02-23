package com.project.ems.integration.employee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.wrapper.PageWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.EmployeeMock.*;
import static org.assertj.core.api.BDDAssertions.then;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class EmployeeRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

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
        ResponseEntity<String> response = template.getForEntity(API_EMPLOYEES, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<EmployeeDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        then(result).isEqualTo(employeeDtos);
    }

    @Test
    void findAllActive_test() throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_EMPLOYEES + "/active", String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<EmployeeDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        then(result).isEqualTo(activeEmployeeDtos);
    }

    @Test
    void findById_validId_test() {
        ResponseEntity<EmployeeDto> response = template.getForEntity(API_EMPLOYEES + "/" + VALID_ID, EmployeeDto.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(employeeDto1);
    }

    @Test
    void findById_invalidId_test() {
        ResponseEntity<String> response = template.getForEntity(API_EMPLOYEES + "/" + INVALID_ID, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(USER_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_test() throws Exception {
        ResponseEntity<EmployeeDto> saveResponse = template.postForEntity(API_EMPLOYEES, employeeDto1, EmployeeDto.class);
        then(saveResponse).isNotNull();
        then(saveResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        then(saveResponse.getBody()).isEqualTo(employeeDto1);
        ResponseEntity<String> findAllResponse = template.getForEntity(API_EMPLOYEES, String.class);
        then(findAllResponse).isNotNull();
        then(findAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<EmployeeDto> result = objectMapper.readValue(findAllResponse.getBody(), new TypeReference<>() {});
        then(result).containsAll(employeeDtos);
    }

    @Test
    void updateById_validId_test() {
        EmployeeDto updatedEmployeeDto = employeeDto2;
        updatedEmployeeDto.setId(VALID_ID);
        updatedEmployeeDto.setIsActive(true);
        updatedEmployeeDto.setName("test_new_name");
        updatedEmployeeDto.setEmail("test_new_email@email.com");
        ResponseEntity<EmployeeDto> updateResponse = template.exchange(API_EMPLOYEES + "/" + VALID_ID, HttpMethod.PUT, new HttpEntity<>(employeeDto2), EmployeeDto.class);
        then(updateResponse).isNotNull();
        then(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(updateResponse.getBody()).isEqualTo(updatedEmployeeDto);
        ResponseEntity<EmployeeDto> getResponse = template.getForEntity(API_EMPLOYEES + "/" + VALID_ID, EmployeeDto.class);
        then(getResponse).isNotNull();
        then(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(getResponse.getBody()).isEqualTo(updatedEmployeeDto);
    }

    @Test
    void updateById_invalidId_test() {
        ResponseEntity<String> response = template.exchange(API_EMPLOYEES + "/" + INVALID_ID, HttpMethod.PUT, new HttpEntity<>(employeeDto2), String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(USER_NOT_FOUND, INVALID_ID));
    }

    @Test
    void disableById_validId_test() throws Exception {
        EmployeeDto disabledEmployeeDto = employeeDto1;
        disabledEmployeeDto.setIsActive(false);
        ResponseEntity<EmployeeDto> disableResponse = template.exchange(API_EMPLOYEES + "/" + VALID_ID, HttpMethod.DELETE, null, EmployeeDto.class);
        then(disableResponse).isNotNull();
        then(disableResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(disableResponse.getBody()).isEqualTo(disabledEmployeeDto);
        ResponseEntity<String> findAllResponse = template.getForEntity(API_EMPLOYEES, String.class);
        then(findAllResponse).isNotNull();
        then(findAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<EmployeeDto> result = objectMapper.readValue(findAllResponse.getBody(), new TypeReference<>() {});
        List<EmployeeDto> employeeDtosCopy = new ArrayList<>(employeeDtos);
        employeeDtosCopy.getFirst().setIsActive(false);
        then(result).containsAll(employeeDtosCopy);
    }

    @Test
    void disableById_invalidId_test() {
        ResponseEntity<String> response = template.exchange(API_EMPLOYEES + "/" + INVALID_ID, HttpMethod.DELETE, null, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(USER_NOT_FOUND, INVALID_ID));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void findAllByKey_test(int page, int size, String sortField, String sortDirection, String key, Page<EmployeeDto> expectedPage) throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_EMPLOYEES + String.format(API_PAGINATION2, page, size, sortField, sortDirection, key), String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PageWrapper<EmployeeDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        then(result.getContent()).isEqualTo(expectedPage.getContent());
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void findAllActiveByKey_test(int page, int size, String sortField, String sortDirection, String key, Page<EmployeeDto> expectedPage) throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_EMPLOYEES + String.format(API_ACTIVE_PAGINATION2, page, size, sortField, sortDirection, key), String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PageWrapper<EmployeeDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        then(result.getContent()).isEqualTo(expectedPage.getContent());
    }

    private Stream<Arguments> paginationArguments() {
        Page<EmployeeDto> employeeDtosPage1 = new PageImpl<>(employeeDtosListPage1);
        Page<EmployeeDto> employeeDtosPage2 = new PageImpl<>(employeeDtosListPage2);
        Page<EmployeeDto> employeeDtosPage3 = new PageImpl<>(employeeDtosListPage3);
        Page<EmployeeDto> emptyPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", "asc", USER_FILTER_KEY, employeeDtosPage1),
                         Arguments.of(1, 2, "id", "asc", USER_FILTER_KEY, employeeDtosPage2),
                         Arguments.of(2, 2, "id", "asc", USER_FILTER_KEY, emptyPage),
                         Arguments.of(0, 2, "id", "asc", "", employeeDtosPage1),
                         Arguments.of(1, 2, "id", "asc", "", employeeDtosPage2),
                         Arguments.of(2, 2, "id", "asc", "", employeeDtosPage3));
    }
}
