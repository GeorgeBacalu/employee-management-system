package com.project.ems.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserApi {

    @Operation(summary = "Find all users", description = "Return list with all users", tags = "user", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation")})
    ResponseEntity<List<UserDto>> findAll();

    @Operation(summary = "Find user by ID", description = "Return user with given ID", tags = "user", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "User not found")})
    ResponseEntity<UserDto> findById(@Parameter(name = "id", description = "User fetching ID") Integer id);

    @Operation(summary = "Save user", description = "Save new user", tags = "user", responses = {
          @ApiResponse(responseCode = "201", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body")})
    ResponseEntity<UserDto> save(@RequestBody(description = "User to save") UserDto userDto);

    @Operation(summary = "Update user by ID", description = "Update user with given ID", tags = "user", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "404", description = "User not found")})
    ResponseEntity<UserDto> updateById(@RequestBody(description = "Updated user") UserDto userDto,
                                       @Parameter(name = "id", description = "User updating ID") Integer id);

    @Operation(summary = "Disable user by ID", description = "Disable user with given ID", tags = "user", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "User not found")})
    ResponseEntity<UserDto> disableById(@Parameter(name = "id", description = "User disabling ID") Integer id);
}
