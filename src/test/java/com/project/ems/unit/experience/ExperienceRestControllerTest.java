package com.project.ems.unit.experience;

import com.project.ems.experience.ExperienceDto;
import com.project.ems.experience.ExperienceRestController;
import com.project.ems.experience.ExperienceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.ExperienceMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ExperienceRestControllerTest {

    @InjectMocks
    private ExperienceRestController experienceRestController;

    @Mock
    private ExperienceService experienceService;

    @Spy
    private ModelMapper modelMapper;

    private ExperienceDto experienceDto1;
    private ExperienceDto experienceDto2;
    private List<ExperienceDto> experienceDtos;

    @BeforeEach
    void setUp() {
        experienceDto1 = getMockedExperienceDto1();
        experienceDto2 = getMockedExperienceDto2();
        experienceDtos = getMockedExperienceDtos();
    }

    @Test
    void findAll_test() {
        given(experienceService.findAll()).willReturn(experienceDtos);
        ResponseEntity<List<ExperienceDto>> response = experienceRestController.findAll();
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(experienceDtos);
    }

    @Test
    void findById_test() {
        given(experienceService.findById(VALID_ID)).willReturn(experienceDto1);
        ResponseEntity<ExperienceDto> response = experienceRestController.findById(VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(experienceDto1);
    }

    @Test
    void save_test() {
        given(experienceService.save(experienceDto1)).willReturn(experienceDto1);
        ResponseEntity<ExperienceDto> response = experienceRestController.save(experienceDto1);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        then(response.getBody()).isEqualTo(experienceDto1);
    }

    @Test
    void updateById_test() {
        given(experienceService.updateById(experienceDto2, VALID_ID)).willReturn(experienceDto2);
        ResponseEntity<ExperienceDto> response = experienceRestController.updateById(experienceDto2, VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(experienceDto2);
    }

    @Test
    void deleteById_test() {
        ResponseEntity<Void> response = experienceRestController.deleteById(VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
