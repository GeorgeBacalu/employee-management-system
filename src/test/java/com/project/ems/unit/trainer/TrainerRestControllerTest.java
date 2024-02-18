package com.project.ems.unit.trainer;

import com.project.ems.exception.InvalidRequestException;
import com.project.ems.trainer.TrainerDto;
import com.project.ems.trainer.TrainerRestController;
import com.project.ems.trainer.TrainerService;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.TrainerMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    private List<TrainerDto> activeTrainerDtos;
    private List<TrainerDto> trainerDtosPage1;
    private List<TrainerDto> trainerDtosPage2;
    private List<TrainerDto> trainerDtosPage3;

    @BeforeEach
    void setUp() {
        trainerDto1 = getMockedTrainerDto1();
        trainerDto2 = getMockedTrainerDto2();
        trainerDtos = getMockedTrainerDtos();
        activeTrainerDtos = getMockedActiveTrainerDtos();
        trainerDtosPage1 = getMockedTrainerDtosPage1();
        trainerDtosPage2 = getMockedTrainerDtosPage2();
        trainerDtosPage3 = getMockedTrainerDtosPage3();
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
    void findAllActive_test() {
        given(trainerService.findAllActive()).willReturn(activeTrainerDtos);
        ResponseEntity<List<TrainerDto>> response = trainerRestController.findAllActive();
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(activeTrainerDtos);
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

    @ParameterizedTest
    @CsvSource({"0, ${TRAINER_FILTER_KEY}", "1, ${TRAINER_FILTER_KEY}", "2, ${TRAINER_FILTER_KEY}", "0, ''", "1, ''", "2, ''"})
    void findAllByKey_test(int page, String key) {
        Pair<Pageable, List<TrainerDto>> pageableTrainerDtosPair = switch (page) {
            case 0 -> Pair.of(PAGEABLE_PAGE1, trainerDtosPage1);
            case 1 -> Pair.of(PAGEABLE_PAGE2, trainerDtosPage2);
            case 2 -> Pair.of(PAGEABLE_PAGE3, key.trim().isEmpty() ? trainerDtosPage3 : Collections.emptyList());
            default -> throw new InvalidRequestException(INVALID_PAGE_NUMBER + page);
        };
        Page<TrainerDto> filteredTrainerDtosPage = new PageImpl<>(pageableTrainerDtosPair.getRight());
        given(trainerService.findAllByKey(any(Pageable.class), eq(key))).willReturn(filteredTrainerDtosPage);
        ResponseEntity<Page<TrainerDto>> response = trainerRestController.findAllByKey(pageableTrainerDtosPair.getLeft(), key);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(filteredTrainerDtosPage);
    }

    @ParameterizedTest
    @CsvSource({"0, ${TRAINER_FILTER_KEY}", "1, ${TRAINER_FILTER_KEY}", "0, ''", "1, ''"})
    void findAllActiveByKey_test(int page, String key) {
        Pair<Pageable, List<TrainerDto>> pageableActiveTrainerDtosPair = switch (page) {
            case 0 -> Pair.of(PAGEABLE_PAGE1, trainerDtosPage1);
            case 1 -> Pair.of(PAGEABLE_PAGE2, key.trim().isEmpty() ? trainerDtosPage2 : Collections.emptyList());
            default -> throw new InvalidRequestException(INVALID_PAGE_NUMBER + page);
        };
        Page<TrainerDto> filteredActiveTrainerDtosPage = new PageImpl<>(pageableActiveTrainerDtosPair.getRight());
        given(trainerService.findAllActiveByKey(any(Pageable.class), eq(key))).willReturn(filteredActiveTrainerDtosPage);
        ResponseEntity<Page<TrainerDto>> response = trainerRestController.findAllActiveByKey(pageableActiveTrainerDtosPair.getLeft(), key);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(filteredActiveTrainerDtosPage);
    }
}
