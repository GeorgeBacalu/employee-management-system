package com.project.ems.integration.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.user.UserDto;
import com.project.ems.user.UserRestController;
import com.project.ems.user.UserService;
import com.project.ems.wrapper.PageWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.UserMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void findAllByKey_test(int page, int size, String sortField, String sortDirection, String key, Page<UserDto> expectedPage) throws Exception {
        given(userService.findAllByKey(any(Pageable.class), eq(key))).willReturn(expectedPage);
        ResultActions actions = mockMvc.perform(get(API_USERS + API_PAGINATION, page, size, sortField, sortDirection, key)).andExpect(status().isOk());
        List<UserDto> expectedPageContent = expectedPage.getContent();
        for (int i = 0; i < expectedPageContent.size(); ++i) {
            assertUserDto(actions, "$.content[" + i + "]", expectedPageContent.get(i));
        }
        PageWrapper<UserDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        then(response.getContent()).isEqualTo(expectedPageContent);
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void findAllActiveByKey_test(int page, int size, String sortField, String sortDirection, String key, Page<UserDto> expectedPage) throws Exception {
        given(userService.findAllActiveByKey(any(Pageable.class), eq(key))).willReturn(expectedPage);
        ResultActions actions = mockMvc.perform(get(API_USERS + API_ACTIVE_PAGINATION, page, size, sortField, sortDirection, key)).andExpect(status().isOk());
        List<UserDto> expectedPageActiveContent = expectedPage.getContent();
        for (int i = 0; i < expectedPageActiveContent.size(); ++i) {
            assertUserDto(actions, "$.content[" + i + "]", expectedPageActiveContent.get(i));
        }
        PageWrapper<UserDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        then(response.getContent()).isEqualTo(expectedPageActiveContent);
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
