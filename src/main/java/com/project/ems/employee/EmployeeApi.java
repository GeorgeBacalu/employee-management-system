package com.project.ems.employee;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeApi {

    @Operation(summary = "Find all employees", description = "Return list with all employees", tags = "employee", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation")})
    ResponseEntity<List<EmployeeDto>> findAll();

    @Operation(summary = "Find all active employees", description = "Return list with all active employees", tags = "employee", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation")})
    ResponseEntity<List<EmployeeDto>> findAllActive();

    @Operation(summary = "Find employee by ID", description = "Return employee with given ID", tags = "employee", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Employee not found")})
    ResponseEntity<EmployeeDto> findById(@Parameter(name = "id", description = "Employee fetching ID") Integer id);

    @Operation(summary = "Save employee", description = "Save new employee", tags = "employee", responses = {
          @ApiResponse(responseCode = "201", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body")})
    ResponseEntity<EmployeeDto> save(@RequestBody(description = "Employee to save") EmployeeDto employeeDto);

    @Operation(summary = "Update employee by ID", description = "Update employee with given ID", tags = "employee", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "404", description = "Employee not found")})
    ResponseEntity<EmployeeDto> updateById(@RequestBody(description = "Updated employee") EmployeeDto employeeDto,
                                           @Parameter(name = "id", description = "Employee updating ID") Integer id);

    @Operation(summary = "Disable employee by ID", description = "Disable employee with given ID", tags = "employee", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Employee not found")})
    ResponseEntity<EmployeeDto> disableById(@Parameter(name = "id", description = "Employee disabling ID") Integer id);
}
