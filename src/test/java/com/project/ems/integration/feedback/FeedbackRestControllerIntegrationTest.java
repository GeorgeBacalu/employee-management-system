package com.project.ems.integration.feedback;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.feedback.FeedbackDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.FeedbackMock.*;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class FeedbackRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    private FeedbackDto feedbackDto1;
    private FeedbackDto feedbackDto2;
    private List<FeedbackDto> feedbackDtos;

    @BeforeEach
    void setUp() {
        feedbackDto1 = getMockedFeedbackDto1();
        feedbackDto2 = getMockedFeedbackDto2();
        feedbackDtos = getMockedFeedbackDtos();
    }

    @Test
    void findAll_test() throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_FEEDBACKS, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<FeedbackDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        then(result).isEqualTo(feedbackDtos);
    }

    @Test
    void findById_validId_test() {
        ResponseEntity<FeedbackDto> response = template.getForEntity(API_FEEDBACKS + "/" + VALID_ID, FeedbackDto.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(feedbackDto1);
    }

    @Test
    void findById_invalidId_test() {
        ResponseEntity<String> response = template.getForEntity(API_FEEDBACKS + "/" + INVALID_ID, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(FEEDBACK_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_test() {
        ResponseEntity<FeedbackDto> saveResponse = template.postForEntity(API_FEEDBACKS, feedbackDto1, FeedbackDto.class);
        then(saveResponse).isNotNull();
        then(saveResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        FeedbackDto saveResult = Objects.requireNonNull(saveResponse.getBody());
        then(saveResult.getId()).isEqualTo(feedbackDto1.getId());
        then(saveResult.getType()).isEqualTo(feedbackDto1.getType());
        then(saveResult.getDescription()).isEqualTo(feedbackDto1.getDescription());
        then(saveResult.getUserId()).isEqualTo(feedbackDto1.getUserId());
        then(saveResult.getSentAt()).isNotNull();
    }

    @Test
    void updateById_validId_test() {
        FeedbackDto updatedFeedbackDto = feedbackDto2;
        updatedFeedbackDto.setId(VALID_ID);
        updatedFeedbackDto.setUserId(VALID_ID);
        ResponseEntity<FeedbackDto> updateResponse = template.exchange(API_FEEDBACKS + "/" + VALID_ID, HttpMethod.PUT, new HttpEntity<>(feedbackDto2), FeedbackDto.class);
        then(updateResponse).isNotNull();
        then(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(updateResponse.getBody()).isEqualTo(updatedFeedbackDto);
        ResponseEntity<FeedbackDto> getResponse = template.getForEntity(API_FEEDBACKS + "/" + VALID_ID, FeedbackDto.class);
        then(getResponse).isNotNull();
        then(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(getResponse.getBody()).isEqualTo(updatedFeedbackDto);
    }

    @Test
    void updateById_invalidId_test() {
        ResponseEntity<String> response = template.exchange(API_FEEDBACKS + "/" + INVALID_ID, HttpMethod.PUT, new HttpEntity<>(feedbackDto2), String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(FEEDBACK_NOT_FOUND, INVALID_ID));
    }

    @Test
    void deleteById_validId_test() throws Exception {
        ResponseEntity<FeedbackDto> deleteResponse = template.exchange(API_FEEDBACKS + "/" + VALID_ID, HttpMethod.DELETE, null, FeedbackDto.class);
        then(deleteResponse).isNotNull();
        then(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        ResponseEntity<String> getResponse = template.getForEntity(API_FEEDBACKS + "/" + VALID_ID, String.class);
        then(getResponse).isNotNull();
        then(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(getResponse.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(FEEDBACK_NOT_FOUND, VALID_ID));
        ResponseEntity<String> findAllResponse = template.getForEntity(API_FEEDBACKS, String.class);
        then(findAllResponse).isNotNull();
        then(findAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<FeedbackDto> result = objectMapper.readValue(findAllResponse.getBody(), new TypeReference<>() {});
        List<FeedbackDto> feedbackDtosCopy = new ArrayList<>(feedbackDtos);
        feedbackDtosCopy.remove(feedbackDto1);
        then(result).isEqualTo(feedbackDtosCopy);
    }

    @Test
    void deleteById_invalidId_test() {
        ResponseEntity<String> response = template.exchange(API_FEEDBACKS + "/" + INVALID_ID, HttpMethod.DELETE, null, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(FEEDBACK_NOT_FOUND, INVALID_ID));
    }
}
