package com.project.ems.unit.trainer;

import com.project.ems.trainer.TrainerDto;
import com.project.ems.trainer.TrainerRestController;
import com.project.ems.trainer.TrainerService;
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
import static com.project.ems.mock.TrainerMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TrainerRestControllerTest {

    @InjectMocks
    private TrainerRestController trainerRestController;

    @Mock
    private TrainerService trainerService;

    @Spy
    private ModelMapper modelMapper;

    private TrainerDto trainerDto1;
    private TrainerDto trainerDto2;
    private List<TrainerDto> trainerDtos;

    @BeforeEach
    void setUp() {
        trainerDto1 = getMockedTrainerDto1();
        trainerDto2 = getMockedTrainerDto2();
        trainerDtos = getMockedTrainerDtos();
    }

    @Test
    void findAll_test() {
        given(trainerService.findAll()).willReturn(trainerDtos);
        ResponseEntity<List<TrainerDto>> response = trainerRestController.findAll();
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(trainerDtos);
    }

    @Test
    void findById_test() {
        given(trainerService.findById(VALID_ID)).willReturn(trainerDto1);
        ResponseEntity<TrainerDto> response = trainerRestController.findById(VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(trainerDto1);
    }

    @Test
    void save_test() {
        given(trainerService.save(trainerDto1)).willReturn(trainerDto1);
        ResponseEntity<TrainerDto> response = trainerRestController.save(trainerDto1);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        then(response.getBody()).isEqualTo(trainerDto1);
    }

    @Test
    void updateById_test() {
        given(trainerService.updateById(trainerDto2, VALID_ID)).willReturn(trainerDto2);
        ResponseEntity<TrainerDto> response = trainerRestController.updateById(trainerDto2, VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(trainerDto2);
    }

    @Test
    void disableById_test() {
        given(trainerService.disableById(VALID_ID)).willReturn(trainerDto1);
        ResponseEntity<TrainerDto> response = trainerRestController.disableById(VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(trainerDto1);
    }
}
