package com.project.ems.integration.trainer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.trainer.TrainerDto;
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
import static com.project.ems.mock.TrainerMock.*;
import static org.assertj.core.api.BDDAssertions.then;

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

    @BeforeEach
    void setUp() {
        trainerDto1 = getMockedTrainerDto1();
        trainerDto2 = getMockedTrainerDto2();
        trainerDtos = getMockedTrainerDtos();
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
        then(result).isEqualTo(trainerDtos);
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
        then(result).containsAll(trainerDtosCopy);
    }

    @Test
    void disableById_invalidId_test() {
        ResponseEntity<String> response = template.exchange(API_TRAINERS + "/" + INVALID_ID, HttpMethod.DELETE, null, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(TRAINER_NOT_FOUND, INVALID_ID));
    }
}
