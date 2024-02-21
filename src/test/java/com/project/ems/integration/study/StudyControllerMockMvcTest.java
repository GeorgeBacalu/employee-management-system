package com.project.ems.integration.study;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.study.*;
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
import static com.project.ems.mock.StudyMock.*;
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

@WebMvcTest(StudyController.class)
class StudyControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudyService studyService;

    private Study study;
    private List<Study> studiesPage1;
    private StudyDto studyDto;
    private List<StudyDto> studyDtosPage1;

    @BeforeEach
    void setUp() {
        study = getMockedStudy1();
        studiesPage1 = getMockedStudiesPage1();
        studyDto = getMockedStudyDto1();
        studyDtosPage1 = getMockedStudyDtosPage1();
    }

    @Test
    void findAllPage_test() throws Exception {
        Page<StudyDto> studyDtosPage = new PageImpl<>(studyDtosPage1);
        given(studyService.findAllByKey(PAGEABLE, STUDY_FILTER_KEY)).willReturn(studyDtosPage);
        given(studyService.convertToEntities(studyDtosPage.getContent())).willReturn(studiesPage1);
        int page = PAGEABLE.getPageNumber();
        int size = PAGEABLE.getPageSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        long nrStudies = studyDtosPage.getTotalElements();
        int nrPages = studyDtosPage.getTotalPages();
        mockMvc.perform(get(STUDIES + PAGINATION, page, size, field, direction, STUDY_FILTER_KEY).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(STUDIES_VIEW))
              .andExpect(model().attribute(STUDIES_ATTRIBUTE, studiesPage1))
              .andExpect(model().attribute("nrStudies", nrStudies))
              .andExpect(model().attribute("nrPages", nrPages))
              .andExpect(model().attribute("page", page))
              .andExpect(model().attribute("size", size))
              .andExpect(model().attribute("field", field))
              .andExpect(model().attribute("direction", direction))
              .andExpect(model().attribute("key", STUDY_FILTER_KEY))
              .andExpect(model().attribute("pageStartIndex", getPageStartIndex(page, size)))
              .andExpect(model().attribute("pageEndIndex", getPageEndIndex(page, size, nrStudies)))
              .andExpect(model().attribute("pageNavigationStartIndex", getPageNavigationStartIndex(page, nrPages)))
              .andExpect(model().attribute("pageNavigationEndIndex", getPageNavigationEndIndex(page, nrPages)))
              .andExpect(model().attribute("searchRequest", new SearchRequest(page, size, field + ',' + direction, STUDY_FILTER_KEY)));
    }

    @Test
    void findAllByKey_test() throws Exception {
        Page<StudyDto> studyDtosPage = new PageImpl<>(studyDtosPage1);
        int page = studyDtosPage.getNumber();
        int size = studyDtosPage.getSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        mockMvc.perform(post(STUDIES + "/search" + PAGINATION, page, size, field, direction, STUDY_FILTER_KEY).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_STUDIES_VIEW))
              .andExpect(redirectedUrlPattern(STUDIES + "?page=*&size=*&sort=*&key=*"));
    }

    @Test
    void findByIdPage_validId_test() throws Exception {
        given(studyService.findEntityById(VALID_ID)).willReturn(study);
        mockMvc.perform(get(STUDIES + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(STUDY_DETAILS_VIEW))
              .andExpect(model().attribute(STUDY_ATTRIBUTE, study));
    }

    @Test
    void findByIdPage_invalidId_test() throws Exception {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        given(studyService.findEntityById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(STUDIES + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void getSavePage_negativeId_test() throws Exception {
        mockMvc.perform(get(STUDIES + "/save/{id}", -1).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_STUDY_VIEW))
              .andExpect(model().attribute("id", -1))
              .andExpect(model().attribute(STUDY_DTO_ATTRIBUTE, new StudyDto()));
    }

    @Test
    void getSavePage_validId_test() throws Exception {
        given(studyService.findById(VALID_ID)).willReturn(studyDto);
        mockMvc.perform(get(STUDIES + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_STUDY_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute(STUDY_DTO_ATTRIBUTE, studyDto));
    }

    @Test
    void getSavePage_invalidId_test() throws Exception {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        given(studyService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(STUDIES + "/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_negativeId_test() throws Exception {
        mockMvc.perform(post(STUDIES + "/save/{id}", -1).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(studyDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_STUDIES_VIEW))
              .andExpect(redirectedUrl(STUDIES));
        verify(studyService).save(any(StudyDto.class));
    }

    @Test
    void save_validId_test() throws Exception {
        mockMvc.perform(post(STUDIES + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(studyDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_STUDIES_VIEW))
              .andExpect(redirectedUrl(STUDIES));
        verify(studyService).updateById(any(StudyDto.class), anyInt());
    }

    @Test
    void save_invalidId_test() throws Exception {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        given(studyService.updateById(any(StudyDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(STUDIES + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(studyDto)))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void deleteById_validId_test() throws Exception {
        Page<StudyDto> studyDtosPage = new PageImpl<>(studyDtosPage1);
        given(studyService.findAllByKey(PAGEABLE, STUDY_FILTER_KEY)).willReturn(studyDtosPage);
        int page = studyDtosPage.getNumber();
        int size = studyDtosPage.getSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        mockMvc.perform(get(STUDIES + "/delete/{id}" + PAGINATION, VALID_ID, page, size, field, direction, STUDY_FILTER_KEY).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_STUDIES_VIEW))
              .andExpect(redirectedUrlPattern(STUDIES + "?page=*&size=*&sort=*&key=*"));
    }

    @Test
    void deleteById_invalidId_test() throws Exception {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(studyService).deleteById(INVALID_ID);
        mockMvc.perform(get(STUDIES + "/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    private MultiValueMap<String, String> convertToMultiValueMap(StudyDto studyDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", studyDto.getTitle());
        params.add("institution", studyDto.getInstitution());
        params.add("description", studyDto.getDescription());
        params.add("type", studyDto.getType().name());
        params.add("startedAt", studyDto.getStartedAt().toString());
        params.add("finishedAt", studyDto.getFinishedAt().toString());
        return params;
    }
}
