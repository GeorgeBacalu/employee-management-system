package com.project.ems.unit.feedback;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.feedback.*;
import com.project.ems.wrapper.SearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.FeedbackMock.*;
import static com.project.ems.util.PageUtil.*;
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
    private RedirectAttributes redirectAttributes;

    private Feedback feedback;
    private List<Feedback> feedbacksPage1;
    private FeedbackDto feedbackDto;
    private List<FeedbackDto> feedbackDtosPage1;

    @BeforeEach
    void setUp() {
        feedback = getMockedFeedback1();
        feedbacksPage1 = getMockedFeedbacksPage1();
        feedbackDto = getMockedFeedbackDto1();
        feedbackDtosPage1 = getMockedFeedbackDtosPage1();
    }

    @Test
    void findAllPage_test() {
        Page<FeedbackDto> feedbackDtosPage = new PageImpl<>(feedbackDtosPage1);
        int page = PAGEABLE.getPageNumber();
        int size = PAGEABLE.getPageSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        long nrFeedbacks = feedbackDtosPage.getTotalElements();
        int nrPages = feedbackDtosPage.getTotalPages();
        SearchRequest searchRequest = new SearchRequest(0, size, field + "," + direction, FEEDBACK_FILTER_KEY);
        given(feedbackService.findAllByKey(PAGEABLE, FEEDBACK_FILTER_KEY)).willReturn(feedbackDtosPage);
        given(model.getAttribute(FEEDBACKS_ATTRIBUTE)).willReturn(feedbacksPage1);
        given(model.getAttribute("nrFeedbacks")).willReturn(nrFeedbacks);
        given(model.getAttribute("nrPages")).willReturn(nrPages);
        given(model.getAttribute("page")).willReturn(page);
        given(model.getAttribute("size")).willReturn(size);
        given(model.getAttribute("field")).willReturn(field);
        given(model.getAttribute("direction")).willReturn(direction);
        given(model.getAttribute("key")).willReturn(FEEDBACK_FILTER_KEY);
        given(model.getAttribute("pageStartIndex")).willReturn(getPageStartIndex(page, size));
        given(model.getAttribute("pageEndIndex")).willReturn(getPageEndIndex(page, size, nrFeedbacks));
        given(model.getAttribute("pageNavigationStartIndex")).willReturn(getPageNavigationStartIndex(page, nrPages));
        given(model.getAttribute("pageNavigationEndIndex")).willReturn(getPageNavigationEndIndex(page, nrPages));
        given(model.getAttribute("searchRequest")).willReturn(searchRequest);
        String viewName = feedbackController.findAllPage(model, PAGEABLE, FEEDBACK_FILTER_KEY);
        then(viewName).isEqualTo(FEEDBACKS_VIEW);
        then(model.getAttribute(FEEDBACKS_ATTRIBUTE)).isEqualTo(feedbacksPage1);
        then(model.getAttribute("nrFeedbacks")).isEqualTo(nrFeedbacks);
        then(model.getAttribute("nrPages")).isEqualTo(nrPages);
        then(model.getAttribute("page")).isEqualTo(page);
        then(model.getAttribute("size")).isEqualTo(size);
        then(model.getAttribute("field")).isEqualTo(field);
        then(model.getAttribute("direction")).isEqualTo(direction);
        then(model.getAttribute("key")).isEqualTo(FEEDBACK_FILTER_KEY);
        then(model.getAttribute("pageStartIndex")).isEqualTo(getPageStartIndex(page, size));
        then(model.getAttribute("pageEndIndex")).isEqualTo(getPageEndIndex(page, size, nrFeedbacks));
        then(model.getAttribute("pageNavigationStartIndex")).isEqualTo(getPageNavigationStartIndex(page, nrPages));
        then(model.getAttribute("pageNavigationEndIndex")).isEqualTo(getPageNavigationEndIndex(page, nrPages));
        then(model.getAttribute("searchRequest")).isEqualTo(searchRequest);
    }

    @Test
    void findAllByKey_test() {
        Page<FeedbackDto> feedbackDtosPage = new PageImpl<>(feedbackDtosPage1);
        int page = feedbackDtosPage.getNumber();
        int size = feedbackDtosPage.getSize();
        String sort = getSortField(PAGEABLE) + ',' + getSortDirection(PAGEABLE);
        given(redirectAttributes.getAttribute("page")).willReturn(page);
        given(redirectAttributes.getAttribute("size")).willReturn(size);
        given(redirectAttributes.getAttribute("sort")).willReturn(sort);
        given(redirectAttributes.getAttribute("key")).willReturn(FEEDBACK_FILTER_KEY);
        String viewName = feedbackController.findAllByKey(new SearchRequest(page, size, sort, FEEDBACK_FILTER_KEY), redirectAttributes);
        then(viewName).isEqualTo(REDIRECT_FEEDBACKS_VIEW);
        then(redirectAttributes.getAttribute("page")).isEqualTo(page);
        then(redirectAttributes.getAttribute("size")).isEqualTo(size);
        then(redirectAttributes.getAttribute("sort")).isEqualTo(sort);
        then(redirectAttributes.getAttribute("key")).isEqualTo(FEEDBACK_FILTER_KEY);
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
        Page<FeedbackDto> feedbackDtosPage = new PageImpl<>(feedbackDtosPage1);
        int page = feedbackDtosPage.getNumber();
        int size = feedbackDtosPage.getSize();
        String sort = getSortField(PAGEABLE) + ',' + getSortDirection(PAGEABLE);
        given(feedbackService.findAllByKey(PAGEABLE, FEEDBACK_FILTER_KEY)).willReturn(feedbackDtosPage);
        given(redirectAttributes.getAttribute("page")).willReturn(page);
        given(redirectAttributes.getAttribute("size")).willReturn(size);
        given(redirectAttributes.getAttribute("sort")).willReturn(sort);
        given(redirectAttributes.getAttribute("key")).willReturn(FEEDBACK_FILTER_KEY);
        String viewName = feedbackController.deleteById(VALID_ID, redirectAttributes, PAGEABLE, FEEDBACK_FILTER_KEY);
        verify(feedbackService).deleteById(VALID_ID);
        then(viewName).isEqualTo(REDIRECT_FEEDBACKS_VIEW);
        then(redirectAttributes.getAttribute("page")).isEqualTo(page);
        then(redirectAttributes.getAttribute("size")).isEqualTo(size);
        then(redirectAttributes.getAttribute("sort")).isEqualTo(sort);
        then(redirectAttributes.getAttribute("key")).isEqualTo(FEEDBACK_FILTER_KEY);
    }

    @Test
    void deleteById_invalidId_test() {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(feedbackService).deleteById(INVALID_ID);
        thenThrownBy(() -> feedbackController.deleteById(INVALID_ID, redirectAttributes, PAGEABLE, FEEDBACK_FILTER_KEY))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
