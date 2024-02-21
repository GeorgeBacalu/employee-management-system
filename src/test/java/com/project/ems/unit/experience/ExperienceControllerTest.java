package com.project.ems.unit.experience;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.*;
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
import static com.project.ems.mock.ExperienceMock.*;
import static com.project.ems.util.PageUtil.*;
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
    private RedirectAttributes redirectAttributes;

    private Experience experience;
    private List<Experience> experiencesPage1;
    private ExperienceDto experienceDto;
    private List<ExperienceDto> experienceDtosPage1;

    @BeforeEach
    void setUp() {
        experience = getMockedExperience1();
        experiencesPage1 = getMockedExperiencesPage1();
        experienceDto = getMockedExperienceDto1();
        experienceDtosPage1 = getMockedExperienceDtosPage1();
    }

    @Test
    void findAllPage_test() {
        Page<ExperienceDto> experienceDtosPage = new PageImpl<>(experienceDtosPage1);
        int page = PAGEABLE.getPageNumber();
        int size = PAGEABLE.getPageSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        long nrExperiences = experienceDtosPage.getTotalElements();
        int nrPages = experienceDtosPage.getTotalPages();
        SearchRequest searchRequest = new SearchRequest(0, size, field + "," + direction, EXPERIENCE_FILTER_KEY);
        given(experienceService.findAllByKey(PAGEABLE, EXPERIENCE_FILTER_KEY)).willReturn(experienceDtosPage);
        given(model.getAttribute(EXPERIENCES_ATTRIBUTE)).willReturn(experiencesPage1);
        given(model.getAttribute("nrExperiences")).willReturn(nrExperiences);
        given(model.getAttribute("nrPages")).willReturn(nrPages);
        given(model.getAttribute("page")).willReturn(page);
        given(model.getAttribute("size")).willReturn(size);
        given(model.getAttribute("field")).willReturn(field);
        given(model.getAttribute("direction")).willReturn(direction);
        given(model.getAttribute("key")).willReturn(EXPERIENCE_FILTER_KEY);
        given(model.getAttribute("pageStartIndex")).willReturn(getPageStartIndex(page, size));
        given(model.getAttribute("pageEndIndex")).willReturn(getPageEndIndex(page, size, nrExperiences));
        given(model.getAttribute("pageNavigationStartIndex")).willReturn(getPageNavigationStartIndex(page, nrPages));
        given(model.getAttribute("pageNavigationEndIndex")).willReturn(getPageNavigationEndIndex(page, nrPages));
        given(model.getAttribute("searchRequest")).willReturn(searchRequest);
        String viewName = experienceController.findAllPage(model, PAGEABLE, EXPERIENCE_FILTER_KEY);
        then(viewName).isEqualTo(EXPERIENCES_VIEW);
        then(model.getAttribute(EXPERIENCES_ATTRIBUTE)).isEqualTo(experiencesPage1);
        then(model.getAttribute("nrExperiences")).isEqualTo(nrExperiences);
        then(model.getAttribute("nrPages")).isEqualTo(nrPages);
        then(model.getAttribute("page")).isEqualTo(page);
        then(model.getAttribute("size")).isEqualTo(size);
        then(model.getAttribute("field")).isEqualTo(field);
        then(model.getAttribute("direction")).isEqualTo(direction);
        then(model.getAttribute("key")).isEqualTo(EXPERIENCE_FILTER_KEY);
        then(model.getAttribute("pageStartIndex")).isEqualTo(getPageStartIndex(page, size));
        then(model.getAttribute("pageEndIndex")).isEqualTo(getPageEndIndex(page, size, nrExperiences));
        then(model.getAttribute("pageNavigationStartIndex")).isEqualTo(getPageNavigationStartIndex(page, nrPages));
        then(model.getAttribute("pageNavigationEndIndex")).isEqualTo(getPageNavigationEndIndex(page, nrPages));
        then(model.getAttribute("searchRequest")).isEqualTo(searchRequest);
    }

    @Test
    void findAllByKey_test() {
        Page<ExperienceDto> experienceDtosPage = new PageImpl<>(experienceDtosPage1);
        int page = experienceDtosPage.getNumber();
        int size = experienceDtosPage.getSize();
        String sort = getSortField(PAGEABLE) + ',' + getSortDirection(PAGEABLE);
        given(redirectAttributes.getAttribute("page")).willReturn(page);
        given(redirectAttributes.getAttribute("size")).willReturn(size);
        given(redirectAttributes.getAttribute("sort")).willReturn(sort);
        given(redirectAttributes.getAttribute("key")).willReturn(EXPERIENCE_FILTER_KEY);
        String viewName = experienceController.findAllByKey(new SearchRequest(page, size, sort, EXPERIENCE_FILTER_KEY), redirectAttributes);
        then(viewName).isEqualTo(REDIRECT_EXPERIENCES_VIEW);
        then(redirectAttributes.getAttribute("page")).isEqualTo(page);
        then(redirectAttributes.getAttribute("size")).isEqualTo(size);
        then(redirectAttributes.getAttribute("sort")).isEqualTo(sort);
        then(redirectAttributes.getAttribute("key")).isEqualTo(EXPERIENCE_FILTER_KEY);
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
        Page<ExperienceDto> experienceDtosPage = new PageImpl<>(experienceDtosPage1);
        int page = experienceDtosPage.getNumber();
        int size = experienceDtosPage.getSize();
        String sort = getSortField(PAGEABLE) + ',' + getSortDirection(PAGEABLE);
        given(experienceService.findAllByKey(PAGEABLE, EXPERIENCE_FILTER_KEY)).willReturn(experienceDtosPage);
        given(redirectAttributes.getAttribute("page")).willReturn(page);
        given(redirectAttributes.getAttribute("size")).willReturn(size);
        given(redirectAttributes.getAttribute("sort")).willReturn(sort);
        given(redirectAttributes.getAttribute("key")).willReturn(EXPERIENCE_FILTER_KEY);
        String viewName = experienceController.deleteById(VALID_ID, redirectAttributes, PAGEABLE, EXPERIENCE_FILTER_KEY);
        verify(experienceService).deleteById(VALID_ID);
        then(viewName).isEqualTo(REDIRECT_EXPERIENCES_VIEW);
        then(redirectAttributes.getAttribute("page")).isEqualTo(page);
        then(redirectAttributes.getAttribute("size")).isEqualTo(size);
        then(redirectAttributes.getAttribute("sort")).isEqualTo(sort);
        then(redirectAttributes.getAttribute("key")).isEqualTo(EXPERIENCE_FILTER_KEY);
    }

    @Test
    void deleteById_invalidId_test() {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(experienceService).deleteById(INVALID_ID);
        thenThrownBy(() -> experienceController.deleteById(INVALID_ID, redirectAttributes, PAGEABLE, EXPERIENCE_FILTER_KEY))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
