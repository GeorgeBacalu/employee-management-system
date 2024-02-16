package com.project.ems.feedback;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FeedbackApi {

    @Operation(summary = "Find all feedbacks", description = "Return list with all feedbacks", tags = "feedback", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation")})
    ResponseEntity<List<FeedbackDto>> findAll();

    @Operation(summary = "Find feedbacks paginated, sorted and filtered", description = "Return list with feedbacks paginated, sorted and filtered", tags = "feedback", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation")})
    ResponseEntity<Page<FeedbackDto>> findAllByKey(@Parameter(name = "pageable", description = "Pageable object for paging and sorting") Pageable pageable,
                                                   @Parameter(name = "key", description = "Filtering key") String key);

    @Operation(summary = "Find feedback by ID", description = "Return feedback with given ID", tags = "feedback", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Feedback not found")})
    ResponseEntity<FeedbackDto> findById(@Parameter(name = "id", description = "Feedback fetching ID") Integer id);

    @Operation(summary = "Save feedback", description = "Save new feedback", tags = "feedback", responses = {
          @ApiResponse(responseCode = "201", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body")})
    ResponseEntity<FeedbackDto> save(@RequestBody(description = "Feedback to save") FeedbackDto feedbackDto);

    @Operation(summary = "Update feedback by ID", description = "Update feedback with given ID", tags = "feedback", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "400", description = "Invalid request body"),
          @ApiResponse(responseCode = "404", description = "Feedback not found")})
    ResponseEntity<FeedbackDto> updateById(@RequestBody(description = "Updated feedback") FeedbackDto feedbackDto,
                                           @Parameter(name = "id", description = "Feedback updating ID") Integer id);

    @Operation(summary = "Delete feedback by ID", description = "Delete feedback with given ID", tags = "feedback", responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation"),
          @ApiResponse(responseCode = "404", description = "Feedback not found")})
    ResponseEntity<Void> deleteById(@Parameter(name = "id", description = "Feedback deleting ID") Integer id);
}
