package com.project.ems.unit.user;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.user.*;
import com.project.ems.wrapper.SearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.UserMock.*;
import static com.project.ems.util.PageUtil.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Spy
    private Model model;

    @Spy
    private RedirectAttributes redirectAttributes;

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
    void findAllActivePage_test() {
        Page<UserDto> userDtosPage = new PageImpl<>(activeUserDtosPage1);
        int page = PAGEABLE.getPageNumber();
        int size = PAGEABLE.getPageSize();
        String field = getSortField(PAGEABLE);
        String direction = getSortDirection(PAGEABLE);
        long nrUsers = userDtosPage.getTotalElements();
        int nrPages = userDtosPage.getTotalPages();
        SearchRequest searchRequest = new SearchRequest(0, size, field + "," + direction, USER_FILTER_KEY);
        given(userService.findAllActiveByKey(PAGEABLE, USER_FILTER_KEY)).willReturn(userDtosPage);
        given(model.getAttribute(USERS_ATTRIBUTE)).willReturn(activeUsersPage1);
        given(model.getAttribute("nrUsers")).willReturn(nrUsers);
        given(model.getAttribute("nrPages")).willReturn(nrPages);
        given(model.getAttribute("page")).willReturn(page);
        given(model.getAttribute("size")).willReturn(size);
        given(model.getAttribute("field")).willReturn(field);
        given(model.getAttribute("direction")).willReturn(direction);
        given(model.getAttribute("key")).willReturn(USER_FILTER_KEY);
        given(model.getAttribute("pageStartIndex")).willReturn(getPageStartIndex(page, size));
        given(model.getAttribute("pageEndIndex")).willReturn(getPageEndIndex(page, size, nrUsers));
        given(model.getAttribute("pageNavigationStartIndex")).willReturn(getPageNavigationStartIndex(page, nrPages));
        given(model.getAttribute("pageNavigationEndIndex")).willReturn(getPageNavigationEndIndex(page, nrPages));
        given(model.getAttribute("searchRequest")).willReturn(searchRequest);
        String viewName = userController.findAllActivePage(model, PAGEABLE, USER_FILTER_KEY);
        then(viewName).isEqualTo(USERS_VIEW);
        then(model.getAttribute(USERS_ATTRIBUTE)).isEqualTo(activeUsersPage1);
        then(model.getAttribute("nrUsers")).isEqualTo(nrUsers);
        then(model.getAttribute("nrPages")).isEqualTo(nrPages);
        then(model.getAttribute("page")).isEqualTo(page);
        then(model.getAttribute("size")).isEqualTo(size);
        then(model.getAttribute("field")).isEqualTo(field);
        then(model.getAttribute("direction")).isEqualTo(direction);
        then(model.getAttribute("key")).isEqualTo(USER_FILTER_KEY);
        then(model.getAttribute("pageStartIndex")).isEqualTo(getPageStartIndex(page, size));
        then(model.getAttribute("pageEndIndex")).isEqualTo(getPageEndIndex(page, size, nrUsers));
        then(model.getAttribute("pageNavigationStartIndex")).isEqualTo(getPageNavigationStartIndex(page, nrPages));
        then(model.getAttribute("pageNavigationEndIndex")).isEqualTo(getPageNavigationEndIndex(page, nrPages));
        then(model.getAttribute("searchRequest")).isEqualTo(searchRequest);
    }

    @Test
    void findAllActiveByKey_test() {
        Page<UserDto> userDtosPage = new PageImpl<>(activeUserDtosPage1);
        int page = userDtosPage.getNumber();
        int size = userDtosPage.getSize();
        String sort = getSortField(PAGEABLE) + ',' + getSortDirection(PAGEABLE);
        given(redirectAttributes.getAttribute("page")).willReturn(page);
        given(redirectAttributes.getAttribute("size")).willReturn(size);
        given(redirectAttributes.getAttribute("sort")).willReturn(sort);
        given(redirectAttributes.getAttribute("key")).willReturn(USER_FILTER_KEY);
        String viewName = userController.findAllActiveByKey(new SearchRequest(page, size, sort, USER_FILTER_KEY), redirectAttributes);
        then(viewName).isEqualTo(REDIRECT_USERS_VIEW);
        then(redirectAttributes.getAttribute("page")).isEqualTo(page);
        then(redirectAttributes.getAttribute("size")).isEqualTo(size);
        then(redirectAttributes.getAttribute("sort")).isEqualTo(sort);
        then(redirectAttributes.getAttribute("key")).isEqualTo(USER_FILTER_KEY);
    }

    @Test
    void findByIdPage_validId_test() {
        given(userService.findEntityById(VALID_ID)).willReturn(user);
        given(model.getAttribute(USER_ATTRIBUTE)).willReturn(user);
        String viewName = userController.findByIdPage(model, VALID_ID);
        then(viewName).isEqualTo(USER_DETAILS_VIEW);
        then(model.getAttribute(USER_ATTRIBUTE)).isEqualTo(user);
    }

    @Test
    void findByIdPage_invalidId_test() {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.findEntityById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> userController.findByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void getSavePage_negativeId_test() {
        given(model.getAttribute("id")).willReturn(-1);
        given(model.getAttribute(USER_DTO_ATTRIBUTE)).willReturn(new UserDto());
        String viewName = userController.getSavePage(model, -1);
        then(viewName).isEqualTo(SAVE_USER_VIEW);
        then(model.getAttribute("id")).isEqualTo(-1);
        then(model.getAttribute(USER_DTO_ATTRIBUTE)).isEqualTo(new UserDto());
    }

    @Test
    void getSavePage_validId_test() {
        given(userService.findById(VALID_ID)).willReturn(userDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute(USER_DTO_ATTRIBUTE)).willReturn(userDto);
        String viewName = userController.getSavePage(model, VALID_ID);
        then(viewName).isEqualTo(SAVE_USER_VIEW);
        then(model.getAttribute("id")).isEqualTo(VALID_ID);
        then(model.getAttribute(USER_DTO_ATTRIBUTE)).isEqualTo(userDto);
    }

    @Test
    void getSavePage_invalidId_test() {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> userController.getSavePage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void save_negativeId_test() {
        String viewName = userController.save(userDto, -1);
        then(viewName).isEqualTo(REDIRECT_USERS_VIEW);
        verify(userService).save(userDto);
    }

    @Test
    void save_validId_test() {
        String viewName = userController.save(userDto, VALID_ID);
        then(viewName).isEqualTo(REDIRECT_USERS_VIEW);
        verify(userService).updateById(userDto, VALID_ID);
    }

    @Test
    void save_invalidId_test() {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.updateById(userDto, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> userController.save(userDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void disableById_validId_test() {
        Page<UserDto> userDtosPage = new PageImpl<>(activeUserDtosPage1);
        int page = userDtosPage.getNumber();
        int size = userDtosPage.getSize();
        String sort = getSortField(PAGEABLE) + ',' + getSortDirection(PAGEABLE);
        given(userService.findAllActiveByKey(PAGEABLE, USER_FILTER_KEY)).willReturn(userDtosPage);
        given(redirectAttributes.getAttribute("page")).willReturn(page);
        given(redirectAttributes.getAttribute("size")).willReturn(size);
        given(redirectAttributes.getAttribute("sort")).willReturn(sort);
        given(redirectAttributes.getAttribute("key")).willReturn(USER_FILTER_KEY);
        String viewName = userController.disableById(VALID_ID, redirectAttributes, PAGEABLE, USER_FILTER_KEY);
        verify(userService).disableById(VALID_ID);
        then(viewName).isEqualTo(REDIRECT_USERS_VIEW);
        then(redirectAttributes.getAttribute("page")).isEqualTo(page);
        then(redirectAttributes.getAttribute("size")).isEqualTo(size);
        then(redirectAttributes.getAttribute("sort")).isEqualTo(sort);
        then(redirectAttributes.getAttribute("key")).isEqualTo(USER_FILTER_KEY);
    }

    @Test
    void disableById_invalidId_test() {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(userService).disableById(INVALID_ID);
        thenThrownBy(() -> userController.disableById(INVALID_ID, redirectAttributes, PAGEABLE, USER_FILTER_KEY))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
