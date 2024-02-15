package com.project.ems.integration.study;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.study.StudyDto;
import com.project.ems.study.StudyRestController;
import com.project.ems.study.StudyService;
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
import static com.project.ems.mock.StudyMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudyRestController.class)
class StudyRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudyService studyService;

    private StudyDto studyDto1;
    private StudyDto studyDto2;
    private List<StudyDto> studyDtos;

    @BeforeEach
    void setUp() {
        studyDto1 = getMockedStudyDto1();
        studyDto2 = getMockedStudyDto2();
        studyDtos = getMockedStudyDtos();
    }

    @Test
    void findAll_test() throws Exception {
        given(studyService.findAll()).willReturn(studyDtos);
        ResultActions actions = mockMvc.perform(get(API_STUDIES)).andExpect(status().isOk());
        for (int i = 0; i < studyDtos.size(); ++i) {
            assertStudyDto(actions, "$[" + i + "]", studyDtos.get(i));
        }
        List<StudyDto> result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        then(result).isEqualTo(studyDtos);
    }

    @Test
    void findById_validId_test() throws Exception {
        given(studyService.findById(VALID_ID)).willReturn(studyDto1);
        ResultActions actions = mockMvc.perform(get(API_STUDIES + "/{id}", VALID_ID)).andExpect(status().isOk());
        assertStudyDtoJson(actions, studyDto1);
        StudyDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), StudyDto.class);
        then(result).isEqualTo(studyDto1);
    }

    @Test
    void findById_invalidId_test() throws Exception {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        given(studyService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(API_STUDIES + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_test() throws Exception {
        given(studyService.save(any(StudyDto.class))).willReturn(studyDto1);
        ResultActions actions = mockMvc.perform(post(API_STUDIES)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(studyDto1)))
              .andExpect(status().isCreated());
        assertStudyDtoJson(actions, studyDto1);
        StudyDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), StudyDto.class);
        then(result).isEqualTo(studyDto1);
    }

    @Test
    void updateById_validId_test() throws Exception {
        StudyDto updatedStudyDto = studyDto2;
        updatedStudyDto.setId(VALID_ID);
        given(studyService.updateById(studyDto2, VALID_ID)).willReturn(updatedStudyDto);
        ResultActions actions = mockMvc.perform(put(API_STUDIES + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(studyDto2)))
              .andExpect(status().isOk());
        assertStudyDtoJson(actions, updatedStudyDto);
        StudyDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), StudyDto.class);
        then(result).isEqualTo(updatedStudyDto);
    }

    @Test
    void updateById_invalidId_test() throws Exception {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        given(studyService.updateById(studyDto2, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(put(API_STUDIES + "/{id}", INVALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(studyDto2)))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void deleteById_validId_test() throws Exception {
        mockMvc.perform(delete(API_STUDIES + "/{id}", VALID_ID)).andExpect(status().isNoContent()).andReturn();
        verify(studyService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_invalidId_test() throws Exception {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(studyService).deleteById(INVALID_ID);
        mockMvc.perform(delete(API_STUDIES + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    private void assertStudyDto(ResultActions actions, String prefix, StudyDto studyDto) throws Exception {
        actions.andExpect(jsonPath(prefix + ".id").value(studyDto.getId()))
              .andExpect(jsonPath(prefix + ".title").value(studyDto.getTitle()))
              .andExpect(jsonPath(prefix + ".institution").value(studyDto.getInstitution()))
              .andExpect(jsonPath(prefix + ".description").value(studyDto.getDescription()))
              .andExpect(jsonPath(prefix + ".type").value(studyDto.getType().name()))
              .andExpect(jsonPath(prefix + ".startedAt").value(studyDto.getStartedAt().toString()))
              .andExpect(jsonPath(prefix + ".finishedAt").value(studyDto.getFinishedAt().toString()));
    }

    private void assertStudyDtoJson(ResultActions actions, StudyDto studyDto) throws Exception {
        actions.andExpect(jsonPath("$.id").value(studyDto.getId()))
              .andExpect(jsonPath("$.title").value(studyDto.getTitle()))
              .andExpect(jsonPath("$.institution").value(studyDto.getInstitution()))
              .andExpect(jsonPath("$.description").value(studyDto.getDescription()))
              .andExpect(jsonPath("$.type").value(studyDto.getType().name()))
              .andExpect(jsonPath("$.startedAt").value(studyDto.getStartedAt().toString()))
              .andExpect(jsonPath("$.finishedAt").value(studyDto.getFinishedAt().toString()));
    }
}
