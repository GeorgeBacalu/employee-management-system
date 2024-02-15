package com.project.ems.unit.study;

import com.project.ems.study.StudyDto;
import com.project.ems.study.StudyRestController;
import com.project.ems.study.StudyService;
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
import static com.project.ems.mock.StudyMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StudyRestControllerTest {

    @InjectMocks
    private StudyRestController studyRestController;

    @Mock
    private StudyService studyService;

    @Spy
    private ModelMapper modelMapper;

    private StudyDto studyDto1;
    private StudyDto studyDto2;
    private List<StudyDto> studyDtos;

    @BeforeEach
    void setUp() {
        studyDto1 = getMockedStudyDto1();
        studyDto2 = getMockedStudyDto2();
        studyDtos = getMockedStudyDtos();
    }

    @Test
    void findAll_test() {
        given(studyService.findAll()).willReturn(studyDtos);
        ResponseEntity<List<StudyDto>> response = studyRestController.findAll();
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(studyDtos);
    }

    @Test
    void findById_test() {
        given(studyService.findById(VALID_ID)).willReturn(studyDto1);
        ResponseEntity<StudyDto> response = studyRestController.findById(VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(studyDto1);
    }

    @Test
    void save_test() {
        given(studyService.save(studyDto1)).willReturn(studyDto1);
        ResponseEntity<StudyDto> response = studyRestController.save(studyDto1);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        then(response.getBody()).isEqualTo(studyDto1);
    }

    @Test
    void updateById_test() {
        given(studyService.updateById(studyDto2, VALID_ID)).willReturn(studyDto2);
        ResponseEntity<StudyDto> response = studyRestController.updateById(studyDto2, VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(studyDto2);
    }

    @Test
    void deleteById_test() {
        ResponseEntity<Void> response = studyRestController.deleteById(VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
