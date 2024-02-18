package com.project.ems.experience;

import com.project.ems.wrapper.PageWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExperienceApi {

    @Operation(summary = "Find all experiences", description = "Return list with all experiences", tags = "experience", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation")})
    ResponseEntity<List<ExperienceDto>> findAll();

    @Operation(summary = "Find experiences paginated, sorted and filtered", description = "Return list with experiences paginated, sorted and filtered", tags = "experience", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation")})
    ResponseEntity<PageWrapper<ExperienceDto>> findAllByKey(@Parameter(name = "pageable", description = "Pageable object for paging and sorting") Pageable pageable,
                                                            @Parameter(name = "key", description = "Filtering key") String key);

    @Operation(summary = "Find experience by ID", description = "Return experience with given ID", tags = "experience", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Experience not found")})
    ResponseEntity<ExperienceDto> findById(@Parameter(name = "id", description = "Experience fetching ID") Integer id);

    @Operation(summary = "Save experience", description = "Save new experience", tags = "experience", responses = {
          @ApiResponse(responseCode = "201", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body")})
    ResponseEntity<ExperienceDto> save(@RequestBody(description = "Experience to save") ExperienceDto experienceDto);

    @Operation(summary = "Update experience by ID", description = "Update experience with given ID", tags = "experience", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "404", description = "Experience not found")})
    ResponseEntity<ExperienceDto> updateById(@RequestBody(description = "Updated experience") ExperienceDto experienceDto,
                                             @Parameter(name = "id", description = "Experience updating ID") Integer id);

    @Operation(summary = "Delete experience by ID", description = "Delete experience with given ID", tags = "experience", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Experience not found")})
    ResponseEntity<Void> deleteById(@Parameter(name = "id", description = "Experience deleting ID") Integer id);
}
