package com.project.ems.integration.trainer;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.trainer.*;
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
import java.util.stream.Collectors;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.TrainerMock.*;
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

@WebMvcTest(TrainerController.class)
class TrainerControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainerService trainerService;

    private Trainer trainer;
    private List<Trainer> activeTrainersPage1;
    private TrainerDto trainerDto;
    private List<TrainerDto> activeTrainerDtosPage1;

    @BeforeEach
    void setUp() {
        trainer = getMockedTrainer1();
        activeTrainersPage1 = getMockedTrainersPage1();
        trainerDto = getMockedTrainerDto1();
        activeTrainerDtosPage1 = getMockedTrainerDtosPage1();
    }

    @Test
    void findAllActivePage_test() throws Exception {
        Page<TrainerDto> trainerDtosPage = new PageImpl<>(activeTrainerDtosPage1);
        given(trainerService.findAllActiveByKey(PAGEABLE, USER_FILTER_KEY)).willReturn(trainerDtosPage);
        given(trainerService.convertToEntities(trainerDtosPage.getContent())).willReturn(activeTrainersPage1);
        int page = PAGEABLE.getPageNumber();
        int size = PAGEABLE.getPageSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        long nrTrainers = trainerDtosPage.getTotalElements();
        int nrPages = trainerDtosPage.getTotalPages();
        mockMvc.perform(get(TRAINERS + PAGINATION, page, size, field, direction, USER_FILTER_KEY).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(TRAINERS_VIEW))
              .andExpect(model().attribute(TRAINERS_ATTRIBUTE, activeTrainersPage1))
              .andExpect(model().attribute("nrTrainers", nrTrainers))
              .andExpect(model().attribute("nrPages", nrPages))
              .andExpect(model().attribute("page", page))
              .andExpect(model().attribute("size", size))
              .andExpect(model().attribute("field", field))
              .andExpect(model().attribute("direction", direction))
              .andExpect(model().attribute("key", USER_FILTER_KEY))
              .andExpect(model().attribute("pageStartIndex", getPageStartIndex(page, size)))
              .andExpect(model().attribute("pageEndIndex", getPageEndIndex(page, size, nrTrainers)))
              .andExpect(model().attribute("pageNavigationStartIndex", getPageNavigationStartIndex(page, nrPages)))
              .andExpect(model().attribute("pageNavigationEndIndex", getPageNavigationEndIndex(page, nrPages)))
              .andExpect(model().attribute("searchRequest", new SearchRequest(page, size, field + ',' + direction, USER_FILTER_KEY)));
    }

    @Test
    void findAllActiveByKey_test() throws Exception {
        Page<TrainerDto> trainerDtosPage = new PageImpl<>(activeTrainerDtosPage1);
        int page = trainerDtosPage.getNumber();
        int size = trainerDtosPage.getSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        mockMvc.perform(post(TRAINERS + "/search" + PAGINATION, page, size, field, direction, USER_FILTER_KEY).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_TRAINERS_VIEW))
              .andExpect(redirectedUrlPattern(TRAINERS + "?page=*&size=*&sort=*&key=*"));
    }

    @Test
    void findByIdPage_validId_test() throws Exception {
        given(trainerService.findEntityById(VALID_ID)).willReturn(trainer);
        mockMvc.perform(get(TRAINERS + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(TRAINER_DETAILS_VIEW))
              .andExpect(model().attribute(TRAINER_ATTRIBUTE, trainer));
    }

    @Test
    void findByIdPage_invalidId_test() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(trainerService.findEntityById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(TRAINERS + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void getSavePage_negativeId_test() throws Exception {
        mockMvc.perform(get(TRAINERS + "/save/{id}", -1).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_TRAINER_VIEW))
              .andExpect(model().attribute("id", -1))
              .andExpect(model().attribute(TRAINER_DTO_ATTRIBUTE, new TrainerDto()));
    }

    @Test
    void getSavePage_validId_test() throws Exception {
        given(trainerService.findById(VALID_ID)).willReturn(trainerDto);
        mockMvc.perform(get(TRAINERS + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_TRAINER_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute(TRAINER_DTO_ATTRIBUTE, trainerDto));
    }

    @Test
    void getSavePage_invalidId_test() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(trainerService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(TRAINERS + "/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_negativeId_test() throws Exception {
        mockMvc.perform(post(TRAINERS + "/save/{id}", -1).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(trainerDto)))
              .andExpect(status().isFound())
              .andExpect(redirectedUrl(TRAINERS));
        verify(trainerService).save(any(TrainerDto.class));
    }

    @Test
    void save_validId_test() throws Exception {
        mockMvc.perform(post(TRAINERS + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(trainerDto)))
              .andExpect(status().isFound())
              .andExpect(redirectedUrl(TRAINERS));
        verify(trainerService).updateById(any(TrainerDto.class), anyInt());
    }

    @Test
    void save_invalidId_test() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(trainerService.updateById(any(TrainerDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(TRAINERS + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(trainerDto)))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void disableById_validId_test() throws Exception {
        Page<TrainerDto> trainerDtosPage = new PageImpl<>(activeTrainerDtosPage1);
        given(trainerService.findAllActiveByKey(PAGEABLE, USER_FILTER_KEY)).willReturn(trainerDtosPage);
        int page = trainerDtosPage.getNumber();
        int size = trainerDtosPage.getSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        mockMvc.perform(get(TRAINERS + "/delete/{id}" + PAGINATION, VALID_ID, page, size, field, direction, USER_FILTER_KEY).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_TRAINERS_VIEW))
              .andExpect(redirectedUrlPattern(TRAINERS + "?page=*&size=*&sort=*&key=*"));
    }

    @Test
    void disableById_invalidId_test() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(trainerService).disableById(INVALID_ID);
        mockMvc.perform(get(TRAINERS + "/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    private MultiValueMap<String, String> convertToMultiValueMap(TrainerDto trainerDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", trainerDto.getName());
        params.add("email", trainerDto.getEmail());
        params.add("password", trainerDto.getPassword());
        params.add("mobile", trainerDto.getMobile());
        params.add("address", trainerDto.getAddress());
        params.add("birthday", trainerDto.getBirthday().toString());
        params.add("roleId", trainerDto.getRoleId().toString());
        params.add("authoritiesIds", trainerDto.getAuthoritiesIds().stream().map(String::valueOf).collect(Collectors.joining(", ")));
        params.add("isActive", trainerDto.getIsActive().toString());
        params.add("employmentType", trainerDto.getEmploymentType().name());
        params.add("position", trainerDto.getPosition().name());
        params.add("grade", trainerDto.getGrade().name());
        params.add("salary", trainerDto.getSalary().toString());
        params.add("hiredAt", trainerDto.getHiredAt().toString());
        params.add("experiencesIds", trainerDto.getExperiencesIds().stream().map(String::valueOf).collect(Collectors.joining(", ")));
        params.add("studiesIds", trainerDto.getStudiesIds().stream().map(String::valueOf).collect(Collectors.joining(", ")));
        params.add("supervisingTrainerId", trainerDto.getSupervisingTrainerId() != null ? trainerDto.getSupervisingTrainerId().toString() : "");
        params.add("nrTrainees", trainerDto.getNrTrainees().toString());
        params.add("maxTrainees", trainerDto.getMaxTrainees().toString());
        return params;
    }
}
