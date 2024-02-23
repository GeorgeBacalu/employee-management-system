package com.project.ems.unit.trainer;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.trainer.*;
import com.project.ems.wrapper.SearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.TrainerMock.*;
import static com.project.ems.util.PageUtil.*;
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
    private RedirectAttributes redirectAttributes;

    private Trainer trainer;
    private List<Trainer> activeTrainersPage1;
    private TrainerDto trainerDto;
    private List<TrainerDto> activeTrainerDtosPage1;

    @BeforeEach
    void setUp() {
        trainer = getMockedTrainer1();
        activeTrainersPage1 = getMockedTrainersPage1();
        trainerDto = getMockedTrainerDto1();
        activeTrainerDtosPage1 = getMockedTrainerDtosPage1();
    }

    @Test
    void findAllActivePage_test() {
        Page<TrainerDto> trainerDtosPage = new PageImpl<>(activeTrainerDtosPage1);
        int page = PAGEABLE.getPageNumber();
        int size = PAGEABLE.getPageSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        long nrTrainers = trainerDtosPage.getTotalElements();
        int nrPages = trainerDtosPage.getTotalPages();
        SearchRequest searchRequest = new SearchRequest(0, size, field + "," + direction, USER_FILTER_KEY);
        given(trainerService.findAllActiveByKey(PAGEABLE, USER_FILTER_KEY)).willReturn(trainerDtosPage);
        given(model.getAttribute(TRAINERS_ATTRIBUTE)).willReturn(activeTrainersPage1);
        given(model.getAttribute("nrTrainers")).willReturn(nrTrainers);
        given(model.getAttribute("nrPages")).willReturn(nrPages);
        given(model.getAttribute("page")).willReturn(page);
        given(model.getAttribute("size")).willReturn(size);
        given(model.getAttribute("field")).willReturn(field);
        given(model.getAttribute("direction")).willReturn(direction);
        given(model.getAttribute("key")).willReturn(USER_FILTER_KEY);
        given(model.getAttribute("pageStartIndex")).willReturn(getPageStartIndex(page, size));
        given(model.getAttribute("pageEndIndex")).willReturn(getPageEndIndex(page, size, nrTrainers));
        given(model.getAttribute("pageNavigationStartIndex")).willReturn(getPageNavigationStartIndex(page, nrPages));
        given(model.getAttribute("pageNavigationEndIndex")).willReturn(getPageNavigationEndIndex(page, nrPages));
        given(model.getAttribute("searchRequest")).willReturn(searchRequest);
        String viewName = trainerController.findAllActivePage(model, PAGEABLE, USER_FILTER_KEY);
        then(viewName).isEqualTo(TRAINERS_VIEW);
        then(model.getAttribute(TRAINERS_ATTRIBUTE)).isEqualTo(activeTrainersPage1);
        then(model.getAttribute("nrTrainers")).isEqualTo(nrTrainers);
        then(model.getAttribute("nrPages")).isEqualTo(nrPages);
        then(model.getAttribute("page")).isEqualTo(page);
        then(model.getAttribute("size")).isEqualTo(size);
        then(model.getAttribute("field")).isEqualTo(field);
        then(model.getAttribute("direction")).isEqualTo(direction);
        then(model.getAttribute("key")).isEqualTo(USER_FILTER_KEY);
        then(model.getAttribute("pageStartIndex")).isEqualTo(getPageStartIndex(page, size));
        then(model.getAttribute("pageEndIndex")).isEqualTo(getPageEndIndex(page, size, nrTrainers));
        then(model.getAttribute("pageNavigationStartIndex")).isEqualTo(getPageNavigationStartIndex(page, nrPages));
        then(model.getAttribute("pageNavigationEndIndex")).isEqualTo(getPageNavigationEndIndex(page, nrPages));
        then(model.getAttribute("searchRequest")).isEqualTo(searchRequest);
    }

    @Test
    void findAllActiveByKey_test() {
        Page<TrainerDto> trainerDtosPage = new PageImpl<>(activeTrainerDtosPage1);
        int page = trainerDtosPage.getNumber();
        int size = trainerDtosPage.getSize();
        String sort = getSortField(PAGEABLE) + ',' + getSortDirection(PAGEABLE);
        given(redirectAttributes.getAttribute("page")).willReturn(page);
        given(redirectAttributes.getAttribute("size")).willReturn(size);
        given(redirectAttributes.getAttribute("sort")).willReturn(sort);
        given(redirectAttributes.getAttribute("key")).willReturn(USER_FILTER_KEY);
        String viewName = trainerController.findAllActiveByKey(new SearchRequest(page, size, sort, USER_FILTER_KEY), redirectAttributes);
        then(viewName).isEqualTo(REDIRECT_TRAINERS_VIEW);
        then(redirectAttributes.getAttribute("page")).isEqualTo(page);
        then(redirectAttributes.getAttribute("size")).isEqualTo(size);
        then(redirectAttributes.getAttribute("sort")).isEqualTo(sort);
        then(redirectAttributes.getAttribute("key")).isEqualTo(USER_FILTER_KEY);
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
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
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
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
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
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(trainerService.updateById(trainerDto, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> trainerController.save(trainerDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void disableById_validId_test() {
        Page<TrainerDto> trainerDtosPage = new PageImpl<>(activeTrainerDtosPage1);
        int page = trainerDtosPage.getNumber();
        int size = trainerDtosPage.getSize();
        String sort = getSortField(PAGEABLE) + ',' + getSortDirection(PAGEABLE);
        given(trainerService.findAllActiveByKey(PAGEABLE, USER_FILTER_KEY)).willReturn(trainerDtosPage);
        given(redirectAttributes.getAttribute("page")).willReturn(page);
        given(redirectAttributes.getAttribute("size")).willReturn(size);
        given(redirectAttributes.getAttribute("sort")).willReturn(sort);
        given(redirectAttributes.getAttribute("key")).willReturn(USER_FILTER_KEY);
        String viewName = trainerController.disableById(VALID_ID, redirectAttributes, PAGEABLE, USER_FILTER_KEY);
        verify(trainerService).disableById(VALID_ID);
        then(viewName).isEqualTo(REDIRECT_TRAINERS_VIEW);
        then(redirectAttributes.getAttribute("page")).isEqualTo(page);
        then(redirectAttributes.getAttribute("size")).isEqualTo(size);
        then(redirectAttributes.getAttribute("sort")).isEqualTo(sort);
        then(redirectAttributes.getAttribute("key")).isEqualTo(USER_FILTER_KEY);
    }

    @Test
    void disableById_invalidId_test() {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(trainerService).disableById(INVALID_ID);
        thenThrownBy(() -> trainerController.disableById(INVALID_ID, redirectAttributes, PAGEABLE, USER_FILTER_KEY))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
