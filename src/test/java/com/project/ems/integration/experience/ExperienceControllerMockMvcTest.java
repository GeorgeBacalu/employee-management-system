package com.project.ems.integration.experience;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.*;
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
import static com.project.ems.mock.ExperienceMock.*;
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

@WebMvcTest(ExperienceController.class)
class ExperienceControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExperienceService experienceService;

    private Experience experience;
    private List<Experience> experiencesPage1;
    private ExperienceDto experienceDto;
    private List<ExperienceDto> experienceDtosPage1;

    @BeforeEach
    void setUp() {
        experience = getMockedExperience1();
        experiencesPage1 = getMockedExperiencesPage1();
        experienceDto = getMockedExperienceDto1();
        experienceDtosPage1 = getMockedExperienceDtosPage1();
    }

    @Test
    void findAllPage_test() throws Exception {
        Page<ExperienceDto> experienceDtosPage = new PageImpl<>(experienceDtosPage1);
        given(experienceService.findAllByKey(PAGEABLE, EXPERIENCE_FILTER_KEY)).willReturn(experienceDtosPage);
        given(experienceService.convertToEntities(experienceDtosPage.getContent())).willReturn(experiencesPage1);
        int page = PAGEABLE.getPageNumber();
        int size = PAGEABLE.getPageSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        long nrExperiences = experienceDtosPage.getTotalElements();
        int nrPages = experienceDtosPage.getTotalPages();
        mockMvc.perform(get(EXPERIENCES + PAGINATION, page, size, field, direction, EXPERIENCE_FILTER_KEY).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(EXPERIENCES_VIEW))
              .andExpect(model().attribute(EXPERIENCES_ATTRIBUTE, experiencesPage1))
              .andExpect(model().attribute("nrExperiences", nrExperiences))
              .andExpect(model().attribute("nrPages", nrPages))
              .andExpect(model().attribute("page", page))
              .andExpect(model().attribute("size", size))
              .andExpect(model().attribute("field", field))
              .andExpect(model().attribute("direction", direction))
              .andExpect(model().attribute("key", EXPERIENCE_FILTER_KEY))
              .andExpect(model().attribute("pageStartIndex", getPageStartIndex(page, size)))
              .andExpect(model().attribute("pageEndIndex", getPageEndIndex(page, size, nrExperiences)))
              .andExpect(model().attribute("pageNavigationStartIndex", getPageNavigationStartIndex(page, nrPages)))
              .andExpect(model().attribute("pageNavigationEndIndex", getPageNavigationEndIndex(page, nrPages)))
              .andExpect(model().attribute("searchRequest", new SearchRequest(page, size, field + ',' + direction, EXPERIENCE_FILTER_KEY)));
    }

    @Test
    void findAllByKey_test() throws Exception {
        Page<ExperienceDto> experienceDtosPage = new PageImpl<>(experienceDtosPage1);
        int page = experienceDtosPage.getNumber();
        int size = experienceDtosPage.getSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        mockMvc.perform(post(EXPERIENCES + "/search" + PAGINATION, page, size, field, direction, EXPERIENCE_FILTER_KEY).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EXPERIENCES_VIEW))
              .andExpect(redirectedUrlPattern(EXPERIENCES + "?page=*&size=*&sort=*&key=*"));
    }

    @Test
    void findByIdPage_validId_test() throws Exception {
        given(experienceService.findEntityById(VALID_ID)).willReturn(experience);
        mockMvc.perform(get(EXPERIENCES + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(EXPERIENCE_DETAILS_VIEW))
              .andExpect(model().attribute(EXPERIENCE_ATTRIBUTE, experience));
    }

    @Test
    void findByIdPage_invalidId_test() throws Exception {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.findEntityById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(EXPERIENCES + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void getSavePage_negativeId_test() throws Exception {
        mockMvc.perform(get(EXPERIENCES + "/save/{id}", -1).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_EXPERIENCE_VIEW))
              .andExpect(model().attribute("id", -1))
              .andExpect(model().attribute(EXPERIENCE_DTO_ATTRIBUTE, new ExperienceDto()));
    }

    @Test
    void getSavePage_validId_test() throws Exception {
        given(experienceService.findById(VALID_ID)).willReturn(experienceDto);
        mockMvc.perform(get(EXPERIENCES + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_EXPERIENCE_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute(EXPERIENCE_DTO_ATTRIBUTE, experienceDto));
    }

    @Test
    void getSavePage_invalidId_test() throws Exception {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(EXPERIENCES + "/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_negativeId_test() throws Exception {
        mockMvc.perform(post(EXPERIENCES + "/save/{id}", -1).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(experienceDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EXPERIENCES_VIEW))
              .andExpect(redirectedUrl(EXPERIENCES));
        verify(experienceService).save(any(ExperienceDto.class));
    }

    @Test
    void save_validId_test() throws Exception {
        mockMvc.perform(post(EXPERIENCES + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(experienceDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EXPERIENCES_VIEW))
              .andExpect(redirectedUrl(EXPERIENCES));
        verify(experienceService).updateById(any(ExperienceDto.class), anyInt());
    }

    @Test
    void save_invalidId_test() throws Exception {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.updateById(any(ExperienceDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(EXPERIENCES + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(experienceDto)))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void deleteById_validId_test() throws Exception {
        Page<ExperienceDto> experienceDtosPage = new PageImpl<>(experienceDtosPage1);
        given(experienceService.findAllByKey(PAGEABLE, EXPERIENCE_FILTER_KEY)).willReturn(experienceDtosPage);
        int page = experienceDtosPage.getNumber();
        int size = experienceDtosPage.getSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        mockMvc.perform(get(EXPERIENCES + "/delete/{id}" + PAGINATION, VALID_ID, page, size, field, direction, EXPERIENCE_FILTER_KEY).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EXPERIENCES_VIEW))
              .andExpect(redirectedUrlPattern(EXPERIENCES + "?page=*&size=*&sort=*&key=*"));
    }

    @Test
    void deleteById_invalidId_test() throws Exception {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(experienceService).deleteById(INVALID_ID);
        mockMvc.perform(get(EXPERIENCES + "/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    private MultiValueMap<String, String> convertToMultiValueMap(ExperienceDto experienceDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", experienceDto.getTitle());
        params.add("organization", experienceDto.getOrganization());
        params.add("description", experienceDto.getDescription());
        params.add("type", experienceDto.getType().name());
        params.add("startedAt", experienceDto.getStartedAt().toString());
        params.add("finishedAt", experienceDto.getFinishedAt().toString());
        return params;
    }
}
