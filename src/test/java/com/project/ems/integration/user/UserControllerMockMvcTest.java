package com.project.ems.integration.user;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.user.*;
import com.project.ems.wrapper.SearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.UserMock.*;
import static com.project.ems.util.PageUtil.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;
    private List<User> activeUsersPage1;
    private UserDto userDto;
    private List<UserDto> activeUserDtosPage1;

    @BeforeEach
    void setUp() {
        user = getMockedUser1();
        activeUsersPage1 = getMockedUsersPage1();
        userDto = getMockedUserDto1();
        activeUserDtosPage1 = getMockedUserDtosPage1();
    }

    @Test
    void findAllActivePage_test() throws Exception {
        Page<UserDto> userDtosPage = new PageImpl<>(activeUserDtosPage1);
        given(userService.findAllActiveByKey(PAGEABLE, USER_FILTER_KEY)).willReturn(userDtosPage);
        given(userService.convertToEntities(userDtosPage.getContent())).willReturn(activeUsersPage1);
        int page = PAGEABLE.getPageNumber();
        int size = PAGEABLE.getPageSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        long nrUsers = userDtosPage.getTotalElements();
        int nrPages = userDtosPage.getTotalPages();
        mockMvc.perform(get(USERS + PAGINATION, page, size, field, direction, USER_FILTER_KEY).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(USERS_VIEW))
              .andExpect(model().attribute(USERS_ATTRIBUTE, activeUsersPage1))
              .andExpect(model().attribute("nrUsers", nrUsers))
              .andExpect(model().attribute("nrPages", nrPages))
              .andExpect(model().attribute("page", page))
              .andExpect(model().attribute("size", size))
              .andExpect(model().attribute("field", field))
              .andExpect(model().attribute("direction", direction))
              .andExpect(model().attribute("key", USER_FILTER_KEY))
              .andExpect(model().attribute("pageStartIndex", getPageStartIndex(page, size)))
              .andExpect(model().attribute("pageEndIndex", getPageEndIndex(page, size, nrUsers)))
              .andExpect(model().attribute("pageNavigationStartIndex", getPageNavigationStartIndex(page, nrPages)))
              .andExpect(model().attribute("pageNavigationEndIndex", getPageNavigationEndIndex(page, nrPages)))
              .andExpect(model().attribute("searchRequest", new SearchRequest(page, size, field + ',' + direction, USER_FILTER_KEY)));
    }

    @Test
    void findAllActiveByKey_test() throws Exception {
        Page<UserDto> userDtosPage = new PageImpl<>(activeUserDtosPage1);
        int page = userDtosPage.getNumber();
        int size = userDtosPage.getSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        mockMvc.perform(post(USERS + "/search" + PAGINATION, page, size, field, direction, USER_FILTER_KEY).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_USERS_VIEW))
              .andExpect(redirectedUrlPattern(USERS + "?page=*&size=*&sort=*&key=*"));
    }

    @Test
    void findByIdPage_validId_test() throws Exception {
        given(userService.findEntityById(VALID_ID)).willReturn(user);
        mockMvc.perform(get(USERS + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(USER_DETAILS_VIEW))
              .andExpect(model().attribute(USER_ATTRIBUTE, user));
    }

    @Test
    void findByIdPage_invalidId_test() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.findEntityById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(USERS + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void getSavePage_negativeId_test() throws Exception {
        mockMvc.perform(get(USERS + "/save/{id}", -1).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_USER_VIEW))
              .andExpect(model().attribute("id", -1))
              .andExpect(model().attribute(USER_DTO_ATTRIBUTE, new UserDto()));
    }

    @Test
    void getSavePage_validId_test() throws Exception {
        given(userService.findById(VALID_ID)).willReturn(userDto);
        mockMvc.perform(get(USERS + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_USER_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute(USER_DTO_ATTRIBUTE, userDto));
    }

    @Test
    void getSavePage_invalidId_test() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(USERS + "/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_negativeId_test() throws Exception {
        mockMvc.perform(post(USERS + "/save/{id}", -1).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(userDto)))
              .andExpect(status().isFound())
              .andExpect(redirectedUrl(USERS));
        verify(userService).save(any(UserDto.class));
    }

    @Test
    void save_validId_test() throws Exception {
        mockMvc.perform(post(USERS + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(userDto)))
              .andExpect(status().isFound())
              .andExpect(redirectedUrl(USERS));
        verify(userService).updateById(any(UserDto.class), anyInt());
    }

    @Test
    void save_invalidId_test() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.updateById(any(UserDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(USERS + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                    .params(convertToMultiValueMap(userDto)))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void disableById_validId_test() throws Exception {
        Page<UserDto> userDtosPage = new PageImpl<>(activeUserDtosPage1);
        given(userService.findAllActiveByKey(PAGEABLE, USER_FILTER_KEY)).willReturn(userDtosPage);
        int page = userDtosPage.getNumber();
        int size = userDtosPage.getSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        mockMvc.perform(get(USERS + "/delete/{id}" + PAGINATION, VALID_ID, page, size, field, direction, USER_FILTER_KEY).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_USERS_VIEW))
              .andExpect(redirectedUrlPattern(USERS + "?page=*&size=*&sort=*&key=*"));
    }

    @Test
    void disableById_invalidId_test() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(userService).disableById(INVALID_ID);
        mockMvc.perform(get(USERS + "/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> then(result.getResolvedException() instanceof ResourceNotFoundException).isTrue())
              .andExpect(result -> then(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    private MultiValueMap<String, String> convertToMultiValueMap(UserDto userDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", userDto.getName());
        params.add("email", userDto.getEmail());
        params.add("password", userDto.getPassword());
        params.add("mobile", userDto.getMobile());
        params.add("address", userDto.getAddress());
        params.add("birthday", userDto.getBirthday().toString());
        params.add("roleId", userDto.getRoleId().toString());
        params.add("authoritiesIds", userDto.getAuthoritiesIds().stream().map(String::valueOf).collect(Collectors.joining(", ")));
        params.add("isActive", userDto.getIsActive().toString());
        return params;
    }
}
