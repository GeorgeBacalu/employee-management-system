package com.project.ems.integration.feedback;

import com.project.ems.exception.InvalidRequestException;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.feedback.*;
import com.project.ems.user.UserService;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.FeedbackMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    private List<Feedback> feedbacksPage1;
    private List<Feedback> feedbacksPage2;
    private List<Feedback> feedbacksPage3;
    private FeedbackDto feedbackDto1;
    private FeedbackDto feedbackDto2;
    private List<FeedbackDto> feedbackDtos;
    private List<FeedbackDto> feedbackDtosPage1;
    private List<FeedbackDto> feedbackDtosPage2;
    private List<FeedbackDto> feedbackDtosPage3;

    @BeforeEach
    void setUp() {
        feedback1 = getMockedFeedback1();
        feedback2 = getMockedFeedback2();
        feedbacks = getMockedFeedbacks();
        feedbacksPage1 = getMockedFeedbacksPage1();
        feedbacksPage2 = getMockedFeedbacksPage2();
        feedbacksPage3 = getMockedFeedbacksPage3();
        feedbackDto1 = getMockedFeedbackDto1();
        feedbackDto2 = getMockedFeedbackDto2();
        feedbackDtos = getMockedFeedbackDtos();
        feedbackDtosPage1 = getMockedFeedbackDtosPage1();
        feedbackDtosPage2 = getMockedFeedbackDtosPage2();
        feedbackDtosPage3 = getMockedFeedbackDtosPage3();
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

    @ParameterizedTest
    @CsvSource({"0, ${FEEDBACK_FILTER_KEY}", "1, ${FEEDBACK_FILTER_KEY}", "2, ${FEEDBACK_FILTER_KEY}", "0, ''", "1, ''", "2, ''"})
    void findAllByKey_test(int page, String key) {
        Pair<Pageable, List<Feedback>> pageableFeedbacksPair = switch (page) {
            case 0 -> Pair.of(PAGEABLE_PAGE1, feedbacksPage1);
            case 1 -> Pair.of(PAGEABLE_PAGE2, feedbacksPage2);
            case 2 -> Pair.of(PAGEABLE_PAGE3, key.trim().isEmpty() ? feedbacksPage3 : Collections.emptyList());
            default -> throw new InvalidRequestException(INVALID_PAGE_NUMBER + page);
        };
        Page<Feedback> filteredFeedbacksPage = new PageImpl<>(pageableFeedbacksPair.getRight());
        if (key.trim().isEmpty()) {
            given(feedbackRepository.findAll(any(Pageable.class))).willReturn(filteredFeedbacksPage);
        } else {
            given(feedbackRepository.findAllByKey(any(Pageable.class), eq(key.toLowerCase()))).willReturn(filteredFeedbacksPage);
        }
        Page<FeedbackDto> result = feedbackService.findAllByKey(pageableFeedbacksPair.getLeft(), key);
        then(result.getContent()).isEqualTo(feedbackService.convertToDtos(pageableFeedbacksPair.getRight()));
    }
}
