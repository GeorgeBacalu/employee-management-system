package com.project.ems.unit.study;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeRepository;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.study.*;
import com.project.ems.trainer.Trainer;
import com.project.ems.trainer.TrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.StudyMock.*;
import static com.project.ems.mock.TrainerMock.getMockedTrainer1;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudyServiceImplTest {

    @InjectMocks
    private StudyServiceImpl studyService;

    @Mock
    private StudyRepository studyRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Spy
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Study> studyCaptor;

    private Study study1;
    private Study study2;
    private List<Study> studies;
    private StudyDto studyDto1;
    private StudyDto studyDto2;
    private List<StudyDto> studyDtos;
    private Employee employee;
    private Trainer trainer;

    @BeforeEach
    void setUp() {
        study1 = getMockedStudy1();
        study2 = getMockedStudy2();
        studies = getMockedStudies();
        studyDto1 = getMockedStudyDto1();
        studyDto2 = getMockedStudyDto2();
        studyDtos = getMockedStudyDtos();
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
}
