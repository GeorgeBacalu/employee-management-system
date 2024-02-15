package com.project.ems.integration.experience;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.ExperienceDto;
import com.project.ems.experience.ExperienceRestController;
import com.project.ems.experience.ExperienceService;
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
import static com.project.ems.mock.ExperienceMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExperienceRestController.class)
class ExperienceRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExperienceService experienceService;

    private ExperienceDto experienceDto1;
    private ExperienceDto experienceDto2;
    private List<ExperienceDto> experienceDtos;

    @BeforeEach
    void setUp() {
        experienceDto1 = getMockedExperienceDto1();
        experienceDto2 = getMockedExperienceDto2();
        experienceDtos = getMockedExperienceDtos();
    }

    @Test
    void findAll_test() throws Exception {
        given(experienceService.findAll()).willReturn(experienceDtos);
        ResultActions actions = mockMvc.perform(get(API_EXPERIENCES)).andExpect(status().isOk());
        for (int i = 0; i < experienceDtos.size(); ++i) {
            assertExperienceDto(actions, "$[" + i + "]", experienceDtos.get(i));
        }
        List<ExperienceDto> result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        then(result).isEqualTo(experienceDtos);
    }

    @Test
    void findById_validId_test() throws Exception {
        given(experienceService.findById(VALID_ID)).willReturn(experienceDto1);
        ResultActions actions = mockMvc.perform(get(API_EXPERIENCES + "/{id}", VALID_ID)).andExpect(status().isOk());
        assertExperienceDtoJson(actions, experienceDto1);
        ExperienceDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), ExperienceDto.class);
        then(result).isEqualTo(experienceDto1);
    }

    @Test
    void findById_invalidId_test() throws Exception {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(API_EXPERIENCES + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_test() throws Exception {
        given(experienceService.save(any(ExperienceDto.class))).willReturn(experienceDto1);
        ResultActions actions = mockMvc.perform(post(API_EXPERIENCES)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(experienceDto1)))
              .andExpect(status().isCreated());
        assertExperienceDtoJson(actions, experienceDto1);
        ExperienceDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), ExperienceDto.class);
        then(result).isEqualTo(experienceDto1);
    }

    @Test
    void updateById_validId_test() throws Exception {
        ExperienceDto updatedExperienceDto = experienceDto2;
        updatedExperienceDto.setId(VALID_ID);
        given(experienceService.updateById(experienceDto2, VALID_ID)).willReturn(updatedExperienceDto);
        ResultActions actions = mockMvc.perform(put(API_EXPERIENCES + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(experienceDto2)))
              .andExpect(status().isOk());
        assertExperienceDtoJson(actions, updatedExperienceDto);
        ExperienceDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), ExperienceDto.class);
        then(result).isEqualTo(updatedExperienceDto);
    }

    @Test
    void updateById_invalidId_test() throws Exception {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.updateById(experienceDto2, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(put(API_EXPERIENCES + "/{id}", INVALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(experienceDto2)))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void deleteById_validId_test() throws Exception {
        mockMvc.perform(delete(API_EXPERIENCES + "/{id}", VALID_ID)).andExpect(status().isNoContent()).andReturn();
        verify(experienceService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_invalidId_test() throws Exception {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(experienceService).deleteById(INVALID_ID);
        mockMvc.perform(delete(API_EXPERIENCES + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    private void assertExperienceDto(ResultActions actions, String prefix, ExperienceDto experienceDto) throws Exception {
        actions.andExpect(jsonPath(prefix + ".id").value(experienceDto.getId()))
              .andExpect(jsonPath(prefix + ".title").value(experienceDto.getTitle()))
              .andExpect(jsonPath(prefix + ".organization").value(experienceDto.getOrganization()))
              .andExpect(jsonPath(prefix + ".description").value(experienceDto.getDescription()))
              .andExpect(jsonPath(prefix + ".type").value(experienceDto.getType().name()))
              .andExpect(jsonPath(prefix + ".startedAt").value(experienceDto.getStartedAt().toString()))
              .andExpect(jsonPath(prefix + ".finishedAt").value(experienceDto.getFinishedAt().toString()));
    }

    private void assertExperienceDtoJson(ResultActions actions, ExperienceDto experienceDto) throws Exception {
        actions.andExpect(jsonPath("$.id").value(experienceDto.getId()))
              .andExpect(jsonPath("$.title").value(experienceDto.getTitle()))
              .andExpect(jsonPath("$.organization").value(experienceDto.getOrganization()))
              .andExpect(jsonPath("$.description").value(experienceDto.getDescription()))
              .andExpect(jsonPath("$.type").value(experienceDto.getType().name()))
              .andExpect(jsonPath("$.startedAt").value(experienceDto.getStartedAt().toString()))
              .andExpect(jsonPath("$.finishedAt").value(experienceDto.getFinishedAt().toString()));
    }
}
