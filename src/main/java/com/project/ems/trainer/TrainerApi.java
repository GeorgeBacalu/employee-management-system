package com.project.ems.trainer;

import com.project.ems.wrapper.PageWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TrainerApi {

    @Operation(summary = "Find all trainers", description = "Return list with all trainers", tags = "trainer", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation")})
    ResponseEntity<List<TrainerDto>> findAll();

    @Operation(summary = "Find all active trainers", description = "Return list with all active trainers", tags = "trainer", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation")})
    ResponseEntity<List<TrainerDto>> findAllActive();

    @Operation(summary = "Find trainers paginated, sorted and filtered", description = "Return list with trainers paginated, sorted and filtered", tags = "trainer", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation")})
    ResponseEntity<PageWrapper<TrainerDto>> findAllByKey(@Parameter(name = "pageable", description = "Pageable object for paging and sorting") Pageable pageable,
                                                         @Parameter(name = "key", description = "Filtering key") String key);

    @Operation(summary = "Find active trainers paginated, sorted and filtered", description = "Return list with active trainers paginated, sorted and filtered", tags = "trainer", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation")})
    ResponseEntity<PageWrapper<TrainerDto>> findAllActiveByKey(@Parameter(name = "pageable", description = "Pageable object for paging and sorting") Pageable pageable,
                                                               @Parameter(name = "key", description = "Filtering key") String key);

    @Operation(summary = "Find trainer by ID", description = "Return trainer with given ID", tags = "trainer", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Trainer not found")})
    ResponseEntity<TrainerDto> findById(@Parameter(name = "id", description = "Trainer fetching ID") Integer id);

    @Operation(summary = "Save trainer", description = "Save new trainer", tags = "trainer", responses = {
          @ApiResponse(responseCode = "201", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body")})
    ResponseEntity<TrainerDto> save(@RequestBody(description = "Trainer to save") TrainerDto trainerDto);

    @Operation(summary = "Update trainer by ID", description = "Update trainer with given ID", tags = "trainer", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "404", description = "Trainer not found")})
    ResponseEntity<TrainerDto> updateById(@RequestBody(description = "Updated trainer") TrainerDto trainerDto,
                                          @Parameter(name = "id", description = "Trainer updating ID") Integer id);

    @Operation(summary = "Disable trainer by ID", description = "Disable trainer with given ID", tags = "trainer", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Trainer not found")})
    ResponseEntity<TrainerDto> disableById(@Parameter(name = "id", description = "Trainer disabling ID") Integer id);
}
