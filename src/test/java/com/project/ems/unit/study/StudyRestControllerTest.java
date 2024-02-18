package com.project.ems.unit.study;

import com.project.ems.exception.InvalidRequestException;
import com.project.ems.study.StudyDto;
import com.project.ems.study.StudyRestController;
import com.project.ems.study.StudyService;
import com.project.ems.wrapper.PageWrapper;
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
import static com.project.ems.mock.StudyMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    private List<StudyDto> studyDtosPage1;
    private List<StudyDto> studyDtosPage2;
    private List<StudyDto> studyDtosPage3;

    @BeforeEach
    void setUp() {
        studyDto1 = getMockedStudyDto1();
        studyDto2 = getMockedStudyDto2();
        studyDtos = getMockedStudyDtos();
        studyDtosPage1 = getMockedStudyDtosPage1();
        studyDtosPage2 = getMockedStudyDtosPage2();
        studyDtosPage3 = getMockedStudyDtosPage3();
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

    @ParameterizedTest
    @CsvSource({"0, ${STUDY_FILTER_KEY}", "1, ${STUDY_FILTER_KEY}", "2, ${STUDY_FILTER_KEY}", "0, ''", "1, ''", "2, ''"})
    void findAllByKey_test(int page, String key) {
        Pair<Pageable, List<StudyDto>> pageableStudyDtosPair = switch (page) {
            case 0 -> Pair.of(PAGEABLE_PAGE1, studyDtosPage1);
            case 1 -> Pair.of(PAGEABLE_PAGE2, studyDtosPage2);
            case 2 -> Pair.of(PAGEABLE_PAGE3, key.trim().isEmpty() ? studyDtosPage3 : Collections.emptyList());
            default -> throw new InvalidRequestException(INVALID_PAGE_NUMBER + page);
        };
        Page<StudyDto> filteredStudyDtosPage = new PageImpl<>(pageableStudyDtosPair.getRight());
        given(studyService.findAllByKey(any(Pageable.class), eq(key))).willReturn(filteredStudyDtosPage);
        ResponseEntity<PageWrapper<StudyDto>> response = studyRestController.findAllByKey(pageableStudyDtosPair.getLeft(), key);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody().getContent()).isEqualTo(filteredStudyDtosPage.getContent());
    }
}
