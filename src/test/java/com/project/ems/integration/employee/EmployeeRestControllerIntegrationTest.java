package com.project.ems.integration.employee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.employee.EmployeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.EmployeeMock.*;
import static org.assertj.core.api.BDDAssertions.then;

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

    @BeforeEach
    void setUp() {
        employeeDto1 = getMockedEmployeeDto1();
        employeeDto2 = getMockedEmployeeDto2();
        employeeDtos = getMockedEmployeeDtos();
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
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(EMPLOYEE_NOT_FOUND, INVALID_ID));
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
        then(result).isEqualTo(employeeDtos);
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
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(EMPLOYEE_NOT_FOUND, INVALID_ID));
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
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(EMPLOYEE_NOT_FOUND, INVALID_ID));
    }
}
