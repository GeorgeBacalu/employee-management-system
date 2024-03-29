package com.project.ems.integration.trainer;

import com.project.ems.authority.Authority;
import com.project.ems.authority.AuthorityService;
import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeRepository;
import com.project.ems.exception.InvalidRequestException;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.role.Role;
import com.project.ems.role.RoleService;
import com.project.ems.study.Study;
import com.project.ems.study.StudyService;
import com.project.ems.trainer.*;
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
import static com.project.ems.mock.AuthorityMock.getMockedAuthorities;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.StudyMock.getMockedStudies1;
import static com.project.ems.mock.TrainerMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TrainerServiceIntegrationTest {

    @Autowired
    private TrainerServiceImpl trainerService;

    @MockBean
    private TrainerRepository trainerRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private RoleService roleService;

    @MockBean
    private AuthorityService authorityService;

    @MockBean
    private ExperienceService experienceService;

    @MockBean
    private StudyService studyService;

    @SpyBean
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Trainer> trainerCaptor;

    private Trainer trainer1;
    private Trainer trainer2;
    private List<Trainer> trainers;
    private List<Trainer> activeTrainers;
    private List<Trainer> trainersPage1;
    private List<Trainer> trainersPage2;
    private List<Trainer> trainersPage3;
    private TrainerDto trainerDto1;
    private TrainerDto trainerDto2;
    private List<TrainerDto> trainerDtos;
    private List<TrainerDto> activeTrainerDtos;
    private List<TrainerDto> trainerDtosPage1;
    private List<TrainerDto> trainerDtosPage2;
    private List<TrainerDto> trainerDtosPage3;
    private Role role;
    private List<Authority> authorities;
    private List<Experience> experiences;
    private List<Study> studies;
    private Employee employee;

    @BeforeEach
    void setUp() {
        trainer1 = getMockedTrainer1();
        trainer2 = getMockedTrainer2();
        trainers = getMockedTrainers();
        activeTrainers = getMockedActiveTrainers();
        trainersPage1 = getMockedTrainersPage1();
        trainersPage2 = getMockedTrainersPage2();
        trainersPage3 = getMockedTrainersPage3();
        trainerDto1 = getMockedTrainerDto1();
        trainerDto2 = getMockedTrainerDto2();
        trainerDtos = getMockedTrainerDtos();
        activeTrainerDtos = getMockedActiveTrainerDtos();
        trainerDtosPage1 = getMockedTrainerDtosPage1();
        trainerDtosPage2 = getMockedTrainerDtosPage2();
        trainerDtosPage3 = getMockedTrainerDtosPage3();
        role = getMockedRole2();
        authorities = getMockedAuthorities();
        experiences = getMockedExperiences1();
        studies = getMockedStudies1();
        employee = getMockedEmployee1();
    }

    @Test
    void findAll_test() {
        given(trainerRepository.findAll()).willReturn(trainers);
        List<TrainerDto> result = trainerService.findAll();
        then(result).isEqualTo(trainerDtos);
    }

    @Test
    void findAllActive_test() {
        given(trainerRepository.findAllByIsActiveTrue()).willReturn(activeTrainers);
        List<TrainerDto> result = trainerService.findAllActive();
        then(result).isEqualTo(activeTrainerDtos);
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
        given(roleService.findEntityById(anyInt())).willReturn(role);
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
        given(employeeRepository.findAllByTrainer(any(Trainer.class))).willReturn(List.of(employee));
        given(trainerRepository.findAllBySupervisingTrainer(any(Trainer.class))).willReturn(List.of(trainer2));
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

    @ParameterizedTest
    @CsvSource({"0, ${TRAINER_FILTER_KEY}", "1, ${TRAINER_FILTER_KEY}", "2, ${TRAINER_FILTER_KEY}", "0, ''", "1, ''", "2, ''"})
    void findAllByKey_test(int page, String key) {
        Pair<Pageable, List<Trainer>> pageableTrainersPair = switch (page) {
            case 0 -> Pair.of(PAGEABLE_PAGE1, trainersPage1);
            case 1 -> Pair.of(PAGEABLE_PAGE2, trainersPage2);
            case 2 -> Pair.of(PAGEABLE_PAGE3, key.trim().isEmpty() ? trainersPage3 : Collections.emptyList());
            default -> throw new InvalidRequestException(INVALID_PAGE_NUMBER + page);
        };
        Page<Trainer> filteredTrainersPage = new PageImpl<>(pageableTrainersPair.getRight());
        if (key.trim().isEmpty()) {
            given(trainerRepository.findAll(any(Pageable.class))).willReturn(filteredTrainersPage);
        } else {
            given(trainerRepository.findAllByKey(any(Pageable.class), eq(key.toLowerCase()))).willReturn(filteredTrainersPage);
        }
        Page<TrainerDto> result = trainerService.findAllByKey(pageableTrainersPair.getLeft(), key);
        then(result.getContent()).isEqualTo(trainerService.convertToDtos(pageableTrainersPair.getRight()));
    }

    @ParameterizedTest
    @CsvSource({"0, ${TRAINER_FILTER_KEY}", "1, ${TRAINER_FILTER_KEY}", "0, ''", "1, ''"})
    void findAllActiveByKey_test(int page, String key) {
        Pair<Pageable, List<Trainer>> pageableActiveTrainersPair = switch (page) {
            case 0 -> Pair.of(PAGEABLE_PAGE1, trainersPage1);
            case 1 -> Pair.of(PAGEABLE_PAGE2, key.trim().isEmpty() ? trainersPage2 : Collections.emptyList());
            default -> throw new InvalidRequestException(INVALID_PAGE_NUMBER + page);
        };
        Page<Trainer> filteredActiveTrainersPage = new PageImpl<>(pageableActiveTrainersPair.getRight());
        if (key.trim().isEmpty()) {
            given(trainerRepository.findAllByIsActiveTrue(any(Pageable.class))).willReturn(filteredActiveTrainersPage);
        } else {
            given(trainerRepository.findAllActiveByKey(any(Pageable.class), eq(key.toLowerCase()))).willReturn(filteredActiveTrainersPage);
        }
        Page<TrainerDto> result = trainerService.findAllActiveByKey(pageableActiveTrainersPair.getLeft(), key);
        then(result.getContent()).isEqualTo(trainerService.convertToDtos(pageableActiveTrainersPair.getRight()));
    }
}
