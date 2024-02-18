package com.project.ems.integration.study;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.study.StudyDto;
import com.project.ems.wrapper.PageWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.StudyMock.*;
import static org.assertj.core.api.BDDAssertions.then;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class StudyRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    private StudyDto studyDto1;
    private StudyDto studyDto2;
    private List<StudyDto> studyDtos;
    private List<StudyDto> studyDtoListPage1;
    private List<StudyDto> studyDtoListPage2;
    private List<StudyDto> studyDtoListPage3;

    @BeforeEach
    void setUp() {
        studyDto1 = getMockedStudyDto1();
        studyDto2 = getMockedStudyDto2();
        studyDtos = getMockedStudyDtos();
        studyDtoListPage1 = getMockedStudyDtosPage1();
        studyDtoListPage2 = getMockedStudyDtosPage2();
        studyDtoListPage3 = getMockedStudyDtosPage3();
    }

    @Test
    void findAll_test() throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_STUDIES, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<StudyDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        then(result).isEqualTo(studyDtos);
    }

    @Test
    void findById_validId_test() {
        ResponseEntity<StudyDto> response = template.getForEntity(API_STUDIES + "/" + VALID_ID, StudyDto.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(studyDto1);
    }

    @Test
    void findById_invalidId_test() {
        ResponseEntity<String> response = template.getForEntity(API_STUDIES + "/" + INVALID_ID, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(STUDY_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_test() throws Exception {
        ResponseEntity<StudyDto> saveResponse = template.postForEntity(API_STUDIES, studyDto1, StudyDto.class);
        then(saveResponse).isNotNull();
        then(saveResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        then(saveResponse.getBody()).isEqualTo(studyDto1);
        ResponseEntity<String> findAllResponse = template.getForEntity(API_STUDIES, String.class);
        then(findAllResponse).isNotNull();
        then(findAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<StudyDto> result = objectMapper.readValue(findAllResponse.getBody(), new TypeReference<>() {});
        then(result).isEqualTo(studyDtos);
    }

    @Test
    void updateById_validId_test() {
        StudyDto updatedStudyDto = studyDto2;
        updatedStudyDto.setId(VALID_ID);
        ResponseEntity<StudyDto> updateResponse = template.exchange(API_STUDIES + "/" + VALID_ID, HttpMethod.PUT, new HttpEntity<>(studyDto2), StudyDto.class);
        then(updateResponse).isNotNull();
        then(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(updateResponse.getBody()).isEqualTo(updatedStudyDto);
        ResponseEntity<StudyDto> getResponse = template.getForEntity(API_STUDIES + "/" + VALID_ID, StudyDto.class);
        then(getResponse).isNotNull();
        then(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(getResponse.getBody()).isEqualTo(updatedStudyDto);
    }

    @Test
    void updateById_invalidId_test() {
        ResponseEntity<String> response = template.exchange(API_STUDIES + "/" + INVALID_ID, HttpMethod.PUT, new HttpEntity<>(studyDto2), String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(STUDY_NOT_FOUND, INVALID_ID));
    }

    @Test
    void deleteById_validId_test() throws Exception {
        ResponseEntity<StudyDto> deleteResponse = template.exchange(API_STUDIES + "/" + VALID_ID, HttpMethod.DELETE, null, StudyDto.class);
        then(deleteResponse).isNotNull();
        then(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        ResponseEntity<String> getResponse = template.getForEntity(API_STUDIES + "/" + VALID_ID, String.class);
        then(getResponse).isNotNull();
        then(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(getResponse.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(STUDY_NOT_FOUND, VALID_ID));
        ResponseEntity<String> findAllResponse = template.getForEntity(API_STUDIES, String.class);
        then(findAllResponse).isNotNull();
        then(findAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<StudyDto> result = objectMapper.readValue(findAllResponse.getBody(), new TypeReference<>() {});
        List<StudyDto> studyDtosCopy = new ArrayList<>(studyDtos);
        studyDtosCopy.remove(studyDto1);
        then(result).isEqualTo(studyDtosCopy);
    }

    @Test
    void deleteById_invalidId_test() {
        ResponseEntity<String> response = template.exchange(API_STUDIES + "/" + INVALID_ID, HttpMethod.DELETE, null, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(STUDY_NOT_FOUND, INVALID_ID));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void findAllByKey_test(int page, int size, String sortField, String sortDirection, String key, Page<StudyDto> expectedPage) throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_STUDIES + String.format(API_PAGINATION2, page, size, sortField, sortDirection, key), String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PageWrapper<StudyDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        then(result.getContent()).isEqualTo(expectedPage.getContent());
    }

    private Stream<Arguments> paginationArguments() {
        Page<StudyDto> studyDtosPage1 = new PageImpl<>(studyDtoListPage1);
        Page<StudyDto> studyDtosPage2 = new PageImpl<>(studyDtoListPage2);
        Page<StudyDto> studyDtosPage3 = new PageImpl<>(studyDtoListPage3);
        Page<StudyDto> emptyPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", "asc", STUDY_FILTER_KEY, studyDtosPage1),
              Arguments.of(1, 2, "id", "asc", STUDY_FILTER_KEY, studyDtosPage2),
              Arguments.of(2, 2, "id", "asc", STUDY_FILTER_KEY, emptyPage),
              Arguments.of(0, 2, "id", "asc", "", studyDtosPage1),
              Arguments.of(1, 2, "id", "asc", "", studyDtosPage2),
              Arguments.of(2, 2, "id", "asc", "", studyDtosPage3));
    }
}
