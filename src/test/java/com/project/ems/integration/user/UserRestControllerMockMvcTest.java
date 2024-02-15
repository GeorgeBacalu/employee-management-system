package com.project.ems.integration.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.user.UserDto;
import com.project.ems.user.UserRestController;
import com.project.ems.user.UserService;
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
import static com.project.ems.mock.UserMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
class UserRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

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
        given(userService.findAll()).willReturn(userDtos);
        ResultActions actions = mockMvc.perform(get(API_USERS)).andExpect(status().isOk());
        for (int i = 0; i < userDtos.size(); ++i) {
            assertUserDto(actions, "$[" + i + "]", userDtos.get(i));
        }
        List<UserDto> result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        then(result).isEqualTo(userDtos);
    }

    @Test
    void findAllActive_test() throws Exception {
        given(userService.findAllActive()).willReturn(activeUserDtos);
        ResultActions actions = mockMvc.perform(get(API_USERS + "/active")).andExpect(status().isOk());
        for (int i = 0; i < activeUserDtos.size(); ++i) {
            assertUserDto(actions, "$[" + i + "]", activeUserDtos.get(i));
        }
        List<UserDto> result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        then(result).isEqualTo(activeUserDtos);
    }

    @Test
    void findById_validId_test() throws Exception {
        given(userService.findById(VALID_ID)).willReturn(userDto1);
        ResultActions actions = mockMvc.perform(get(API_USERS + "/{id}", VALID_ID)).andExpect(status().isOk());
        assertUserDtoJson(actions, userDto1);
        UserDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), UserDto.class);
        then(result).isEqualTo(userDto1);
    }

    @Test
    void findById_invalidId_test() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(API_USERS + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_test() throws Exception {
        given(userService.save(any(UserDto.class))).willReturn(userDto1);
        ResultActions actions = mockMvc.perform(post(API_USERS)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(userDto1)))
              .andExpect(status().isCreated());
        assertUserDtoJson(actions, userDto1);
        UserDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), UserDto.class);
        then(result).isEqualTo(userDto1);
    }

    @Test
    void updateById_validId_test() throws Exception {
        UserDto updatedUserDto = userDto2;
        updatedUserDto.setId(VALID_ID);
        given(userService.updateById(userDto2, VALID_ID)).willReturn(updatedUserDto);
        ResultActions actions = mockMvc.perform(put(API_USERS + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(userDto2)))
              .andExpect(status().isOk());
        assertUserDtoJson(actions, updatedUserDto);
        UserDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), UserDto.class);
        then(result).isEqualTo(updatedUserDto);
    }

    @Test
    void updateById_invalidId_test() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.updateById(userDto2, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(put(API_USERS + "/{id}", INVALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(userDto2)))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void disableById_validId_test() throws Exception {
        UserDto disabledUserDto = userDto1;
        disabledUserDto.setIsActive(false);
        given(userService.disableById(VALID_ID)).willReturn(disabledUserDto);
        ResultActions actions = mockMvc.perform(delete(API_USERS + "/{id}", VALID_ID)).andExpect(status().isOk());
        assertUserDtoJson(actions, disabledUserDto);
        UserDto result = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), UserDto.class);
        then(result).isEqualTo(disabledUserDto);
    }

    @Test
    void disableById_invalidId_test() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.disableById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(delete(API_USERS + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    private void assertUserDto(ResultActions actions, String prefix, UserDto userDto) throws Exception {
        actions.andExpect(jsonPath(prefix + ".id").value(userDto.getId()))
              .andExpect(jsonPath(prefix + ".name").value(userDto.getName()))
              .andExpect(jsonPath(prefix + ".email").value(userDto.getEmail()))
              .andExpect(jsonPath(prefix + ".password").value(userDto.getPassword()))
              .andExpect(jsonPath(prefix + ".mobile").value(userDto.getMobile()))
              .andExpect(jsonPath(prefix + ".address").value(userDto.getAddress()))
              .andExpect(jsonPath(prefix + ".birthday").value(userDto.getBirthday().toString()))
              .andExpect(jsonPath(prefix + ".roleId").value(userDto.getRoleId()))
              .andExpect(jsonPath(prefix + ".isActive").value(userDto.getIsActive()));
        for (int i = 0; i < userDto.getAuthoritiesIds().size(); ++i) {
            actions.andExpect(jsonPath(prefix + ".authoritiesIds[" + i + "]").value(userDto.getAuthoritiesIds().get(i)));
        }
    }

    private void assertUserDtoJson(ResultActions actions, UserDto userDto) throws Exception {
        actions.andExpect(jsonPath("$.id").value(userDto.getId()))
              .andExpect(jsonPath("$.name").value(userDto.getName()))
              .andExpect(jsonPath("$.email").value(userDto.getEmail()))
              .andExpect(jsonPath("$.password").value(userDto.getPassword()))
              .andExpect(jsonPath("$.mobile").value(userDto.getMobile()))
              .andExpect(jsonPath("$.address").value(userDto.getAddress()))
              .andExpect(jsonPath("$.birthday").value(userDto.getBirthday().toString()))
              .andExpect(jsonPath("$.roleId").value(userDto.getRoleId()))
              .andExpect(jsonPath("$.isActive").value(userDto.getIsActive()));
        for (int i = 0; i < userDto.getAuthoritiesIds().size(); ++i) {
            actions.andExpect(jsonPath("$.authoritiesIds[" + i + "]").value(userDto.getAuthoritiesIds().get(i)));
        }
    }
}
