package com.project.ems.integration.experience;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.experience.ExperienceDto;
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
import static com.project.ems.mock.ExperienceMock.*;
import static org.assertj.core.api.BDDAssertions.then;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
    private List<ExperienceDto> experienceDtoListPage1;
    private List<ExperienceDto> experienceDtoListPage2;
    private List<ExperienceDto> experienceDtoListPage3;

    @BeforeEach
    void setUp() {
        experienceDto1 = getMockedExperienceDto1();
        experienceDto2 = getMockedExperienceDto2();
        experienceDtos = getMockedExperienceDtos();
        experienceDtoListPage1 = getMockedExperienceDtosPage1();
        experienceDtoListPage2 = getMockedExperienceDtosPage2();
        experienceDtoListPage3 = getMockedExperienceDtosPage3();
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

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void findAllByKey_test(int page, int size, String sortField, String sortDirection, String key, Page<ExperienceDto> expectedPage) throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_EXPERIENCES + String.format(API_PAGINATION2, page, size, sortField, sortDirection, key), String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PageWrapper<ExperienceDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        then(result.getContent()).isEqualTo(expectedPage.getContent());
    }

    private Stream<Arguments> paginationArguments() {
        Page<ExperienceDto> experienceDtosPage1 = new PageImpl<>(experienceDtoListPage1);
        Page<ExperienceDto> experienceDtosPage2 = new PageImpl<>(experienceDtoListPage2);
        Page<ExperienceDto> experienceDtosPage3 = new PageImpl<>(experienceDtoListPage3);
        Page<ExperienceDto> emptyPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", "asc", EXPERIENCE_FILTER_KEY, experienceDtosPage1),
                         Arguments.of(1, 2, "id", "asc", EXPERIENCE_FILTER_KEY, experienceDtosPage2),
                         Arguments.of(2, 2, "id", "asc", EXPERIENCE_FILTER_KEY, emptyPage),
                         Arguments.of(0, 2, "id", "asc", "", experienceDtosPage1),
                         Arguments.of(1, 2, "id", "asc", "", experienceDtosPage2),
                         Arguments.of(2, 2, "id", "asc", "", experienceDtosPage3));
    }
}
