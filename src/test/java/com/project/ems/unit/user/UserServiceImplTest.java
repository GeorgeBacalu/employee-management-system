package com.project.ems.unit.user;

import com.project.ems.authority.Authority;
import com.project.ems.authority.AuthorityService;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.Role;
import com.project.ems.role.RoleService;
import com.project.ems.user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorities1;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.UserMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private AuthorityService authorityService;

    @Spy
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    private User user1;
    private User user2;
    private List<User> users;
    private List<User> activeUsers;
    private UserDto userDto1;
    private UserDto userDto2;
    private List<UserDto> userDtos;
    private List<UserDto> activeUserDtos;
    private Role role;
    private List<Authority> authorities;

    @BeforeEach
    void setUp() {
        user1 = getMockedUser1();
        user2 = getMockedUser2();
        users = getMockedUsers();
        activeUsers = getMockedActiveUsers();
        userDto1 = getMockedUserDto1();
        userDto2 = getMockedUserDto2();
        userDtos = getMockedUserDtos();
        activeUserDtos = getMockedActiveUserDtos();
        role = getMockedRole1();
        authorities = getMockedAuthorities1();
    }

    @Test
    void findAll_test() {
        given(userRepository.findAll()).willReturn(users);
        List<UserDto> result = userService.findAll();
        then(result).isEqualTo(userDtos);
    }

    @Test
    void findAllActive_test() {
        given(userRepository.findAllByIsActiveTrue()).willReturn(activeUsers);
        List<UserDto> result = userService.findAllActive();
        then(result).isEqualTo(activeUserDtos);
    }

    @Test
    void findById_validId_test() {
        given(userRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(user1));
        UserDto result = userService.findById(VALID_ID);
        then(result).isEqualTo(userDto1);
    }

    @Test
    void findById_invalidId_test() {
        thenThrownBy(() -> userService.findById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(USER_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_test() {
        userDto1.getAuthoritiesIds().forEach(id -> given(authorityService.findEntityById(id)).willReturn(authorities.get(id - 1)));
        given(roleService.findEntityById(VALID_ID)).willReturn(role);
        given(userRepository.save(any(User.class))).willReturn(user1);
        UserDto result = userService.save(userDto1);
        verify(userRepository).save(userCaptor.capture());
        then(result).isEqualTo(userService.convertToDto(userCaptor.getValue()));
    }

    @Test
    void updateById_validId_test() {
        User updatedUser = user2;
        updatedUser.setId(VALID_ID);
        given(userRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(user1));
        given(userRepository.save(any(User.class))).willReturn(updatedUser);
        UserDto result = userService.updateById(userDto2, VALID_ID);
        verify(userRepository).save(userCaptor.capture());
        then(result).isEqualTo(userService.convertToDto(updatedUser));
    }

    @Test
    void updateById_invalidId_test() {
        thenThrownBy(() -> userService.updateById(userDto2, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(USER_NOT_FOUND, INVALID_ID));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void disableById_validId_test() {
        User disabledUser = user1;
        disabledUser.setIsActive(false);
        given(userRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(user1));
        given(userRepository.save(any(User.class))).willReturn(disabledUser);
        UserDto result = userService.disableById(VALID_ID);
        verify(userRepository).save(userCaptor.capture());
        then(result).isEqualTo(userService.convertToDto(disabledUser));
    }

    @Test
    void disableById_invalidId_test() {
        thenThrownBy(() -> userService.disableById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(USER_NOT_FOUND, INVALID_ID));
        verify(userRepository, never()).save(any(User.class));
    }
}
