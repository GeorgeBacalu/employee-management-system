package com.project.ems.integration.role;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.*;
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
import static com.project.ems.mock.RoleMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleController.class)
class RoleControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    private Role role;
    private List<Role> roles;
    private RoleDto roleDto;
    private List<RoleDto> roleDtos;

    @BeforeEach
    void setUp() {
        role = getMockedRole1();
        roles = getMockedRoles();
        roleDto = getMockedRoleDto1();
        roleDtos = getMockedRoleDtos();
    }

    @Test
    void findAllPage_test() throws Exception {
        given(roleService.findAll()).willReturn(roleDtos);
        given(roleService.convertToEntities(roleDtos)).willReturn(roles);
        mockMvc.perform(get(ROLES).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(ROLES_VIEW))
              .andExpect(model().attribute(ROLES_ATTRIBUTE, roles));
    }

    @Test
    void findById_validId_test() throws Exception {
        given(roleService.findEntityById(VALID_ID)).willReturn(role);
        mockMvc.perform(get(ROLES + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(ROLE_DETAILS_VIEW))
              .andExpect(model().attribute(ROLE_ATTRIBUTE, role));
    }

    @Test
    void findByIdPage_invalidId_test() throws Exception {
        String message = String.format(ROLE_NOT_FOUND, INVALID_ID);
        given(roleService.findEntityById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(ROLES + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void getSavePage_test() throws Exception {
        mockMvc.perform(get(ROLES + "/save").accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_ROLE_VIEW))
              .andExpect(model().attribute(ROLE_DTO_ATTRIBUTE, new RoleDto()));
    }

    @Test
    void save_test() throws Exception {
        mockMvc.perform(post(ROLES + "/save").accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(roleDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_ROLES_VIEW))
              .andExpect(redirectedUrl(ROLES));
        verify(roleService).save(any(RoleDto.class));
    }

    private MultiValueMap<String, String> convertToMultiValueMap(RoleDto roleDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("type", roleDto.getType().name());
        return params;
    }
}
