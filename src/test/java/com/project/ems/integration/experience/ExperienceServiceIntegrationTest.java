package com.project.ems.integration.experience;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeRepository;
import com.project.ems.exception.InvalidRequestException;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.*;
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
import static com.project.ems.mock.ExperienceMock.*;
import static com.project.ems.mock.TrainerMock.getMockedTrainer1;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ExperienceServiceIntegrationTest {

    @Autowired
    private ExperienceServiceImpl experienceService;

    @MockBean
    private ExperienceRepository experienceRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private TrainerRepository trainerRepository;

    @SpyBean
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Experience> experienceCaptor;

    private Experience experience1;
    private Experience experience2;
    private List<Experience> experiences;
    private List<Experience> experiencesPage1;
    private List<Experience> experiencesPage2;
    private List<Experience> experiencesPage3;
    private ExperienceDto experienceDto1;
    private ExperienceDto experienceDto2;
    private List<ExperienceDto> experienceDtos;
    private List<ExperienceDto> experienceDtosPage1;
    private List<ExperienceDto> experienceDtosPage2;
    private List<ExperienceDto> experienceDtosPage3;
    private Employee employee;
    private Trainer trainer;

    @BeforeEach
    void setUp() {
        experience1 = getMockedExperience1();
        experience2 = getMockedExperience2();
        experiences = getMockedExperiences();
        experiencesPage1 = getMockedExperiencesPage1();
        experiencesPage2 = getMockedExperiencesPage2();
        experiencesPage3 = getMockedExperiencesPage3();
        experienceDto1 = getMockedExperienceDto1();
        experienceDto2 = getMockedExperienceDto2();
        experienceDtos = getMockedExperienceDtos();
        experienceDtosPage1 = getMockedExperienceDtosPage1();
        experienceDtosPage2 = getMockedExperienceDtosPage2();
        experienceDtosPage3 = getMockedExperienceDtosPage3();
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

    @ParameterizedTest
    @CsvSource({"0, ${EXPERIENCE_FILTER_KEY}", "1, ${EXPERIENCE_FILTER_KEY}", "2, ${EXPERIENCE_FILTER_KEY}", "0, ''", "1, ''", "2, ''"})
    void findAllByKey_test(int page, String key) {
        Pair<Pageable, List<Experience>> pageableExperiencesPair = switch (page) {
            case 0 -> Pair.of(PAGEABLE_PAGE1, experiencesPage1);
            case 1 -> Pair.of(PAGEABLE_PAGE2, experiencesPage2);
            case 2 -> Pair.of(PAGEABLE_PAGE3, key.trim().isEmpty() ? experiencesPage3 : Collections.emptyList());
            default -> throw new InvalidRequestException(INVALID_PAGE_NUMBER + page);
        };
        Page<Experience> filteredExperiencesPage = new PageImpl<>(pageableExperiencesPair.getRight());
        if (key.trim().isEmpty()) {
            given(experienceRepository.findAll(any(Pageable.class))).willReturn(filteredExperiencesPage);
        } else {
            given(experienceRepository.findAllByKey(any(Pageable.class), eq(key.toLowerCase()))).willReturn(filteredExperiencesPage);
        }
        Page<ExperienceDto> result = experienceService.findAllByKey(pageableExperiencesPair.getLeft(), key);
        then(result.getContent()).isEqualTo(experienceService.convertToDtos(pageableExperiencesPair.getRight()));
    }
}
