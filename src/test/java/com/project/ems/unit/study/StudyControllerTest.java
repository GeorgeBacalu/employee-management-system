package com.project.ems.unit.study;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.study.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;

import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.StudyMock.*;
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
    private ModelMapper modelMapper;

    private Study study;
    private List<Study> studies;
    private StudyDto studyDto;
    private List<StudyDto> studyDtos;

    @BeforeEach
    void setUp() {
        study = getMockedStudy1();
        studies = getMockedStudies();
        studyDto = getMockedStudyDto1();
        studyDtos = getMockedStudyDtos();
    }

    @Test
    void findAllPage_test() {
        given(studyService.findAll()).willReturn(studyDtos);
        given(studyService.convertToEntities(studyDtos)).willReturn(studies);
        given(model.getAttribute(STUDIES_ATTRIBUTE)).willReturn(studies);
        String viewName = studyController.findAllPage(model);
        then(viewName).isEqualTo(STUDIES_VIEW);
        then(model.getAttribute(STUDIES_ATTRIBUTE)).isEqualTo(studies);
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
        String viewName = studyController.deleteById(VALID_ID);
        then(viewName).isEqualTo(REDIRECT_STUDIES_VIEW);
    }

    @Test
    void deleteById_invalidId_test() {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(studyService).deleteById(INVALID_ID);
        thenThrownBy(() -> studyController.deleteById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
