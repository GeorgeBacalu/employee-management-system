package com.project.ems.authority;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthorityApi {

    @Operation(summary = "Find all authorities", description = "Return list with all authorities", tags = "authority", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation")})
    ResponseEntity<List<AuthorityDto>> findAll();

    @Operation(summary = "Find authority by ID", description = "Return authority with given ID", tags = "authority", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Authority not found")})
    ResponseEntity<AuthorityDto> findById(@Parameter(name = "id", description = "Authority fetching ID") Integer id);

    @Operation(summary = "Save authority", description = "Save new authority(CREATE/READ/UPDATE/DELETE)", tags = "authority", responses = {
          @ApiResponse(responseCode = "201", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body")})
    ResponseEntity<AuthorityDto> save(@RequestBody(description = "Authority to save") AuthorityDto authorityDto);
}
