package com.project.ems.integration.trainer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.trainer.TrainerDto;
import com.project.ems.trainer.TrainerRestController;
import com.project.ems.trainer.TrainerService;
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
import static com.project.ems.mock.TrainerMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrainerRestController.class)
class TrainerRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TrainerService trainerService;

    private TrainerDto trainerDto1;
    private TrainerDto trainerDto2;
    private List<TrainerDto> trainerDtos;

    @BeforeEach
    void setUp() {
        trainerDto1 = getMockedTrainerDto1();
        trainerDto2 = getMockedTrainerDto2();
        trainerDtos = getMockedTrainerDtos();
    }

    @Test
    void findAll_test() throws Exception {
        given(trainerService.findAll()).willReturn(trainerDtos);
        ResultActions actions = mockMvc.perform(get(API_TRAINERS)).andExpect(status().isOk());
        for (int i = 0; i < trainerDtos.size(); ++i) {
            assertTrainerDto(actions, "$[" + i + "]", trainerDtos.get(i));
        }
        List<TrainerDto> result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        then(result).isEqualTo(trainerDtos);
    }

    @Test
    void findById_validId_test() throws Exception {
        given(trainerService.findById(VALID_ID)).willReturn(trainerDto1);
        ResultActions actions = mockMvc.perform(get(API_TRAINERS + "/{id}", VALID_ID)).andExpect(status().isOk());
        assertTrainerDtoJson(actions, trainerDto1);
        TrainerDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), TrainerDto.class);
        then(result).isEqualTo(trainerDto1);
    }

    @Test
    void findById_invalidId_test() throws Exception {
        String message = String.format(TRAINER_NOT_FOUND, INVALID_ID);
        given(trainerService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(API_TRAINERS + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_test() throws Exception {
        given(trainerService.save(any(TrainerDto.class))).willReturn(trainerDto1);
        ResultActions actions = mockMvc.perform(post(API_TRAINERS)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(trainerDto1)))
              .andExpect(status().isCreated());
        assertTrainerDtoJson(actions, trainerDto1);
        TrainerDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), TrainerDto.class);
        then(result).isEqualTo(trainerDto1);
    }

    @Test
    void updateById_validId_test() throws Exception {
        TrainerDto updatedTrainerDto = trainerDto2;
        updatedTrainerDto.setId(VALID_ID);
        given(trainerService.updateById(trainerDto2, VALID_ID)).willReturn(updatedTrainerDto);
        ResultActions actions = mockMvc.perform(put(API_TRAINERS + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(trainerDto2)))
              .andExpect(status().isOk());
        assertTrainerDtoJson(actions, updatedTrainerDto);
        TrainerDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), TrainerDto.class);
        then(result).isEqualTo(updatedTrainerDto);
    }

    @Test
    void updateById_invalidId_test() throws Exception {
        String message = String.format(TRAINER_NOT_FOUND, INVALID_ID);
        given(trainerService.updateById(trainerDto2, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(put(API_TRAINERS + "/{id}", INVALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(trainerDto2)))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void disableById_validId_test() throws Exception {
        TrainerDto disabledTrainerDto = trainerDto1;
        disabledTrainerDto.setIsActive(false);
        given(trainerService.disableById(VALID_ID)).willReturn(disabledTrainerDto);
        ResultActions actions = mockMvc.perform(delete(API_TRAINERS + "/{id}", VALID_ID)).andExpect(status().isOk());
        assertTrainerDtoJson(actions, disabledTrainerDto);
        TrainerDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), TrainerDto.class);
        then(result).isEqualTo(disabledTrainerDto);
    }

    @Test
    void disableById_invalidId_test() throws Exception {
        String message = String.format(TRAINER_NOT_FOUND, INVALID_ID);
        given(trainerService.disableById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(delete(API_TRAINERS + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    private void assertTrainerDto(ResultActions actions, String prefix, TrainerDto trainerDto) throws Exception {
        actions.andExpect(jsonPath(prefix + ".id").value(trainerDto.getId()))
              .andExpect(jsonPath(prefix + ".name").value(trainerDto.getName()))
              .andExpect(jsonPath(prefix + ".email").value(trainerDto.getEmail()))
              .andExpect(jsonPath(prefix + ".password").value(trainerDto.getPassword()))
              .andExpect(jsonPath(prefix + ".mobile").value(trainerDto.getMobile()))
              .andExpect(jsonPath(prefix + ".address").value(trainerDto.getAddress()))
              .andExpect(jsonPath(prefix + ".birthday").value(trainerDto.getBirthday().toString()))
              .andExpect(jsonPath(prefix + ".roleId").value(trainerDto.getRoleId()))
              .andExpect(jsonPath(prefix + ".isActive").value(trainerDto.getIsActive()))
              .andExpect(jsonPath(prefix + ".employmentType").value(trainerDto.getEmploymentType().name()))
              .andExpect(jsonPath(prefix + ".position").value(trainerDto.getPosition().name()))
              .andExpect(jsonPath(prefix + ".grade").value(trainerDto.getGrade().name()))
              .andExpect(jsonPath(prefix + ".salary").value(trainerDto.getSalary()))
              .andExpect(jsonPath(prefix + ".hiredAt").value(trainerDto.getHiredAt().toString()))
              .andExpect(jsonPath(prefix + ".supervisingTrainerId").value(trainerDto.getSupervisingTrainerId()))
              .andExpect(jsonPath(prefix + ".nrTrainees").value(trainerDto.getNrTrainees()))
              .andExpect(jsonPath(prefix + ".maxTrainees").value(trainerDto.getMaxTrainees()));
        for (int i = 0; i < trainerDto.getAuthoritiesIds().size(); ++i) {
            actions.andExpect(jsonPath(prefix + ".authoritiesIds[" + i + "]").value(trainerDto.getAuthoritiesIds().get(i)));
        }
        for (int i = 0; i < trainerDto.getExperiencesIds().size(); ++i) {
            actions.andExpect(jsonPath(prefix + ".experiencesIds[" + i + "]").value(trainerDto.getExperiencesIds().get(i)));
        }
        for (int i = 0; i < trainerDto.getStudiesIds().size(); ++i) {
            actions.andExpect(jsonPath(prefix + ".studiesIds[" + i + "]").value(trainerDto.getStudiesIds().get(i)));
        }
    }

    private void assertTrainerDtoJson(ResultActions actions, TrainerDto trainerDto) throws Exception {
        actions.andExpect(jsonPath("$.id").value(trainerDto.getId()))
              .andExpect(jsonPath("$.name").value(trainerDto.getName()))
              .andExpect(jsonPath("$.email").value(trainerDto.getEmail()))
              .andExpect(jsonPath("$.password").value(trainerDto.getPassword()))
              .andExpect(jsonPath("$.mobile").value(trainerDto.getMobile()))
              .andExpect(jsonPath("$.address").value(trainerDto.getAddress()))
              .andExpect(jsonPath("$.birthday").value(trainerDto.getBirthday().toString()))
              .andExpect(jsonPath("$.roleId").value(trainerDto.getRoleId()))
              .andExpect(jsonPath("$.isActive").value(trainerDto.getIsActive()))
              .andExpect(jsonPath("$.employmentType").value(trainerDto.getEmploymentType().name()))
              .andExpect(jsonPath("$.position").value(trainerDto.getPosition().name()))
              .andExpect(jsonPath("$.grade").value(trainerDto.getGrade().name()))
              .andExpect(jsonPath("$.salary").value(trainerDto.getSalary()))
              .andExpect(jsonPath("$.hiredAt").value(trainerDto.getHiredAt().toString()))
              .andExpect(jsonPath("$.supervisingTrainerId").value(trainerDto.getSupervisingTrainerId()))
              .andExpect(jsonPath("$.nrTrainees").value(trainerDto.getNrTrainees()))
              .andExpect(jsonPath("$.maxTrainees").value(trainerDto.getMaxTrainees()));
        for (int i = 0; i < trainerDto.getAuthoritiesIds().size(); ++i) {
            actions.andExpect(jsonPath("$.authoritiesIds[" + i + "]").value(trainerDto.getAuthoritiesIds().get(i)));
        }
        for (int i = 0; i < trainerDto.getExperiencesIds().size(); ++i) {
            actions.andExpect(jsonPath("$.experiencesIds[" + i + "]").value(trainerDto.getExperiencesIds().get(i)));
        }
        for (int i = 0; i < trainerDto.getStudiesIds().size(); ++i) {
            actions.andExpect(jsonPath("$.studiesIds[" + i + "]").value(trainerDto.getStudiesIds().get(i)));
        }
    }
}
