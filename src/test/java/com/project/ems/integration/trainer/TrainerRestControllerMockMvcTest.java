package com.project.ems.integration.trainer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.trainer.TrainerDto;
import com.project.ems.trainer.TrainerRestController;
import com.project.ems.trainer.TrainerService;
import com.project.ems.wrapper.PageWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.TrainerMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
    private List<TrainerDto> activeTrainerDtos;
    private List<TrainerDto> trainerDtosListPage1;
    private List<TrainerDto> trainerDtosListPage2;
    private List<TrainerDto> trainerDtosListPage3;

    @BeforeEach
    void setUp() {
        trainerDto1 = getMockedTrainerDto1();
        trainerDto2 = getMockedTrainerDto2();
        trainerDtos = getMockedTrainerDtos();
        activeTrainerDtos = getMockedActiveTrainerDtos();
        trainerDtosListPage1 = getMockedTrainerDtosPage1();
        trainerDtosListPage2 = getMockedTrainerDtosPage2();
        trainerDtosListPage3 = getMockedTrainerDtosPage3();
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
    void findAllActive_test() throws Exception {
        given(trainerService.findAllActive()).willReturn(activeTrainerDtos);
        ResultActions actions = mockMvc.perform(get(API_TRAINERS + "/active")).andExpect(status().isOk());
        for (int i = 0; i < activeTrainerDtos.size(); ++i) {
            assertTrainerDto(actions, "$[" + i + "]", activeTrainerDtos.get(i));
        }
        List<TrainerDto> result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        then(result).isEqualTo(activeTrainerDtos);
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

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void findAllByKey_test(int page, int size, String sortField, String sortDirection, String key, Page<TrainerDto> expectedPage) throws Exception {
        given(trainerService.findAllByKey(any(Pageable.class), eq(key))).willReturn(expectedPage);
        ResultActions actions = mockMvc.perform(get(API_TRAINERS + API_PAGINATION, page, size, sortField, sortDirection, key)).andExpect(status().isOk());
        List<TrainerDto> expectedPageContent = expectedPage.getContent();
        for (int i = 0; i < expectedPageContent.size(); ++i) {
            assertTrainerDto(actions, "$.content[" + i + "]", expectedPageContent.get(i));
        }
        PageWrapper<TrainerDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        then(response.getContent()).isEqualTo(expectedPageContent);
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void findAllActiveByKey_test(int page, int size, String sortField, String sortDirection, String key, Page<TrainerDto> expectedPage) throws Exception {
        given(trainerService.findAllActiveByKey(any(Pageable.class), eq(key))).willReturn(expectedPage);
        ResultActions actions = mockMvc.perform(get(API_TRAINERS + API_ACTIVE_PAGINATION, page, size, sortField, sortDirection, key)).andExpect(status().isOk());
        List<TrainerDto> expectedPageActiveContent = expectedPage.getContent();
        for (int i = 0; i < expectedPageActiveContent.size(); ++i) {
            assertTrainerDto(actions, "$.content[" + i + "]", expectedPageActiveContent.get(i));
        }
        PageWrapper<TrainerDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        then(response.getContent()).isEqualTo(expectedPageActiveContent);
    }

    private Stream<Arguments> paginationArguments() {
        Page<TrainerDto> trainerDtosPage1 = new PageImpl<>(trainerDtosListPage1);
        Page<TrainerDto> trainerDtosPage2 = new PageImpl<>(trainerDtosListPage2);
        Page<TrainerDto> trainerDtosPage3 = new PageImpl<>(trainerDtosListPage3);
        Page<TrainerDto> emptyPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", "asc", TRAINER_FILTER_KEY, trainerDtosPage1),
                         Arguments.of(1, 2, "id", "asc", TRAINER_FILTER_KEY, trainerDtosPage2),
                         Arguments.of(2, 2, "id", "asc", TRAINER_FILTER_KEY, emptyPage),
                         Arguments.of(0, 2, "id", "asc", "", trainerDtosPage1),
                         Arguments.of(1, 2, "id", "asc", "", trainerDtosPage2),
                         Arguments.of(2, 2, "id", "asc", "", trainerDtosPage3));
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
