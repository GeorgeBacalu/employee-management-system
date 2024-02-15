package com.project.ems.integration.feedback;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.feedback.FeedbackDto;
import com.project.ems.feedback.FeedbackRestController;
import com.project.ems.feedback.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Objects;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.FeedbackMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FeedbackRestController.class)
class FeedbackRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FeedbackService feedbackService;

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
        given(feedbackService.findAll()).willReturn(feedbackDtos);
        ResultActions actions = mockMvc.perform(get(API_FEEDBACKS)).andExpect(status().isOk());
        for (int i = 0; i < feedbackDtos.size(); ++i) {
            assertFeedbackDto(actions, "$[" + i + "]", feedbackDtos.get(i));
        }
        List<FeedbackDto> result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        then(result).isEqualTo(feedbackDtos);
    }

    @Test
    void findById_validId_test() throws Exception {
        given(feedbackService.findById(VALID_ID)).willReturn(feedbackDto1);
        ResultActions actions = mockMvc.perform(get(API_FEEDBACKS + "/{id}", VALID_ID)).andExpect(status().isOk());
        assertFeedbackDtoJson(actions, feedbackDto1);
        FeedbackDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), FeedbackDto.class);
        then(result).isEqualTo(feedbackDto1);
    }

    @Test
    void findById_invalidId_test() throws Exception {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        given(feedbackService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(API_FEEDBACKS + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_test() throws Exception {
        given(feedbackService.save(any(FeedbackDto.class))).willReturn(feedbackDto1);
        ResultActions actions = mockMvc.perform(post(API_FEEDBACKS)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(feedbackDto1)))
              .andExpect(status().isCreated());
        assertFeedbackDtoJson(actions, feedbackDto1);
        FeedbackDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), FeedbackDto.class);
        then(result).isEqualTo(feedbackDto1);
    }

    @Test
    void updateById_validId_test() throws Exception {
        FeedbackDto updatedFeedbackDto = feedbackDto2;
        updatedFeedbackDto.setId(VALID_ID);
        given(feedbackService.updateById(feedbackDto2, VALID_ID)).willReturn(updatedFeedbackDto);
        ResultActions actions = mockMvc.perform(put(API_FEEDBACKS + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(feedbackDto2)))
              .andExpect(status().isOk());
        assertFeedbackDtoJson(actions, updatedFeedbackDto);
        FeedbackDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), FeedbackDto.class);
        then(result).isEqualTo(updatedFeedbackDto);
    }

    @Test
    void updateById_invalidId_test() throws Exception {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        given(feedbackService.updateById(feedbackDto2, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(put(API_FEEDBACKS + "/{id}", INVALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(feedbackDto2)))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void deleteById_validId_test() throws Exception {
        mockMvc.perform(delete(API_FEEDBACKS + "/{id}", VALID_ID)).andExpect(status().isNoContent()).andReturn();
        verify(feedbackService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_invalidId_test() throws Exception {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(feedbackService).deleteById(INVALID_ID);
        mockMvc.perform(delete(API_FEEDBACKS + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    private void assertFeedbackDto(ResultActions actions, String prefix, FeedbackDto feedbackDto) throws Exception {
        actions.andExpect(jsonPath(prefix + ".id").value(feedbackDto.getId()))
              .andExpect(jsonPath(prefix + ".description").value(feedbackDto.getDescription()))
              .andExpect(jsonPath(prefix + ".type").value(feedbackDto.getType().name()))
              .andExpect(jsonPath(prefix + ".sentAt").value(feedbackDto.getSentAt() + ":00"))
              .andExpect(jsonPath(prefix + ".userId").value(feedbackDto.getUserId()));
    }

    private void assertFeedbackDtoJson(ResultActions actions, FeedbackDto feedbackDto) throws Exception {
        actions.andExpect(jsonPath("$.id").value(feedbackDto.getId()))
              .andExpect(jsonPath("$.description").value(feedbackDto.getDescription()))
              .andExpect(jsonPath("$.type").value(feedbackDto.getType().name()))
              .andExpect(jsonPath("$.sentAt").value(feedbackDto.getSentAt() + ":00"))
              .andExpect(jsonPath("$.userId").value(feedbackDto.getUserId()));
    }
}
