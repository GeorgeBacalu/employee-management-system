package com.project.ems.integration.experience;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.experience.ExperienceDto;
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
import static com.project.ems.mock.ExperienceMock.*;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ExperienceRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

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
    void findAll_test() throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_EXPERIENCES, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<ExperienceDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        then(result).isEqualTo(experienceDtos);
    }

    @Test
    void findById_validId_test() {
        ResponseEntity<ExperienceDto> response = template.getForEntity(API_EXPERIENCES + "/" + VALID_ID, ExperienceDto.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(experienceDto1);
    }

    @Test
    void findById_invalidId_test() {
        ResponseEntity<String> response = template.getForEntity(API_EXPERIENCES + "/" + INVALID_ID, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(EXPERIENCE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_test() throws Exception {
        ResponseEntity<ExperienceDto> saveResponse = template.postForEntity(API_EXPERIENCES, experienceDto1, ExperienceDto.class);
        then(saveResponse).isNotNull();
        then(saveResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        then(saveResponse.getBody()).isEqualTo(experienceDto1);
        ResponseEntity<String> findAllResponse = template.getForEntity(API_EXPERIENCES, String.class);
        then(findAllResponse).isNotNull();
        then(findAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<ExperienceDto> result = objectMapper.readValue(findAllResponse.getBody(), new TypeReference<>() {});
        then(result).isEqualTo(experienceDtos);
    }

    @Test
    void updateById_validId_test() {
        ExperienceDto updatedExperienceDto = experienceDto2;
        updatedExperienceDto.setId(VALID_ID);
        ResponseEntity<ExperienceDto> updateResponse = template.exchange(API_EXPERIENCES + "/" + VALID_ID, HttpMethod.PUT, new HttpEntity<>(experienceDto2), ExperienceDto.class);
        then(updateResponse).isNotNull();
        then(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(updateResponse.getBody()).isEqualTo(updatedExperienceDto);
        ResponseEntity<ExperienceDto> getResponse = template.getForEntity(API_EXPERIENCES + "/" + VALID_ID, ExperienceDto.class);
        then(getResponse).isNotNull();
        then(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(getResponse.getBody()).isEqualTo(updatedExperienceDto);
    }

    @Test
    void updateById_invalidId_test() {
        ResponseEntity<String> response = template.exchange(API_EXPERIENCES + "/" + INVALID_ID, HttpMethod.PUT, new HttpEntity<>(experienceDto2), String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(EXPERIENCE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void deleteById_validId_test() throws Exception {
        ResponseEntity<ExperienceDto> deleteResponse = template.exchange(API_EXPERIENCES + "/" + VALID_ID, HttpMethod.DELETE, null, ExperienceDto.class);
        then(deleteResponse).isNotNull();
        then(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        ResponseEntity<String> getResponse = template.getForEntity(API_EXPERIENCES + "/" + VALID_ID, String.class);
        then(getResponse).isNotNull();
        then(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(getResponse.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(EXPERIENCE_NOT_FOUND, VALID_ID));
        ResponseEntity<String> findAllResponse = template.getForEntity(API_EXPERIENCES, String.class);
        then(findAllResponse).isNotNull();
        then(findAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<ExperienceDto> result = objectMapper.readValue(findAllResponse.getBody(), new TypeReference<>() {});
        List<ExperienceDto> experienceDtosCopy = new ArrayList<>(experienceDtos);
        experienceDtosCopy.remove(experienceDto1);
        then(result).isEqualTo(experienceDtosCopy);
    }

    @Test
    void deleteById_invalidId_test() {
        ResponseEntity<String> response = template.exchange(API_EXPERIENCES + "/" + INVALID_ID, HttpMethod.DELETE, null, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(EXPERIENCE_NOT_FOUND, INVALID_ID));
    }
}
