package com.project.ems.unit.feedback;

import com.project.ems.feedback.FeedbackDto;
import com.project.ems.feedback.FeedbackRestController;
import com.project.ems.feedback.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.FeedbackMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FeedbackRestControllerTest {

    @InjectMocks
    private FeedbackRestController feedbackRestController;

    @Mock
    private FeedbackService feedbackService;

    @Spy
    private ModelMapper modelMapper;

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
    void findAll_test() {
        given(feedbackService.findAll()).willReturn(feedbackDtos);
        ResponseEntity<List<FeedbackDto>> response = feedbackRestController.findAll();
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(feedbackDtos);
    }

    @Test
    void findById_test() {
        given(feedbackService.findById(VALID_ID)).willReturn(feedbackDto1);
        ResponseEntity<FeedbackDto> response = feedbackRestController.findById(VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(feedbackDto1);
    }

    @Test
    void save_test() {
        given(feedbackService.save(feedbackDto1)).willReturn(feedbackDto1);
        ResponseEntity<FeedbackDto> response = feedbackRestController.save(feedbackDto1);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        then(response.getBody()).isEqualTo(feedbackDto1);
    }

    @Test
    void updateById_test() {
        given(feedbackService.updateById(feedbackDto2, VALID_ID)).willReturn(feedbackDto2);
        ResponseEntity<FeedbackDto> response = feedbackRestController.updateById(feedbackDto2, VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(feedbackDto2);
    }

    @Test
    void deleteById_test() {
        ResponseEntity<Void> response = feedbackRestController.deleteById(VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
