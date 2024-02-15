package com.project.ems.integration.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.user.UserDto;
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
import static com.project.ems.mock.UserMock.*;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDto userDto1;
    private UserDto userDto2;
    private List<UserDto> userDtos;
    private List<UserDto> activeUserDtos;

    @BeforeEach
    void setUp() {
        userDto1 = getMockedUserDto1();
        userDto2 = getMockedUserDto2();
        userDtos = getMockedUserDtos();
        activeUserDtos = getMockedActiveUserDtos();
    }

    @Test
    void findAll_test() throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_USERS, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<UserDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        then(result).isEqualTo(userDtos);
    }

    @Test
    void findAllActive_test() throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_USERS + "/active", String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<UserDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        then(result).isEqualTo(activeUserDtos);
    }

    @Test
    void findById_validId_test() {
        ResponseEntity<UserDto> response = template.getForEntity(API_USERS + "/" + VALID_ID, UserDto.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(userDto1);
    }

    @Test
    void findById_invalidId_test() {
        ResponseEntity<String> response = template.getForEntity(API_USERS + "/" + INVALID_ID, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(USER_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_test() throws Exception {
        ResponseEntity<UserDto> saveResponse = template.postForEntity(API_USERS, userDto1, UserDto.class);
        then(saveResponse).isNotNull();
        then(saveResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        then(saveResponse.getBody()).isEqualTo(userDto1);
        ResponseEntity<String> findAllResponse = template.getForEntity(API_USERS, String.class);
        then(findAllResponse).isNotNull();
        then(findAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<UserDto> result = objectMapper.readValue(findAllResponse.getBody(), new TypeReference<>() {});
        then(result).isEqualTo(userDtos);
    }

    @Test
    void updateById_validId_test() {
        UserDto updatedUserDto = userDto2;
        updatedUserDto.setId(VALID_ID);
        updatedUserDto.setIsActive(true);
        updatedUserDto.setName("test_new_name");
        updatedUserDto.setEmail("test_new_email@email.com");
        ResponseEntity<UserDto> updateResponse = template.exchange(API_USERS + "/" + VALID_ID, HttpMethod.PUT, new HttpEntity<>(userDto2), UserDto.class);
        then(updateResponse).isNotNull();
        then(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(updateResponse.getBody()).isEqualTo(updatedUserDto);
        ResponseEntity<UserDto> getResponse = template.getForEntity(API_USERS + "/" + VALID_ID, UserDto.class);
        then(getResponse).isNotNull();
        then(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(getResponse.getBody()).isEqualTo(updatedUserDto);
    }

    @Test
    void updateById_invalidId_test() {
        ResponseEntity<String> response = template.exchange(API_USERS + "/" + INVALID_ID, HttpMethod.PUT, new HttpEntity<>(userDto2), String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(USER_NOT_FOUND, INVALID_ID));
    }

    @Test
    void disableById_validId_test() throws Exception {
        UserDto disabledUserDto = userDto1;
        disabledUserDto.setIsActive(false);
        ResponseEntity<UserDto> disableResponse = template.exchange(API_USERS + "/" + VALID_ID, HttpMethod.DELETE, null, UserDto.class);
        then(disableResponse).isNotNull();
        then(disableResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(disableResponse.getBody()).isEqualTo(disabledUserDto);
        ResponseEntity<String> findAllResponse = template.getForEntity(API_USERS, String.class);
        then(findAllResponse).isNotNull();
        then(findAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<UserDto> result = objectMapper.readValue(findAllResponse.getBody(), new TypeReference<>() {});
        List<UserDto> userDtosCopy = new ArrayList<>(userDtos);
        userDtosCopy.getFirst().setIsActive(false);
        then(result).containsAll(userDtosCopy);
    }

    @Test
    void disableById_invalidId_test() {
        ResponseEntity<String> response = template.exchange(API_USERS + "/" + INVALID_ID, HttpMethod.DELETE, null, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(USER_NOT_FOUND, INVALID_ID));
    }
}
