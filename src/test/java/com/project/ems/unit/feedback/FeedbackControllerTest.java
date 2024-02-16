package com.project.ems.unit.feedback;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.feedback.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;

import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.FeedbackMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FeedbackControllerTest {

    @InjectMocks
    private FeedbackController feedbackController;

    @Mock
    private FeedbackService feedbackService;

    @Spy
    private Model model;

    @Spy
    private ModelMapper modelMapper;

    private Feedback feedback;
    private List<Feedback> feedbacks;
    private FeedbackDto feedbackDto;
    private List<FeedbackDto> feedbackDtos;

    @BeforeEach
    void setUp() {
        feedback = getMockedFeedback1();
        feedbacks = getMockedFeedbacks();
        feedbackDto = getMockedFeedbackDto1();
        feedbackDtos = getMockedFeedbackDtos();
    }

    @Test
    void findAllPage_test() {
        given(feedbackService.findAll()).willReturn(feedbackDtos);
        given(feedbackService.convertToEntities(feedbackDtos)).willReturn(feedbacks);
        given(model.getAttribute(FEEDBACKS_ATTRIBUTE)).willReturn(feedbacks);
        String viewName = feedbackController.findAllPage(model);
        then(viewName).isEqualTo(FEEDBACKS_VIEW);
        then(model.getAttribute(FEEDBACKS_ATTRIBUTE)).isEqualTo(feedbacks);
    }

    @Test
    void findByIdPage_validId_test() {
        given(feedbackService.findEntityById(VALID_ID)).willReturn(feedback);
        given(model.getAttribute(FEEDBACK_ATTRIBUTE)).willReturn(feedback);
        String viewName = feedbackController.findByIdPage(model, VALID_ID);
        then(viewName).isEqualTo(FEEDBACK_DETAILS_VIEW);
        then(model.getAttribute(FEEDBACK_ATTRIBUTE)).isEqualTo(feedback);
    }

    @Test
    void findByIdPage_invalidId_test() {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        given(feedbackService.findEntityById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> feedbackController.findByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void getSavePage_negativeId_test() {
        given(model.getAttribute("id")).willReturn(-1);
        given(model.getAttribute(FEEDBACK_DTO_ATTRIBUTE)).willReturn(new FeedbackDto());
        String viewName = feedbackController.getSavePage(model, -1);
        then(viewName).isEqualTo(SAVE_FEEDBACK_VIEW);
        then(model.getAttribute("id")).isEqualTo(-1);
        then(model.getAttribute(FEEDBACK_DTO_ATTRIBUTE)).isEqualTo(new FeedbackDto());
    }

    @Test
    void getSavePage_validId_test() {
        given(feedbackService.findById(VALID_ID)).willReturn(feedbackDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute(FEEDBACK_DTO_ATTRIBUTE)).willReturn(feedbackDto);
        String viewName = feedbackController.getSavePage(model, VALID_ID);
        then(viewName).isEqualTo(SAVE_FEEDBACK_VIEW);
        then(model.getAttribute("id")).isEqualTo(VALID_ID);
        then(model.getAttribute(FEEDBACK_DTO_ATTRIBUTE)).isEqualTo(feedbackDto);
    }

    @Test
    void getSavePage_invalidId_test() {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        given(feedbackService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> feedbackController.getSavePage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void save_negativeId_test() {
        String viewName = feedbackController.save(feedbackDto, -1);
        then(viewName).isEqualTo(REDIRECT_FEEDBACKS_VIEW);
        verify(feedbackService).save(feedbackDto);
    }

    @Test
    void save_validId_test() {
        String viewName = feedbackController.save(feedbackDto, VALID_ID);
        then(viewName).isEqualTo(REDIRECT_FEEDBACKS_VIEW);
        verify(feedbackService).updateById(feedbackDto, VALID_ID);
    }

    @Test
    void save_invalidId_test() {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        given(feedbackService.updateById(feedbackDto, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> feedbackController.save(feedbackDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void deleteById_validId_test() {
        String viewName = feedbackController.deleteById(VALID_ID);
        then(viewName).isEqualTo(REDIRECT_FEEDBACKS_VIEW);
    }

    @Test
    void deleteById_invalidId_test() {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(feedbackService).deleteById(INVALID_ID);
        thenThrownBy(() -> feedbackController.deleteById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
