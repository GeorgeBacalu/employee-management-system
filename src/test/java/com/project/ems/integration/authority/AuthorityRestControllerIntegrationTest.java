package com.project.ems.integration.authority;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.authority.AuthorityDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorityDto1;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorityDtos;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AuthorityRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    private AuthorityDto authorityDto;
    private List<AuthorityDto> authorityDtos;

    @BeforeEach
    void setUp() {
        authorityDto = getMockedAuthorityDto1();
        authorityDtos = getMockedAuthorityDtos();
    }

    @Test
    void findAll_test() throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_AUTHORITIES, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<AuthorityDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        then(result).isEqualTo(authorityDtos);
    }

    @Test
    void findById_validId_test() {
        ResponseEntity<AuthorityDto> response = template.getForEntity(API_AUTHORITIES + "/" + VALID_ID, AuthorityDto.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(authorityDto);
    }

    @Test
    void findById_invalidId_test() {
        ResponseEntity<String> response = template.getForEntity(API_AUTHORITIES + "/" + INVALID_ID, String.class);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isEqualTo(RESOURCE_NOT_FOUND + String.format(AUTHORITY_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_test() throws Exception {
        ResponseEntity<AuthorityDto> saveResponse = template.postForEntity(API_AUTHORITIES, authorityDto, AuthorityDto.class);
        then(saveResponse).isNotNull();
        then(saveResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        then(saveResponse.getBody()).isEqualTo(authorityDto);
        ResponseEntity<String> findAllResponse = template.getForEntity(API_AUTHORITIES, String.class);
        then(findAllResponse).isNotNull();
        then(findAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<AuthorityDto> result = objectMapper.readValue(findAllResponse.getBody(), new TypeReference<>() {});
        then(result).isEqualTo(authorityDtos);
    }
}
