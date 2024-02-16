package com.project.ems.integration.authority;

import com.project.ems.authority.*;
import com.project.ems.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Objects;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.AuthorityMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorityController.class)
class AuthorityControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorityService authorityService;

    private Authority authority;
    private List<Authority> authorities;
    private AuthorityDto authorityDto;
    private List<AuthorityDto> authorityDtos;

    @BeforeEach
    void setUp() {
        authority = getMockedAuthority1();
        authorities = getMockedAuthorities();
        authorityDto = getMockedAuthorityDto1();
        authorityDtos = getMockedAuthorityDtos();
    }

    @Test
    void findAllPage_test() throws Exception {
        given(authorityService.findAll()).willReturn(authorityDtos);
        given(authorityService.convertToEntities(authorityDtos)).willReturn(authorities);
        mockMvc.perform(get(AUTHORITIES).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(AUTHORITIES_VIEW))
              .andExpect(model().attribute(AUTHORITIES_ATTRIBUTE, authorities));
    }

    @Test
    void findById_validId_test() throws Exception {
        given(authorityService.findEntityById(VALID_ID)).willReturn(authority);
        mockMvc.perform(get(AUTHORITIES + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(AUTHORITY_DETAILS_VIEW))
              .andExpect(model().attribute(AUTHORITY_ATTRIBUTE, authority));
    }

    @Test
    void findByIdPage_invalidId_test() throws Exception {
        String message = String.format(AUTHORITY_NOT_FOUND, INVALID_ID);
        given(authorityService.findEntityById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(AUTHORITIES + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void getSavePage_test() throws Exception {
        mockMvc.perform(get(AUTHORITIES + "/save").accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_AUTHORITY_VIEW))
              .andExpect(model().attribute(AUTHORITY_DTO_ATTRIBUTE, new AuthorityDto()));
    }

    @Test
    void save_test() throws Exception {
        mockMvc.perform(post(AUTHORITIES + "/save").accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(authorityDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_AUTHORITIES_VIEW))
              .andExpect(redirectedUrl(AUTHORITIES));
        verify(authorityService).save(any(AuthorityDto.class));
    }

    private MultiValueMap<String, String> convertToMultiValueMap(AuthorityDto authorityDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("type", authorityDto.getType().name());
        return params;
    }
}
