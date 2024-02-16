package com.project.ems.unit.experience;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.*;
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
import static com.project.ems.mock.ExperienceMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExperienceControllerTest {

    @InjectMocks
    private ExperienceController experienceController;

    @Mock
    private ExperienceService experienceService;

    @Spy
    private Model model;

    @Spy
    private ModelMapper modelMapper;

    private Experience experience;
    private List<Experience> experiences;
    private ExperienceDto experienceDto;
    private List<ExperienceDto> experienceDtos;

    @BeforeEach
    void setUp() {
        experience = getMockedExperience1();
        experiences = getMockedExperiences();
        experienceDto = getMockedExperienceDto1();
        experienceDtos = getMockedExperienceDtos();
    }

    @Test
    void findAllPage_test() {
        given(experienceService.findAll()).willReturn(experienceDtos);
        given(experienceService.convertToEntities(experienceDtos)).willReturn(experiences);
        given(model.getAttribute(EXPERIENCES_ATTRIBUTE)).willReturn(experiences);
        String viewName = experienceController.findAllPage(model);
        then(viewName).isEqualTo(EXPERIENCES_VIEW);
        then(model.getAttribute(EXPERIENCES_ATTRIBUTE)).isEqualTo(experiences);
    }

    @Test
    void findByIdPage_validId_test() {
        given(experienceService.findEntityById(VALID_ID)).willReturn(experience);
        given(model.getAttribute(EXPERIENCE_ATTRIBUTE)).willReturn(experience);
        String viewName = experienceController.findByIdPage(model, VALID_ID);
        then(viewName).isEqualTo(EXPERIENCE_DETAILS_VIEW);
        then(model.getAttribute(EXPERIENCE_ATTRIBUTE)).isEqualTo(experience);
    }

    @Test
    void findByIdPage_invalidId_test() {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.findEntityById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> experienceController.findByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void getSavePage_negativeId_test() {
        given(model.getAttribute("id")).willReturn(-1);
        given(model.getAttribute(EXPERIENCE_DTO_ATTRIBUTE)).willReturn(new ExperienceDto());
        String viewName = experienceController.getSavePage(model, -1);
        then(viewName).isEqualTo(SAVE_EXPERIENCE_VIEW);
        then(model.getAttribute("id")).isEqualTo(-1);
        then(model.getAttribute(EXPERIENCE_DTO_ATTRIBUTE)).isEqualTo(new ExperienceDto());
    }

    @Test
    void getSavePage_validId_test() {
        given(experienceService.findById(VALID_ID)).willReturn(experienceDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute(EXPERIENCE_DTO_ATTRIBUTE)).willReturn(experienceDto);
        String viewName = experienceController.getSavePage(model, VALID_ID);
        then(viewName).isEqualTo(SAVE_EXPERIENCE_VIEW);
        then(model.getAttribute("id")).isEqualTo(VALID_ID);
        then(model.getAttribute(EXPERIENCE_DTO_ATTRIBUTE)).isEqualTo(experienceDto);
    }

    @Test
    void getSavePage_invalidId_test() {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> experienceController.getSavePage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void save_negativeId_test() {
        String viewName = experienceController.save(experienceDto, -1);
        then(viewName).isEqualTo(REDIRECT_EXPERIENCES_VIEW);
        verify(experienceService).save(experienceDto);
    }

    @Test
    void save_validId_test() {
        String viewName = experienceController.save(experienceDto, VALID_ID);
        then(viewName).isEqualTo(REDIRECT_EXPERIENCES_VIEW);
        verify(experienceService).updateById(experienceDto, VALID_ID);
    }

    @Test
    void save_invalidId_test() {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.updateById(experienceDto, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> experienceController.save(experienceDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void deleteById_validId_test() {
        String viewName = experienceController.deleteById(VALID_ID);
        then(viewName).isEqualTo(REDIRECT_EXPERIENCES_VIEW);
    }

    @Test
    void deleteById_invalidId_test() {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(experienceService).deleteById(INVALID_ID);
        thenThrownBy(() -> experienceController.deleteById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
