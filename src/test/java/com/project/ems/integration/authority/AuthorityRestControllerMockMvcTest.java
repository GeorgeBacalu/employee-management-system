package com.project.ems.integration.authority;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.authority.AuthorityDto;
import com.project.ems.authority.AuthorityRestController;
import com.project.ems.authority.AuthorityService;
import com.project.ems.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Objects;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorityDto1;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorityDtos;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorityRestController.class)
class AuthorityRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorityService authorityService;

    private AuthorityDto authorityDto;
    private List<AuthorityDto> authorityDtos;

    @BeforeEach
    void setUp() {
        authorityDto = getMockedAuthorityDto1();
        authorityDtos = getMockedAuthorityDtos();
    }

    @Test
    void findAll_test() throws Exception {
        given(authorityService.findAll()).willReturn(authorityDtos);
        ResultActions actions = mockMvc.perform(get(API_AUTHORITIES)).andExpect(status().isOk());
        for (int i = 0; i < authorityDtos.size(); ++i) {
            assertAuthorityDto(actions, "$[" + i + "]", authorityDtos.get(i));
        }
        List<AuthorityDto> result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        then(result).isEqualTo(authorityDtos);
    }

    @Test
    void findById_validId_test() throws Exception {
        given(authorityService.findById(VALID_ID)).willReturn(authorityDto);
        ResultActions actions = mockMvc.perform(get(API_AUTHORITIES + "/{id}", VALID_ID)).andExpect(status().isOk());
        assertAuthorityDtoJson(actions, authorityDto);
        AuthorityDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), AuthorityDto.class);
        then(result).isEqualTo(authorityDto);
    }

    @Test
    void findById_invalidId_test() throws Exception {
        String message = String.format(AUTHORITY_NOT_FOUND, INVALID_ID);
        given(authorityService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(API_AUTHORITIES + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_test() throws Exception {
        given(authorityService.save(any(AuthorityDto.class))).willReturn(authorityDto);
        ResultActions actions = mockMvc.perform(post(API_AUTHORITIES)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(authorityDto)))
              .andExpect(status().isCreated());
        AuthorityDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), AuthorityDto.class);
        then(result).isEqualTo(authorityDto);
    }

    private void assertAuthorityDto(ResultActions actions, String prefix, AuthorityDto authorityDto) throws Exception {
        actions.andExpect(jsonPath(prefix + ".id").value(authorityDto.getId()))
              .andExpect(jsonPath(prefix + ".type").value(authorityDto.getType().name()));
    }

    private void assertAuthorityDtoJson(ResultActions actions, AuthorityDto authorityDto) throws Exception {
        actions.andExpect(jsonPath("$.id").value(authorityDto.getId()))
              .andExpect(jsonPath("$.type").value(authorityDto.getType().name()));
    }
}
