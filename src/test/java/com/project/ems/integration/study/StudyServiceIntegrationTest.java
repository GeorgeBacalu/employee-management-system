package com.project.ems.integration.study;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeRepository;
import com.project.ems.exception.InvalidRequestException;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.study.*;
import com.project.ems.trainer.Trainer;
import com.project.ems.trainer.TrainerRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.StudyMock.*;
import static com.project.ems.mock.TrainerMock.getMockedTrainer1;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class StudyServiceIntegrationTest {

    @Autowired
    private StudyServiceImpl studyService;

    @MockBean
    private StudyRepository studyRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private TrainerRepository trainerRepository;

    @SpyBean
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Study> studyCaptor;

    private Study study1;
    private Study study2;
    private List<Study> studies;
    private List<Study> studiesPage1;
    private List<Study> studiesPage2;
    private List<Study> studiesPage3;
    private StudyDto studyDto1;
    private StudyDto studyDto2;
    private List<StudyDto> studyDtos;
    private List<StudyDto> studyDtosPage1;
    private List<StudyDto> studyDtosPage2;
    private List<StudyDto> studyDtosPage3;
    private Employee employee;
    private Trainer trainer;

    @BeforeEach
    void setUp() {
        study1 = getMockedStudy1();
        study2 = getMockedStudy2();
        studies = getMockedStudies();
        studiesPage1 = getMockedStudiesPage1();
        studiesPage2 = getMockedStudiesPage2();
        studiesPage3 = getMockedStudiesPage3();
        studyDto1 = getMockedStudyDto1();
        studyDto2 = getMockedStudyDto2();
        studyDtos = getMockedStudyDtos();
        studyDtosPage1 = getMockedStudyDtosPage1();
        studyDtosPage2 = getMockedStudyDtosPage2();
        studyDtosPage3 = getMockedStudyDtosPage3();
        employee = getMockedEmployee1();
        trainer = getMockedTrainer1();
    }

    @Test
    void findAll_test() {
        given(studyRepository.findAll()).willReturn(studies);
        List<StudyDto> result = studyService.findAll();
        then(result).isEqualTo(studyDtos);
    }

    @Test
    void findById_validId_test() {
        given(studyRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(study1));
        StudyDto result = studyService.findById(VALID_ID);
        then(result).isEqualTo(studyDto1);
    }

    @Test
    void findById_invalidId_test() {
        thenThrownBy(() -> studyService.findById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(STUDY_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_test() {
        given(studyRepository.save(any(Study.class))).willReturn(study1);
        StudyDto result = studyService.save(studyDto1);
        verify(studyRepository).save(studyCaptor.capture());
        then(result).isEqualTo(studyService.convertToDto(studyCaptor.getValue()));
    }

    @Test
    void updateById_validId_test() {
        Study updatedStudy = study2;
        updatedStudy.setId(VALID_ID);
        given(studyRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(study1));
        given(studyRepository.save(any(Study.class))).willReturn(updatedStudy);
        StudyDto result = studyService.updateById(studyDto2, VALID_ID);
        verify(studyRepository).save(studyCaptor.capture());
        then(result).isEqualTo(studyService.convertToDto(updatedStudy));
    }

    @Test
    void updateById_invalidId_test() {
        thenThrownBy(() -> studyService.updateById(studyDto2, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(STUDY_NOT_FOUND, INVALID_ID));
        verify(studyRepository, never()).save(any(Study.class));
    }

    @Test
    void deleteById_validId_test() {
        given(studyRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(study1));
        given(employeeRepository.findAllByStudiesContains(any(Study.class))).willReturn(List.of(employee));
        given(trainerRepository.findAllByStudiesContains(any(Study.class))).willReturn(List.of(trainer));
        studyService.deleteById(VALID_ID);
        verify(studyRepository).delete(study1);
    }

    @Test
    void deleteById_invalidId_test() {
        thenThrownBy(() -> studyService.deleteById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(STUDY_NOT_FOUND, INVALID_ID));
        verify(studyRepository, never()).delete(any(Study.class));
    }

    @ParameterizedTest
    @CsvSource({"0, ${STUDY_FILTER_KEY}", "1, ${STUDY_FILTER_KEY}", "2, ${STUDY_FILTER_KEY}", "0, ''", "1, ''", "2, ''"})
    void findAllByKey_test(int page, String key) {
        Pair<Pageable, List<Study>> pageableStudiesPair = switch (page) {
            case 0 -> Pair.of(PAGEABLE_PAGE1, studiesPage1);
            case 1 -> Pair.of(PAGEABLE_PAGE2, studiesPage2);
            case 2 -> Pair.of(PAGEABLE_PAGE3, key.trim().isEmpty() ? studiesPage3 : Collections.emptyList());
            default -> throw new InvalidRequestException(INVALID_PAGE_NUMBER + page);
        };
        Page<Study> filteredStudiesPage = new PageImpl<>(pageableStudiesPair.getRight());
        if (key.trim().isEmpty()) {
            given(studyRepository.findAll(any(Pageable.class))).willReturn(filteredStudiesPage);
        } else {
            given(studyRepository.findAllByKey(any(Pageable.class), eq(key.toLowerCase()))).willReturn(filteredStudiesPage);
        }
        Page<StudyDto> result = studyService.findAllByKey(pageableStudiesPair.getLeft(), key);
        then(result.getContent()).isEqualTo(studyService.convertToDtos(pageableStudiesPair.getRight()));
    }
}
