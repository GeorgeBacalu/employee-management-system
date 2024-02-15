package com.project.ems.unit.experience;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeRepository;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.*;
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
import static com.project.ems.mock.ExperienceMock.*;
import static com.project.ems.mock.TrainerMock.getMockedTrainer1;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExperienceServiceImplTest {

    @InjectMocks
    private ExperienceServiceImpl experienceService;

    @Mock
    private ExperienceRepository experienceRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Spy
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Experience> experienceCaptor;

    private Experience experience1;
    private Experience experience2;
    private List<Experience> experiences;
    private ExperienceDto experienceDto1;
    private ExperienceDto experienceDto2;
    private List<ExperienceDto> experienceDtos;
    private Employee employee;
    private Trainer trainer;

    @BeforeEach
    void setUp() {
        experience1 = getMockedExperience1();
        experience2 = getMockedExperience2();
        experiences = getMockedExperiences();
        experienceDto1 = getMockedExperienceDto1();
        experienceDto2 = getMockedExperienceDto2();
        experienceDtos = getMockedExperienceDtos();
        employee = getMockedEmployee1();
        trainer = getMockedTrainer1();
    }

    @Test
    void findAll_test() {
        given(experienceRepository.findAll()).willReturn(experiences);
        List<ExperienceDto> result = experienceService.findAll();
        then(result).isEqualTo(experienceDtos);
    }

    @Test
    void findById_validId_test() {
        given(experienceRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(experience1));
        ExperienceDto result = experienceService.findById(VALID_ID);
        then(result).isEqualTo(experienceDto1);
    }

    @Test
    void findById_invalidId_test() {
        thenThrownBy(() -> experienceService.findById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_test() {
        given(experienceRepository.save(any(Experience.class))).willReturn(experience1);
        ExperienceDto result = experienceService.save(experienceDto1);
        verify(experienceRepository).save(experienceCaptor.capture());
        then(result).isEqualTo(experienceService.convertToDto(experienceCaptor.getValue()));
    }

    @Test
    void updateById_validId_test() {
        Experience updatedExperience = experience2;
        updatedExperience.setId(VALID_ID);
        given(experienceRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(experience1));
        given(experienceRepository.save(any(Experience.class))).willReturn(updatedExperience);
        ExperienceDto result = experienceService.updateById(experienceDto2, VALID_ID);
        verify(experienceRepository).save(experienceCaptor.capture());
        then(result).isEqualTo(experienceService.convertToDto(updatedExperience));
    }

    @Test
    void updateById_invalidId_test() {
        thenThrownBy(() -> experienceService.updateById(experienceDto2, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID));
        verify(experienceRepository, never()).save(any(Experience.class));
    }

    @Test
    void deleteById_validId_test() {
        given(experienceRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(experience1));
        given(employeeRepository.findAllByExperiencesContains(any(Experience.class))).willReturn(List.of(employee));
        given(trainerRepository.findAllByExperiencesContains(any(Experience.class))).willReturn(List.of(trainer));
        experienceService.deleteById(VALID_ID);
        verify(experienceRepository).delete(experience1);
    }

    @Test
    void deleteById_invalidId_test() {
        thenThrownBy(() -> experienceService.deleteById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID));
        verify(experienceRepository, never()).delete(any(Experience.class));
    }
}
