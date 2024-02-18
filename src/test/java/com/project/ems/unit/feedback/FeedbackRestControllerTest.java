package com.project.ems.unit.feedback;

import com.project.ems.exception.InvalidRequestException;
import com.project.ems.feedback.FeedbackDto;
import com.project.ems.feedback.FeedbackRestController;
import com.project.ems.feedback.FeedbackService;
import com.project.ems.wrapper.PageWrapper;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.FeedbackMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    private List<FeedbackDto> feedbackDtosPage1;
    private List<FeedbackDto> feedbackDtosPage2;
    private List<FeedbackDto> feedbackDtosPage3;

    @BeforeEach
    void setUp() {
        feedbackDto1 = getMockedFeedbackDto1();
        feedbackDto2 = getMockedFeedbackDto2();
        feedbackDtos = getMockedFeedbackDtos();
        feedbackDtosPage1 = getMockedFeedbackDtosPage1();
        feedbackDtosPage2 = getMockedFeedbackDtosPage2();
        feedbackDtosPage3 = getMockedFeedbackDtosPage3();
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

    @ParameterizedTest
    @CsvSource({"0, ${FEEDBACK_FILTER_KEY}", "1, ${FEEDBACK_FILTER_KEY}", "2, ${FEEDBACK_FILTER_KEY}", "0, ''", "1, ''", "2, ''"})
    void findAllByKey_test(int page, String key) {
        Pair<Pageable, List<FeedbackDto>> pageableFeedbackDtosPair = switch (page) {
            case 0 -> Pair.of(PAGEABLE_PAGE1, feedbackDtosPage1);
            case 1 -> Pair.of(PAGEABLE_PAGE2, feedbackDtosPage2);
            case 2 -> Pair.of(PAGEABLE_PAGE3, key.trim().isEmpty() ? feedbackDtosPage3 : Collections.emptyList());
            default -> throw new InvalidRequestException(INVALID_PAGE_NUMBER + page);
        };
        Page<FeedbackDto> filteredFeedbackDtosPage = new PageImpl<>(pageableFeedbackDtosPair.getRight());
        given(feedbackService.findAllByKey(any(Pageable.class), eq(key))).willReturn(filteredFeedbackDtosPage);
        ResponseEntity<PageWrapper<FeedbackDto>> response = feedbackRestController.findAllByKey(pageableFeedbackDtosPair.getLeft(), key);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody().getContent()).isEqualTo(filteredFeedbackDtosPage.getContent());
    }
}
