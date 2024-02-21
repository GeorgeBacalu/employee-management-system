package com.project.ems.integration.feedback;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.feedback.*;
import com.project.ems.wrapper.SearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Objects;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.FeedbackMock.*;
import static com.project.ems.util.PageUtil.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeedbackController.class)
class FeedbackControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackService feedbackService;

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
    void findAllPage_test() throws Exception {
        Page<FeedbackDto> feedbackDtosPage = new PageImpl<>(feedbackDtosPage1);
        given(feedbackService.findAllByKey(PAGEABLE, FEEDBACK_FILTER_KEY)).willReturn(feedbackDtosPage);
        given(feedbackService.convertToEntities(feedbackDtosPage.getContent())).willReturn(feedbacksPage1);
        int page = PAGEABLE.getPageNumber();
        int size = PAGEABLE.getPageSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        long nrFeedbacks = feedbackDtosPage.getTotalElements();
        int nrPages = feedbackDtosPage.getTotalPages();
        mockMvc.perform(get(FEEDBACKS + PAGINATION, page, size, field, direction, FEEDBACK_FILTER_KEY).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(FEEDBACKS_VIEW))
              .andExpect(model().attribute(FEEDBACKS_ATTRIBUTE, feedbacksPage1))
              .andExpect(model().attribute("nrFeedbacks", nrFeedbacks))
              .andExpect(model().attribute("nrPages", nrPages))
              .andExpect(model().attribute("page", page))
              .andExpect(model().attribute("size", size))
              .andExpect(model().attribute("field", field))
              .andExpect(model().attribute("direction", direction))
              .andExpect(model().attribute("key", FEEDBACK_FILTER_KEY))
              .andExpect(model().attribute("pageStartIndex", getPageStartIndex(page, size)))
              .andExpect(model().attribute("pageEndIndex", getPageEndIndex(page, size, nrFeedbacks)))
              .andExpect(model().attribute("pageNavigationStartIndex", getPageNavigationStartIndex(page, nrPages)))
              .andExpect(model().attribute("pageNavigationEndIndex", getPageNavigationEndIndex(page, nrPages)))
              .andExpect(model().attribute("searchRequest", new SearchRequest(page, size, field + ',' + direction, FEEDBACK_FILTER_KEY)));
    }

    @Test
    void findAllByKey_test() throws Exception {
        Page<FeedbackDto> feedbackDtosPage = new PageImpl<>(feedbackDtosPage1);
        int page = feedbackDtosPage.getNumber();
        int size = feedbackDtosPage.getSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        mockMvc.perform(post(FEEDBACKS + "/search" + PAGINATION, page, size, field, direction, FEEDBACK_FILTER_KEY).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_FEEDBACKS_VIEW))
              .andExpect(redirectedUrlPattern(FEEDBACKS + "?page=*&size=*&sort=*&key=*"));
    }

    @Test
    void findByIdPage_validId_test() throws Exception {
        given(feedbackService.findEntityById(VALID_ID)).willReturn(feedback);
        mockMvc.perform(get(FEEDBACKS + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(FEEDBACK_DETAILS_VIEW))
              .andExpect(model().attribute(FEEDBACK_ATTRIBUTE, feedback));
    }

    @Test
    void findByIdPage_invalidId_test() throws Exception {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        given(feedbackService.findEntityById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(FEEDBACKS + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void getSavePage_negativeId_test() throws Exception {
        mockMvc.perform(get(FEEDBACKS + "/save/{id}", -1).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_FEEDBACK_VIEW))
              .andExpect(model().attribute("id", -1))
              .andExpect(model().attribute(FEEDBACK_DTO_ATTRIBUTE, new FeedbackDto()));
    }

    @Test
    void getSavePage_validId_test() throws Exception {
        given(feedbackService.findById(VALID_ID)).willReturn(feedbackDto);
        mockMvc.perform(get(FEEDBACKS + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_FEEDBACK_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute(FEEDBACK_DTO_ATTRIBUTE, feedbackDto));
    }

    @Test
    void getSavePage_invalidId_test() throws Exception {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        given(feedbackService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(FEEDBACKS + "/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_negativeId_test() throws Exception {
        mockMvc.perform(post(FEEDBACKS + "/save/{id}", -1).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(feedbackDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_FEEDBACKS_VIEW))
              .andExpect(redirectedUrl(FEEDBACKS));
        verify(feedbackService).save(any(FeedbackDto.class));
    }

    @Test
    void save_validId_test() throws Exception {
        mockMvc.perform(post(FEEDBACKS + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(feedbackDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_FEEDBACKS_VIEW))
              .andExpect(redirectedUrl(FEEDBACKS));
        verify(feedbackService).updateById(any(FeedbackDto.class), anyInt());
    }

    @Test
    void save_invalidId_test() throws Exception {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        given(feedbackService.updateById(any(FeedbackDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(FEEDBACKS + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(feedbackDto)))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void deleteById_validId_test() throws Exception {
        Page<FeedbackDto> feedbackDtosPage = new PageImpl<>(feedbackDtosPage1);
        given(feedbackService.findAllByKey(PAGEABLE, FEEDBACK_FILTER_KEY)).willReturn(feedbackDtosPage);
        int page = feedbackDtosPage.getNumber();
        int size = feedbackDtosPage.getSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        mockMvc.perform(get(FEEDBACKS + "/delete/{id}" + PAGINATION, VALID_ID, page, size, field, direction, FEEDBACK_FILTER_KEY).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_FEEDBACKS_VIEW))
              .andExpect(redirectedUrlPattern(FEEDBACKS + "?page=*&size=*&sort=*&key=*"));
    }

    @Test
    void deleteById_invalidId_test() throws Exception {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(feedbackService).deleteById(INVALID_ID);
        mockMvc.perform(get(FEEDBACKS + "/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    private MultiValueMap<String, String> convertToMultiValueMap(FeedbackDto feedbackDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("type", feedbackDto.getType().name());
        params.add("description", feedbackDto.getDescription());
        params.add("sentAt", feedbackDto.getSentAt().toString());
        params.add("userId", feedbackDto.getUserId().toString());
        return params;
    }
}
