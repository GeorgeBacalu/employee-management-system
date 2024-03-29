package com.project.ems.integration.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.user.UserDto;
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
import static com.project.ems.mock.UserMock.*;
import static org.assertj.core.api.BDDAssertions.then;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
    private List<UserDto> userDtosListPage1;
    private List<UserDto> userDtosListPage2;
    private List<UserDto> userDtosListPage3;

    @BeforeEach
    void setUp() {
        userDto1 = getMockedUserDto1();
        userDto2 = getMockedUserDto2();
        userDtos = getMockedUserDtos();
        activeUserDtos = getMockedActiveUserDtos();
        userDtosListPage1 = getMockedUserDtosPage1();
        userDtosListPage2 = getMockedUserDtosPage2();
        userDtosListPage3 = getMockedUserDtosPage3();
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

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void findAllByKey_test(int page, int size, String sortField, String sortDirection, String key, Page<UserDto> expectedPage) throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_USERS + String.format(API_PAGINATION2, page, size, sortField, sortDirection, key), String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PageWrapper<UserDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        then(result.getContent()).isEqualTo(expectedPage.getContent());
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void findAllActiveByKey_test(int page, int size, String sortField, String sortDirection, String key, Page<UserDto> expectedPage) throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_USERS + String.format(API_ACTIVE_PAGINATION2, page, size, sortField, sortDirection, key), String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PageWrapper<UserDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        then(result.getContent()).isEqualTo(expectedPage.getContent());
    }

    private Stream<Arguments> paginationArguments() {
        Page<UserDto> userDtosPage1 = new PageImpl<>(userDtosListPage1);
        Page<UserDto> userDtosPage2 = new PageImpl<>(userDtosListPage2);
        Page<UserDto> userDtosPage3 = new PageImpl<>(userDtosListPage3);
        Page<UserDto> emptyPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", "asc", USER_FILTER_KEY, userDtosPage1),
                         Arguments.of(1, 2, "id", "asc", USER_FILTER_KEY, userDtosPage2),
                         Arguments.of(2, 2, "id", "asc", USER_FILTER_KEY, emptyPage),
                         Arguments.of(0, 2, "id", "asc", "", userDtosPage1),
                         Arguments.of(1, 2, "id", "asc", "", userDtosPage2),
                         Arguments.of(2, 2, "id", "asc", "", userDtosPage3));
    }
}
