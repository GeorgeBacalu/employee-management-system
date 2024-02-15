package com.project.ems.integration.feedback;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.feedback.*;
import com.project.ems.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.FeedbackMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class FeedbackServiceIntegrationTest {

    @Autowired
    private FeedbackServiceImpl feedbackService;

    @MockBean
    private FeedbackRepository feedbackRepository;

    @MockBean
    private UserService userService;

    @SpyBean
    private ModelMapper modelMapper;

    @MockBean
    private Clock clock;

    private static final ZonedDateTime zonedDateTime = ZonedDateTime.of(2024, 1, 1, 0, 0, 0, 0, ZoneId.of("Europe/Bucharest"));

    @Captor
    private ArgumentCaptor<Feedback> feedbackCaptor;

    private Feedback feedback1;
    private Feedback feedback2;
    private List<Feedback> feedbacks;
    private FeedbackDto feedbackDto1;
    private FeedbackDto feedbackDto2;
    private List<FeedbackDto> feedbackDtos;

    @BeforeEach
    void setUp() {
        feedback1 = getMockedFeedback1();
        feedback2 = getMockedFeedback2();
        feedbacks = getMockedFeedbacks();
        feedbackDto1 = getMockedFeedbackDto1();
        feedbackDto2 = getMockedFeedbackDto2();
        feedbackDtos = getMockedFeedbackDtos();
    }

    @Test
    void findAll_test() {
        given(feedbackRepository.findAll()).willReturn(feedbacks);
        List<FeedbackDto> result = feedbackService.findAll();
        then(result).isEqualTo(feedbackDtos);
    }

    @Test
    void findById_validId_test() {
        given(feedbackRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(feedback1));
        FeedbackDto result = feedbackService.findById(VALID_ID);
        then(result).isEqualTo(feedbackDto1);
    }

    @Test
    void findById_invalidId_test() {
        thenThrownBy(() -> feedbackService.findById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(FEEDBACK_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_test() {
        given(clock.getZone()).willReturn(zonedDateTime.getZone());
        given(clock.instant()).willReturn(zonedDateTime.toInstant());
        Feedback savedFeedback = feedback1;
        savedFeedback.setSentAt(zonedDateTime.toLocalDateTime());
        given(feedbackRepository.save(any(Feedback.class))).willReturn(savedFeedback);
        FeedbackDto result = feedbackService.save(feedbackDto1);
        verify(feedbackRepository).save(feedbackCaptor.capture());
        then(result).isEqualTo(feedbackService.convertToDto(savedFeedback));
    }

    @Test
    void updateById_validId_test() {
        Feedback updatedFeedback = feedback2;
        updatedFeedback.setId(VALID_ID);
        given(feedbackRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(feedback1));
        given(feedbackRepository.save(any(Feedback.class))).willReturn(updatedFeedback);
        FeedbackDto result = feedbackService.updateById(feedbackDto2, VALID_ID);
        verify(feedbackRepository).save(feedbackCaptor.capture());
        then(result).isEqualTo(feedbackService.convertToDto(updatedFeedback));
    }

    @Test
    void updateById_invalidId_test() {
        thenThrownBy(() -> feedbackService.updateById(feedbackDto2, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(FEEDBACK_NOT_FOUND, INVALID_ID));
        verify(feedbackRepository, never()).save(any(Feedback.class));
    }

    @Test
    void deleteById_validId_test() {
        given(feedbackRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(feedback1));
        feedbackService.deleteById(VALID_ID);
        verify(feedbackRepository).delete(feedback1);
    }

    @Test
    void deleteById_invalidId_test() {
        thenThrownBy(() -> feedbackService.deleteById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(FEEDBACK_NOT_FOUND, INVALID_ID));
        verify(feedbackRepository, never()).delete(any(Feedback.class));
    }
}
