package com.project.ems.unit.trainer;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.trainer.*;
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
import static com.project.ems.mock.TrainerMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TrainerControllerTest {

    @InjectMocks
    private TrainerController trainerController;

    @Mock
    private TrainerService trainerService;

    @Spy
    private Model model;

    @Spy
    private ModelMapper modelMapper;

    private Trainer trainer;
    private List<Trainer> activeTrainers;
    private TrainerDto trainerDto;
    private List<TrainerDto> activeTrainerDtos;

    @BeforeEach
    void setUp() {
        trainer = getMockedTrainer1();
        activeTrainers = getMockedActiveTrainers();
        trainerDto = getMockedTrainerDto1();
        activeTrainerDtos = getMockedActiveTrainerDtos();
    }

    @Test
    void findAllActivePage_test() {
        given(trainerService.findAllActive()).willReturn(activeTrainerDtos);
        given(trainerService.convertToEntities(activeTrainerDtos)).willReturn(activeTrainers);
        given(model.getAttribute(TRAINERS_ATTRIBUTE)).willReturn(activeTrainers);
        String viewName = trainerController.findAllActivePage(model);
        then(viewName).isEqualTo(TRAINERS_VIEW);
        then(model.getAttribute(TRAINERS_ATTRIBUTE)).isEqualTo(activeTrainers);
    }

    @Test
    void findByIdPage_validId_test() {
        given(trainerService.findEntityById(VALID_ID)).willReturn(trainer);
        given(model.getAttribute(TRAINER_ATTRIBUTE)).willReturn(trainer);
        String viewName = trainerController.findByIdPage(model, VALID_ID);
        then(viewName).isEqualTo(TRAINER_DETAILS_VIEW);
        then(model.getAttribute(TRAINER_ATTRIBUTE)).isEqualTo(trainer);
    }

    @Test
    void findByIdPage_invalidId_test() {
        String message = String.format(TRAINER_NOT_FOUND, INVALID_ID);
        given(trainerService.findEntityById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> trainerController.findByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void getSavePage_negativeId_test() {
        given(model.getAttribute("id")).willReturn(-1);
        given(model.getAttribute(TRAINER_DTO_ATTRIBUTE)).willReturn(new TrainerDto());
        String viewName = trainerController.getSavePage(model, -1);
        then(viewName).isEqualTo(SAVE_TRAINER_VIEW);
        then(model.getAttribute("id")).isEqualTo(-1);
        then(model.getAttribute(TRAINER_DTO_ATTRIBUTE)).isEqualTo(new TrainerDto());
    }

    @Test
    void getSavePage_validId_test() {
        given(trainerService.findById(VALID_ID)).willReturn(trainerDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute(TRAINER_DTO_ATTRIBUTE)).willReturn(trainerDto);
        String viewName = trainerController.getSavePage(model, VALID_ID);
        then(viewName).isEqualTo(SAVE_TRAINER_VIEW);
        then(model.getAttribute("id")).isEqualTo(VALID_ID);
        then(model.getAttribute(TRAINER_DTO_ATTRIBUTE)).isEqualTo(trainerDto);
    }

    @Test
    void getSavePage_invalidId_test() {
        String message = String.format(TRAINER_NOT_FOUND, INVALID_ID);
        given(trainerService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> trainerController.getSavePage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void save_negativeId_test() {
        String viewName = trainerController.save(trainerDto, -1);
        then(viewName).isEqualTo(REDIRECT_TRAINERS_VIEW);
        verify(trainerService).save(trainerDto);
    }

    @Test
    void save_validId_test() {
        String viewName = trainerController.save(trainerDto, VALID_ID);
        then(viewName).isEqualTo(REDIRECT_TRAINERS_VIEW);
        verify(trainerService).updateById(trainerDto, VALID_ID);
    }

    @Test
    void save_invalidId_test() {
        String message = String.format(TRAINER_NOT_FOUND, INVALID_ID);
        given(trainerService.updateById(trainerDto, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> trainerController.save(trainerDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void disableById_validId_test() {
        String viewName = trainerController.disableById(VALID_ID);
        then(viewName).isEqualTo(REDIRECT_TRAINERS_VIEW);
    }

    @Test
    void disableById_invalidId_test() {
        String message = String.format(TRAINER_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(trainerService).disableById(INVALID_ID);
        thenThrownBy(() -> trainerController.disableById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
