package com.project.ems.unit.user;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;

import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.UserMock.*;
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
    private ModelMapper modelMapper;

    private User user;
    private List<User> activeUsers;
    private UserDto userDto;
    private List<UserDto> activeUserDtos;

    @BeforeEach
    void setUp() {
        user = getMockedUser1();
        activeUsers = getMockedActiveUsers();
        userDto = getMockedUserDto1();
        activeUserDtos = getMockedActiveUserDtos();
    }

    @Test
    void findAllActivePage_test() {
        given(userService.findAllActive()).willReturn(activeUserDtos);
        given(userService.convertToEntities(activeUserDtos)).willReturn(activeUsers);
        given(model.getAttribute(USERS_ATTRIBUTE)).willReturn(activeUsers);
        String viewName = userController.findAllActivePage(model);
        then(viewName).isEqualTo(USERS_VIEW);
        then(model.getAttribute(USERS_ATTRIBUTE)).isEqualTo(activeUsers);
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
        String viewName = userController.disableById(VALID_ID);
        then(viewName).isEqualTo(REDIRECT_USERS_VIEW);
    }

    @Test
    void disableById_invalidId_test() {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(userService).disableById(INVALID_ID);
        thenThrownBy(() -> userController.disableById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}