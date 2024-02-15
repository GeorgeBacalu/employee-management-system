package com.project.ems.integration.study;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.study.StudyDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.StudyMock.*;
import static org.assertj.core.api.BDDAssertions.then;

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

    @BeforeEach
    void setUp() {
        studyDto1 = getMockedStudyDto1();
        studyDto2 = getMockedStudyDto2();
        studyDtos = getMockedStudyDtos();
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
}
