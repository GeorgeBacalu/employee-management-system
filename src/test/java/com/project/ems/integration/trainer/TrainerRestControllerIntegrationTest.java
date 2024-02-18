package com.project.ems.integration.trainer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.trainer.TrainerDto;
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
import static com.project.ems.mock.TrainerMock.*;
import static org.assertj.core.api.BDDAssertions.then;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class TrainerRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    private TrainerDto trainerDto1;
    private TrainerDto trainerDto2;
    private List<TrainerDto> trainerDtos;
    private List<TrainerDto> activeTrainerDtos;
    private List<TrainerDto> trainerDtosListPage1;
    private List<TrainerDto> trainerDtosListPage2;
    private List<TrainerDto> trainerDtosListPage3;

    @BeforeEach
    void setUp() {
        trainerDto1 = getMockedTrainerDto1();
        trainerDto2 = getMockedTrainerDto2();
        trainerDtos = getMockedTrainerDtos();
        activeTrainerDtos = getMockedActiveTrainerDtos();
        trainerDtosListPage1 = getMockedTrainerDtosPage1();
        trainerDtosListPage2 = getMockedTrainerDtosPage2();
        trainerDtosListPage3 = getMockedTrainerDtosPage3();
    }

    @Test
    void findAll_test() throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_TRAINERS, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<TrainerDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        then(result).isEqualTo(trainerDtos);
    }

    @Test
    void findAllActive_test() throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_TRAINERS + "/active", String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<TrainerDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        then(result).isEqualTo(activeTrainerDtos);
    }

    @Test
    void findById_validId_test() {
        ResponseEntity<TrainerDto> response = template.getForEntity(API_TRAINERS + "/" + VALID_ID, TrainerDto.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(trainerDto1);
    }

    @Test
    void findById_invalidId_test() {
        ResponseEntity<String> response = template.getForEntity(API_TRAINERS + "/" + INVALID_ID, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(TRAINER_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_test() throws Exception {
        ResponseEntity<TrainerDto> saveResponse = template.postForEntity(API_TRAINERS, trainerDto1, TrainerDto.class);
        then(saveResponse).isNotNull();
        then(saveResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        then(saveResponse.getBody()).isEqualTo(trainerDto1);
        ResponseEntity<String> findAllResponse = template.getForEntity(API_TRAINERS, String.class);
        then(findAllResponse).isNotNull();
        then(findAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<TrainerDto> result = objectMapper.readValue(findAllResponse.getBody(), new TypeReference<>() {});
        then(result).containsAll(trainerDtos);
    }

    @Test
    void updateById_validId_test() {
        TrainerDto updatedTrainerDto = trainerDto2;
        updatedTrainerDto.setId(VALID_ID);
        updatedTrainerDto.setIsActive(true);
        updatedTrainerDto.setName("test_new_name");
        updatedTrainerDto.setEmail("test_new_email@email.com");
        ResponseEntity<TrainerDto> updateResponse = template.exchange(API_TRAINERS + "/" + VALID_ID, HttpMethod.PUT, new HttpEntity<>(trainerDto2), TrainerDto.class);
        then(updateResponse).isNotNull();
        then(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(updateResponse.getBody()).isEqualTo(updatedTrainerDto);
        ResponseEntity<TrainerDto> getResponse = template.getForEntity(API_TRAINERS + "/" + VALID_ID, TrainerDto.class);
        then(getResponse).isNotNull();
        then(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(getResponse.getBody()).isEqualTo(updatedTrainerDto);
    }

    @Test
    void updateById_invalidId_test() {
        ResponseEntity<String> response = template.exchange(API_TRAINERS + "/" + INVALID_ID, HttpMethod.PUT, new HttpEntity<>(trainerDto2), String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(TRAINER_NOT_FOUND, INVALID_ID));
    }

    @Test
    void disableById_validId_test() throws Exception {
        TrainerDto disabledTrainerDto = trainerDto1;
        disabledTrainerDto.setIsActive(false);
        ResponseEntity<TrainerDto> disableResponse = template.exchange(API_TRAINERS + "/" + VALID_ID, HttpMethod.DELETE, null, TrainerDto.class);
        then(disableResponse).isNotNull();
        then(disableResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(disableResponse.getBody()).isEqualTo(disabledTrainerDto);
        ResponseEntity<String> findAllResponse = template.getForEntity(API_TRAINERS, String.class);
        then(findAllResponse).isNotNull();
        then(findAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<TrainerDto> result = objectMapper.readValue(findAllResponse.getBody(), new TypeReference<>() {});
        List<TrainerDto> trainerDtosCopy = new ArrayList<>(trainerDtos);
        trainerDtosCopy.getFirst().setIsActive(false);
        for (int i = 6; i < 12; ++i) {
            trainerDtosCopy.get(i).setSupervisingTrainerId(null);
        }
        then(result).containsAll(trainerDtosCopy);
    }

    @Test
    void disableById_invalidId_test() {
        ResponseEntity<String> response = template.exchange(API_TRAINERS + "/" + INVALID_ID, HttpMethod.DELETE, null, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(TRAINER_NOT_FOUND, INVALID_ID));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void findAllByKey_test(int page, int size, String sortField, String sortDirection, String key, Page<TrainerDto> expectedPage) throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_TRAINERS + String.format(API_PAGINATION2, page, size, sortField, sortDirection, key), String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PageWrapper<TrainerDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        then(result.getContent()).isEqualTo(expectedPage.getContent());
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void findAllActiveByKey_test(int page, int size, String sortField, String sortDirection, String key, Page<TrainerDto> expectedPage) throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_TRAINERS + String.format(API_ACTIVE_PAGINATION2, page, size, sortField, sortDirection, key), String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PageWrapper<TrainerDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        then(result.getContent()).isEqualTo(expectedPage.getContent());
    }

    private Stream<Arguments> paginationArguments() {
        Page<TrainerDto> trainerDtosPage1 = new PageImpl<>(trainerDtosListPage1);
        Page<TrainerDto> trainerDtosPage2 = new PageImpl<>(trainerDtosListPage2);
        Page<TrainerDto> trainerDtosPage3 = new PageImpl<>(trainerDtosListPage3);
        Page<TrainerDto> emptyPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", "asc", TRAINER_FILTER_KEY, trainerDtosPage1),
                         Arguments.of(1, 2, "id", "asc", TRAINER_FILTER_KEY, trainerDtosPage2),
                         Arguments.of(2, 2, "id", "asc", TRAINER_FILTER_KEY, emptyPage),
                         Arguments.of(0, 2, "id", "asc", "", trainerDtosPage1),
                         Arguments.of(1, 2, "id", "asc", "", trainerDtosPage2),
                         Arguments.of(2, 2, "id", "asc", "", trainerDtosPage3));
    }
}
