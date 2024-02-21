package com.project.ems.unit.study;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.study.*;
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
import static com.project.ems.mock.StudyMock.*;
import static com.project.ems.util.PageUtil.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudyControllerTest {

    @InjectMocks
    private StudyController studyController;

    @Mock
    private StudyService studyService;

    @Spy
    private Model model;

    @Spy
    private RedirectAttributes redirectAttributes;

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
    void findAllPage_test() {
        Page<StudyDto> studyDtosPage = new PageImpl<>(studyDtosPage1);
        int page = PAGEABLE.getPageNumber();
        int size = PAGEABLE.getPageSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        long nrStudies = studyDtosPage.getTotalElements();
        int nrPages = studyDtosPage.getTotalPages();
        SearchRequest searchRequest = new SearchRequest(0, size, field + "," + direction, STUDY_FILTER_KEY);
        given(studyService.findAllByKey(PAGEABLE, STUDY_FILTER_KEY)).willReturn(studyDtosPage);
        given(model.getAttribute(STUDIES_ATTRIBUTE)).willReturn(studiesPage1);
        given(model.getAttribute("nrStudies")).willReturn(nrStudies);
        given(model.getAttribute("nrPages")).willReturn(nrPages);
        given(model.getAttribute("page")).willReturn(page);
        given(model.getAttribute("size")).willReturn(size);
        given(model.getAttribute("field")).willReturn(field);
        given(model.getAttribute("direction")).willReturn(direction);
        given(model.getAttribute("key")).willReturn(STUDY_FILTER_KEY);
        given(model.getAttribute("pageStartIndex")).willReturn(getPageStartIndex(page, size));
        given(model.getAttribute("pageEndIndex")).willReturn(getPageEndIndex(page, size, nrStudies));
        given(model.getAttribute("pageNavigationStartIndex")).willReturn(getPageNavigationStartIndex(page, nrPages));
        given(model.getAttribute("pageNavigationEndIndex")).willReturn(getPageNavigationEndIndex(page, nrPages));
        given(model.getAttribute("searchRequest")).willReturn(searchRequest);
        String viewName = studyController.findAllPage(model, PAGEABLE, STUDY_FILTER_KEY);
        then(viewName).isEqualTo(STUDIES_VIEW);
        then(model.getAttribute(STUDIES_ATTRIBUTE)).isEqualTo(studiesPage1);
        then(model.getAttribute("nrStudies")).isEqualTo(nrStudies);
        then(model.getAttribute("nrPages")).isEqualTo(nrPages);
        then(model.getAttribute("page")).isEqualTo(page);
        then(model.getAttribute("size")).isEqualTo(size);
        then(model.getAttribute("field")).isEqualTo(field);
        then(model.getAttribute("direction")).isEqualTo(direction);
        then(model.getAttribute("key")).isEqualTo(STUDY_FILTER_KEY);
        then(model.getAttribute("pageStartIndex")).isEqualTo(getPageStartIndex(page, size));
        then(model.getAttribute("pageEndIndex")).isEqualTo(getPageEndIndex(page, size, nrStudies));
        then(model.getAttribute("pageNavigationStartIndex")).isEqualTo(getPageNavigationStartIndex(page, nrPages));
        then(model.getAttribute("pageNavigationEndIndex")).isEqualTo(getPageNavigationEndIndex(page, nrPages));
        then(model.getAttribute("searchRequest")).isEqualTo(searchRequest);
    }

    @Test
    void findAllByKey_test() {
        Page<StudyDto> studyDtosPage = new PageImpl<>(studyDtosPage1);
        int page = studyDtosPage.getNumber();
        int size = studyDtosPage.getSize();
        String sort = getSortField(PAGEABLE) + ',' + getSortDirection(PAGEABLE);
        given(redirectAttributes.getAttribute("page")).willReturn(page);
        given(redirectAttributes.getAttribute("size")).willReturn(size);
        given(redirectAttributes.getAttribute("sort")).willReturn(sort);
        given(redirectAttributes.getAttribute("key")).willReturn(STUDY_FILTER_KEY);
        String viewName = studyController.findAllByKey(new SearchRequest(page, size, sort, STUDY_FILTER_KEY), redirectAttributes);
        then(viewName).isEqualTo(REDIRECT_STUDIES_VIEW);
        then(redirectAttributes.getAttribute("page")).isEqualTo(page);
        then(redirectAttributes.getAttribute("size")).isEqualTo(size);
        then(redirectAttributes.getAttribute("sort")).isEqualTo(sort);
        then(redirectAttributes.getAttribute("key")).isEqualTo(STUDY_FILTER_KEY);
    }

    @Test
    void findByIdPage_validId_test() {
        given(studyService.findEntityById(VALID_ID)).willReturn(study);
        given(model.getAttribute(STUDY_ATTRIBUTE)).willReturn(study);
        String viewName = studyController.findByIdPage(model, VALID_ID);
        then(viewName).isEqualTo(STUDY_DETAILS_VIEW);
        then(model.getAttribute(STUDY_ATTRIBUTE)).isEqualTo(study);
    }

    @Test
    void findByIdPage_invalidId_test() {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        given(studyService.findEntityById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> studyController.findByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void getSavePage_negativeId_test() {
        given(model.getAttribute("id")).willReturn(-1);
        given(model.getAttribute(STUDY_DTO_ATTRIBUTE)).willReturn(new StudyDto());
        String viewName = studyController.getSavePage(model, -1);
        then(viewName).isEqualTo(SAVE_STUDY_VIEW);
        then(model.getAttribute("id")).isEqualTo(-1);
        then(model.getAttribute(STUDY_DTO_ATTRIBUTE)).isEqualTo(new StudyDto());
    }

    @Test
    void getSavePage_validId_test() {
        given(studyService.findById(VALID_ID)).willReturn(studyDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute(STUDY_DTO_ATTRIBUTE)).willReturn(studyDto);
        String viewName = studyController.getSavePage(model, VALID_ID);
        then(viewName).isEqualTo(SAVE_STUDY_VIEW);
        then(model.getAttribute("id")).isEqualTo(VALID_ID);
        then(model.getAttribute(STUDY_DTO_ATTRIBUTE)).isEqualTo(studyDto);
    }

    @Test
    void getSavePage_invalidId_test() {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        given(studyService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> studyController.getSavePage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void save_negativeId_test() {
        String viewName = studyController.save(studyDto, -1);
        then(viewName).isEqualTo(REDIRECT_STUDIES_VIEW);
        verify(studyService).save(studyDto);
    }

    @Test
    void save_validId_test() {
        String viewName = studyController.save(studyDto, VALID_ID);
        then(viewName).isEqualTo(REDIRECT_STUDIES_VIEW);
        verify(studyService).updateById(studyDto, VALID_ID);
    }

    @Test
    void save_invalidId_test() {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        given(studyService.updateById(studyDto, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> studyController.save(studyDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void deleteById_validId_test() {
        Page<StudyDto> studyDtosPage = new PageImpl<>(studyDtosPage1);
        int page = studyDtosPage.getNumber();
        int size = studyDtosPage.getSize();
        String sort = getSortField(PAGEABLE) + ',' + getSortDirection(PAGEABLE);
        given(studyService.findAllByKey(PAGEABLE, STUDY_FILTER_KEY)).willReturn(studyDtosPage);
        given(redirectAttributes.getAttribute("page")).willReturn(page);
        given(redirectAttributes.getAttribute("size")).willReturn(size);
        given(redirectAttributes.getAttribute("sort")).willReturn(sort);
        given(redirectAttributes.getAttribute("key")).willReturn(STUDY_FILTER_KEY);
        String viewName = studyController.deleteById(VALID_ID, redirectAttributes, PAGEABLE, STUDY_FILTER_KEY);
        verify(studyService).deleteById(VALID_ID);
        then(viewName).isEqualTo(REDIRECT_STUDIES_VIEW);
        then(redirectAttributes.getAttribute("page")).isEqualTo(page);
        then(redirectAttributes.getAttribute("size")).isEqualTo(size);
        then(redirectAttributes.getAttribute("sort")).isEqualTo(sort);
        then(redirectAttributes.getAttribute("key")).isEqualTo(STUDY_FILTER_KEY);
    }

    @Test
    void deleteById_invalidId_test() {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(studyService).deleteById(INVALID_ID);
        thenThrownBy(() -> studyController.deleteById(INVALID_ID, redirectAttributes, PAGEABLE, STUDY_FILTER_KEY))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
