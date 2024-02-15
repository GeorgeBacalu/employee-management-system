package com.project.ems.integration.experience;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;
import java.util.Optional;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.ExperienceMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ExperienceServiceIntegrationTest {

    @Autowired
    private ExperienceServiceImpl experienceService;

    @MockBean
    private ExperienceRepository experienceRepository;

    @SpyBean
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Experience> experienceCaptor;

    private Experience experience1;
    private Experience experience2;
    private List<Experience> experiences;
    private ExperienceDto experienceDto1;
    private ExperienceDto experienceDto2;
    private List<ExperienceDto> experienceDtos;

    @BeforeEach
    void setUp() {
        experience1 = getMockedExperience1();
        experience2 = getMockedExperience2();
        experiences = getMockedExperiences();
        experienceDto1 = getMockedExperienceDto1();
        experienceDto2 = getMockedExperienceDto2();
        experienceDtos = getMockedExperienceDtos();
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
