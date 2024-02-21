package com.project.ems.unit.employee;

import com.project.ems.employee.*;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.wrapper.SearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.EmployeeMock.*;
import static com.project.ems.util.PageUtil.*;
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
    private RedirectAttributes redirectAttributes;

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
    void findAllActivePage_test() {
        Page<EmployeeDto> employeeDtosPage = new PageImpl<>(activeEmployeeDtosPage1);
        int page = PAGEABLE.getPageNumber();
        int size = PAGEABLE.getPageSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        long nrEmployees = employeeDtosPage.getTotalElements();
        int nrPages = employeeDtosPage.getTotalPages();
        SearchRequest searchRequest = new SearchRequest(0, size, field + "," + direction, EMPLOYEE_FILTER_KEY);
        given(employeeService.findAllActiveByKey(PAGEABLE, EMPLOYEE_FILTER_KEY)).willReturn(employeeDtosPage);
        given(model.getAttribute(EMPLOYEES_ATTRIBUTE)).willReturn(activeEmployeesPage1);
        given(model.getAttribute("nrEmployees")).willReturn(nrEmployees);
        given(model.getAttribute("nrPages")).willReturn(nrPages);
        given(model.getAttribute("page")).willReturn(page);
        given(model.getAttribute("size")).willReturn(size);
        given(model.getAttribute("field")).willReturn(field);
        given(model.getAttribute("direction")).willReturn(direction);
        given(model.getAttribute("key")).willReturn(EMPLOYEE_FILTER_KEY);
        given(model.getAttribute("pageStartIndex")).willReturn(getPageStartIndex(page, size));
        given(model.getAttribute("pageEndIndex")).willReturn(getPageEndIndex(page, size, nrEmployees));
        given(model.getAttribute("pageNavigationStartIndex")).willReturn(getPageNavigationStartIndex(page, nrPages));
        given(model.getAttribute("pageNavigationEndIndex")).willReturn(getPageNavigationEndIndex(page, nrPages));
        given(model.getAttribute("searchRequest")).willReturn(searchRequest);
        String viewName = employeeController.findAllActivePage(model, PAGEABLE, EMPLOYEE_FILTER_KEY);
        then(viewName).isEqualTo(EMPLOYEES_VIEW);
        then(model.getAttribute(EMPLOYEES_ATTRIBUTE)).isEqualTo(activeEmployeesPage1);
        then(model.getAttribute("nrEmployees")).isEqualTo(nrEmployees);
        then(model.getAttribute("nrPages")).isEqualTo(nrPages);
        then(model.getAttribute("page")).isEqualTo(page);
        then(model.getAttribute("size")).isEqualTo(size);
        then(model.getAttribute("field")).isEqualTo(field);
        then(model.getAttribute("direction")).isEqualTo(direction);
        then(model.getAttribute("key")).isEqualTo(EMPLOYEE_FILTER_KEY);
        then(model.getAttribute("pageStartIndex")).isEqualTo(getPageStartIndex(page, size));
        then(model.getAttribute("pageEndIndex")).isEqualTo(getPageEndIndex(page, size, nrEmployees));
        then(model.getAttribute("pageNavigationStartIndex")).isEqualTo(getPageNavigationStartIndex(page, nrPages));
        then(model.getAttribute("pageNavigationEndIndex")).isEqualTo(getPageNavigationEndIndex(page, nrPages));
        then(model.getAttribute("searchRequest")).isEqualTo(searchRequest);
    }

    @Test
    void findAllActiveByKey_test() {
        Page<EmployeeDto> employeeDtosPage = new PageImpl<>(activeEmployeeDtosPage1);
        int page = employeeDtosPage.getNumber();
        int size = employeeDtosPage.getSize();
        String sort = getSortField(PAGEABLE) + ',' + getSortDirection(PAGEABLE);
        given(redirectAttributes.getAttribute("page")).willReturn(page);
        given(redirectAttributes.getAttribute("size")).willReturn(size);
        given(redirectAttributes.getAttribute("sort")).willReturn(sort);
        given(redirectAttributes.getAttribute("key")).willReturn(EMPLOYEE_FILTER_KEY);
        String viewName = employeeController.findAllActiveByKey(new SearchRequest(page, size, sort, EMPLOYEE_FILTER_KEY), redirectAttributes);
        then(viewName).isEqualTo(REDIRECT_EMPLOYEES_VIEW);
        then(redirectAttributes.getAttribute("page")).isEqualTo(page);
        then(redirectAttributes.getAttribute("size")).isEqualTo(size);
        then(redirectAttributes.getAttribute("sort")).isEqualTo(sort);
        then(redirectAttributes.getAttribute("key")).isEqualTo(EMPLOYEE_FILTER_KEY);
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
        Page<EmployeeDto> employeeDtosPage = new PageImpl<>(activeEmployeeDtosPage1);
        int page = employeeDtosPage.getNumber();
        int size = employeeDtosPage.getSize();
        String sort = getSortField(PAGEABLE) + ',' + getSortDirection(PAGEABLE);
        given(employeeService.findAllActiveByKey(PAGEABLE, EMPLOYEE_FILTER_KEY)).willReturn(employeeDtosPage);
        given(redirectAttributes.getAttribute("page")).willReturn(page);
        given(redirectAttributes.getAttribute("size")).willReturn(size);
        given(redirectAttributes.getAttribute("sort")).willReturn(sort);
        given(redirectAttributes.getAttribute("key")).willReturn(EMPLOYEE_FILTER_KEY);
        String viewName = employeeController.disableById(VALID_ID, redirectAttributes, PAGEABLE, EMPLOYEE_FILTER_KEY);
        verify(employeeService).disableById(VALID_ID);
        then(viewName).isEqualTo(REDIRECT_EMPLOYEES_VIEW);
        then(redirectAttributes.getAttribute("page")).isEqualTo(page);
        then(redirectAttributes.getAttribute("size")).isEqualTo(size);
        then(redirectAttributes.getAttribute("sort")).isEqualTo(sort);
        then(redirectAttributes.getAttribute("key")).isEqualTo(EMPLOYEE_FILTER_KEY);
    }

    @Test
    void disableById_invalidId_test() {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(employeeService).disableById(INVALID_ID);
        thenThrownBy(() -> employeeController.disableById(INVALID_ID, redirectAttributes, PAGEABLE, EMPLOYEE_FILTER_KEY))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
