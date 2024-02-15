package com.project.ems.unit.trainer;

import com.project.ems.authority.Authority;
import com.project.ems.authority.AuthorityService;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.role.Role;
import com.project.ems.role.RoleService;
import com.project.ems.study.Study;
import com.project.ems.study.StudyService;
import com.project.ems.trainer.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorities1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.StudyMock.getMockedStudies1;
import static com.project.ems.mock.TrainerMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TrainerServiceImplTest {

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private AuthorityService authorityService;

    @Mock
    private ExperienceService experienceService;

    @Mock
    private StudyService studyService;

    @Spy
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Trainer> trainerCaptor;

    private Trainer trainer1;
    private Trainer trainer2;
    private List<Trainer> trainers;
    private TrainerDto trainerDto1;
    private TrainerDto trainerDto2;
    private List<TrainerDto> trainerDtos;
    private Role role;
    private List<Authority> authorities;
    private List<Experience> experiences;
    private List<Study> studies;

    @BeforeEach
    void setUp() {
        trainer1 = getMockedTrainer1();
        trainer2 = getMockedTrainer2();
        trainers = getMockedTrainers();
        trainerDto1 = getMockedTrainerDto1();
        trainerDto2 = getMockedTrainerDto2();
        trainerDtos = getMockedTrainerDtos();
        role = getMockedRole1();
        authorities = getMockedAuthorities1();
        experiences = getMockedExperiences1();
        studies = getMockedStudies1();
    }

    @Test
    void findAll_test() {
        given(trainerRepository.findAll()).willReturn(trainers);
        List<TrainerDto> result = trainerService.findAll();
        then(result).isEqualTo(trainerDtos);
    }

    @Test
    void findById_validId_test() {
        given(trainerRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(trainer1));
        TrainerDto result = trainerService.findById(VALID_ID);
        then(result).isEqualTo(trainerDto1);
    }

    @Test
    void findById_invalidId_test() {
        thenThrownBy(() -> trainerService.findById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(TRAINER_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_test() {
        trainerDto1.getAuthoritiesIds().forEach(id -> given(authorityService.findEntityById(id)).willReturn(authorities.get(id - 1)));
        trainerDto1.getExperiencesIds().forEach(id -> given(experienceService.findEntityById(id)).willReturn(experiences.get(id - 1)));
        trainerDto1.getStudiesIds().forEach(id -> given(studyService.findEntityById(id)).willReturn(studies.get(id - 1)));
        given(roleService.findEntityById(VALID_ID)).willReturn(role);
        given(trainerRepository.save(any(Trainer.class))).willReturn(trainer1);
        TrainerDto result = trainerService.save(trainerDto1);
        verify(trainerRepository).save(trainerCaptor.capture());
        then(result).isEqualTo(trainerService.convertToDto(trainerCaptor.getValue()));
    }

    @Test
    void updateById_validId_test() {
        Trainer updatedTrainer = trainer2;
        updatedTrainer.setId(VALID_ID);
        given(trainerRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(trainer1));
        given(trainerRepository.save(any(Trainer.class))).willReturn(updatedTrainer);
        TrainerDto result = trainerService.updateById(trainerDto2, VALID_ID);
        verify(trainerRepository).save(trainerCaptor.capture());
        then(result).isEqualTo(trainerService.convertToDto(updatedTrainer));
    }

    @Test
    void updateById_invalidId_test() {
        thenThrownBy(() -> trainerService.updateById(trainerDto2, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(TRAINER_NOT_FOUND, INVALID_ID));
        verify(trainerRepository, never()).save(any(Trainer.class));
    }

    @Test
    void disableById_validId_test() {
        Trainer disabledTrainer = trainer1;
        disabledTrainer.setIsActive(false);
        given(trainerRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(trainer1));
        given(trainerRepository.save(any(Trainer.class))).willReturn(disabledTrainer);
        TrainerDto result = trainerService.disableById(VALID_ID);
        verify(trainerRepository).save(trainerCaptor.capture());
        then(result).isEqualTo(trainerService.convertToDto(disabledTrainer));
    }

    @Test
    void disableById_invalidId_test() {
        thenThrownBy(() -> trainerService.disableById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(TRAINER_NOT_FOUND, INVALID_ID));
        verify(trainerRepository, never()).save(any(Trainer.class));
    }
}