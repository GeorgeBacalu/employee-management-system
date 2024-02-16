package com.project.ems.study;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudyApi {

    @Operation(summary = "Find all studies", description = "Return list with all studies", tags = "study", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation")})
    ResponseEntity<List<StudyDto>> findAll();

    @Operation(summary = "Find studies paginated, sorted and filtered", description = "Return list with studies paginated, sorted and filtered", tags = "study", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation")})
    ResponseEntity<Page<StudyDto>> findAllByKey(@Parameter(name = "pageable", description = "Pageable object for paging and sorting") Pageable pageable,
                                                @Parameter(name = "key", description = "Filtering key") String key);

    @Operation(summary = "Find study by ID", description = "Return study with given ID", tags = "study", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Study not found")})
    ResponseEntity<StudyDto> findById(@Parameter(name = "id", description = "Study fetching ID") Integer id);

    @Operation(summary = "Save study", description = "Save new study", tags = "study", responses = {
          @ApiResponse(responseCode = "201", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body")})
    ResponseEntity<StudyDto> save(@RequestBody(description = "Study to save") StudyDto studyDto);

    @Operation(summary = "Update study by ID", description = "Update study with given ID", tags = "study", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "404", description = "Study not found")})
    ResponseEntity<StudyDto> updateById(@RequestBody(description = "Updated study") StudyDto studyDto,
                                        @Parameter(name = "id", description = "Study updating ID") Integer id);

    @Operation(summary = "Delete study by ID", description = "Delete study with given ID", tags = "study", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Study not found")})
    ResponseEntity<Void> deleteById(@Parameter(name = "id", description = "Study deleting ID") Integer id);
}
