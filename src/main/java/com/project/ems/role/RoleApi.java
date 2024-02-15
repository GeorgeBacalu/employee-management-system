package com.project.ems.role;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleApi {

    @Operation(summary = "Find all roles", description = "Return list with all roles", tags = "role", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation")})
    ResponseEntity<List<RoleDto>> findAll();

    @Operation(summary = "Find role by ID", description = "Return role with given ID", tags = "role", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Role not found")})
    ResponseEntity<RoleDto> findById(@Parameter(name = "id", description = "Role fetching ID") Integer id);

    @Operation(summary = "Save role", description = "Save new role(USER/ADMIN)", tags = "role", responses = {
          @ApiResponse(responseCode = "201", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body")})
    ResponseEntity<RoleDto> save(@RequestBody(description = "Role to save") RoleDto roleDto);
}
