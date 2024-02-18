package com.project.ems.integration.employee;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeesPage1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeesPage2;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
class EmployeeRepositoryIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private List<Employee> filteredEmployeesPage1;
    private List<Employee> filteredEmployeesPage2;

    @BeforeEach
    void setUp() {
        filteredEmployeesPage1 = getMockedEmployeesPage1();
        filteredEmployeesPage2 = getMockedEmployeesPage2();
    }

    @Test
    void findAllByKey_test() {
        then(employeeRepository.findAllByKey(PAGEABLE_PAGE1, EMPLOYEE_FILTER_KEY).getContent()).usingRecursiveComparison().isEqualTo(filteredEmployeesPage1);
        then(employeeRepository.findAllByKey(PAGEABLE_PAGE2, EMPLOYEE_FILTER_KEY).getContent()).usingRecursiveComparison().isEqualTo(filteredEmployeesPage2);
        then(employeeRepository.findAllByKey(PAGEABLE_PAGE3, EMPLOYEE_FILTER_KEY).getContent()).isEmpty();
    }

    @Test
    void findAllActiveByKey_test() {
        then(employeeRepository.findAllActiveByKey(PAGEABLE_PAGE1, EMPLOYEE_FILTER_KEY).getContent()).usingRecursiveComparison().isEqualTo(filteredEmployeesPage1);
        then(employeeRepository.findAllActiveByKey(PAGEABLE_PAGE2, EMPLOYEE_FILTER_KEY).getContent()).usingRecursiveComparison().isEqualTo(filteredEmployeesPage2);
        then(employeeRepository.findAllActiveByKey(PAGEABLE_PAGE3, EMPLOYEE_FILTER_KEY).getContent()).isEmpty();
    }
}
