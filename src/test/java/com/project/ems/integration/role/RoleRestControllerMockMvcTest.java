package com.project.ems.integration.role;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.RoleDto;
import com.project.ems.role.RoleRestController;
import com.project.ems.role.RoleService;
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
import static com.project.ems.mock.RoleMock.getMockedRoleDto1;
import static com.project.ems.mock.RoleMock.getMockedRoleDtos;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoleRestController.class)
class RoleRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoleService roleService;

    private RoleDto roleDto;
    private List<RoleDto> roleDtos;

    @BeforeEach
    void setUp() {
        roleDto = getMockedRoleDto1();
        roleDtos = getMockedRoleDtos();
    }

    @Test
    void findAll_test() throws Exception {
        given(roleService.findAll()).willReturn(roleDtos);
        ResultActions actions = mockMvc.perform(get(API_ROLES)).andExpect(status().isOk());
        for (int i = 0; i < roleDtos.size(); ++i) {
            assertRoleDto(actions, "$[" + i + "]", roleDtos.get(i));
        }
        List<RoleDto> result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        then(result).isEqualTo(roleDtos);
    }

    @Test
    void findById_validId_test() throws Exception {
        given(roleService.findById(VALID_ID)).willReturn(roleDto);
        ResultActions actions = mockMvc.perform(get(API_ROLES + "/{id}", VALID_ID)).andExpect(status().isOk());
        assertRoleDtoJson(actions, roleDto);
        RoleDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), RoleDto.class);
        then(result).isEqualTo(roleDto);
    }

    @Test
    void findById_invalidId_test() throws Exception {
        String message = String.format(ROLE_NOT_FOUND, INVALID_ID);
        given(roleService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(API_ROLES + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_test() throws Exception {
        given(roleService.save(any(RoleDto.class))).willReturn(roleDto);
        ResultActions actions = mockMvc.perform(post(API_ROLES)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(roleDto)))
              .andExpect(status().isCreated());
        RoleDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), RoleDto.class);
        then(result).isEqualTo(roleDto);
    }

    private void assertRoleDto(ResultActions actions, String prefix, RoleDto roleDto) throws Exception {
        actions.andExpect(jsonPath(prefix + ".id").value(roleDto.getId()))
              .andExpect(jsonPath(prefix + ".type").value(roleDto.getType().name()));
    }

    private void assertRoleDtoJson(ResultActions actions, RoleDto roleDto) throws Exception {
        actions.andExpect(jsonPath("$.id").value(roleDto.getId()))
              .andExpect(jsonPath("$.type").value(roleDto.getType().name()));
    }
}
