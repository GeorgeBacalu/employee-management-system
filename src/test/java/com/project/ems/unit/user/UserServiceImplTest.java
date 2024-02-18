package com.project.ems.unit.user;

import com.project.ems.authority.Authority;
import com.project.ems.authority.AuthorityService;
import com.project.ems.exception.InvalidRequestException;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.Role;
import com.project.ems.role.RoleService;
import com.project.ems.user.*;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorities;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.UserMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.*;
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
    private List<User> usersPage1;
    private List<User> usersPage2;
    private List<User> usersPage3;
    private UserDto userDto1;
    private UserDto userDto2;
    private List<UserDto> userDtos;
    private List<UserDto> activeUserDtos;
    private List<UserDto> userDtosPage1;
    private List<UserDto> userDtosPage2;
    private List<UserDto> userDtosPage3;
    private Role role;
    private List<Authority> authorities;

    @BeforeEach
    void setUp() {
        user1 = getMockedUser1();
        user2 = getMockedUser2();
        users = getMockedUsers();
        activeUsers = getMockedActiveUsers();
        usersPage1 = getMockedUsersPage1();
        usersPage2 = getMockedUsersPage2();
        usersPage3 = getMockedUsersPage3();
        userDto1 = getMockedUserDto1();
        userDto2 = getMockedUserDto2();
        userDtos = getMockedUserDtos();
        activeUserDtos = getMockedActiveUserDtos();
        userDtosPage1 = getMockedUserDtosPage1();
        userDtosPage2 = getMockedUserDtosPage2();
        userDtosPage3 = getMockedUserDtosPage3();
        role = getMockedRole2();
        authorities = getMockedAuthorities();
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
        given(roleService.findEntityById(anyInt())).willReturn(role);
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

    @ParameterizedTest
    @CsvSource({"0, ${USER_FILTER_KEY}", "1, ${USER_FILTER_KEY}", "2, ${USER_FILTER_KEY}", "0, ''", "1, ''", "2, ''"})
    void findAllByKey_test(int page, String key) {
        Pair<Pageable, List<User>> pageableUsersPair = switch (page) {
            case 0 -> Pair.of(PAGEABLE_PAGE1, usersPage1);
            case 1 -> Pair.of(PAGEABLE_PAGE2, usersPage2);
            case 2 -> Pair.of(PAGEABLE_PAGE3, key.trim().isEmpty() ? usersPage3 : Collections.emptyList());
            default -> throw new InvalidRequestException(INVALID_PAGE_NUMBER + page);
        };
        Page<User> filteredUsersPage = new PageImpl<>(pageableUsersPair.getRight());
        if (key.trim().isEmpty()) {
            given(userRepository.findAll(any(Pageable.class))).willReturn(filteredUsersPage);
        } else {
            given(userRepository.findAllByKey(any(Pageable.class), eq(key.toLowerCase()))).willReturn(filteredUsersPage);
        }
        Page<UserDto> result = userService.findAllByKey(pageableUsersPair.getLeft(), key);
        then(result.getContent()).isEqualTo(userService.convertToDtos(pageableUsersPair.getRight()));
    }

    @ParameterizedTest
    @CsvSource({"0, ${USER_FILTER_KEY}", "1, ${USER_FILTER_KEY}", "0, ''", "1, ''"})
    void findAllActiveByKey_test(int page, String key) {
        Pair<Pageable, List<User>> pageableActiveUsersPair = switch (page) {
            case 0 -> Pair.of(PAGEABLE_PAGE1, usersPage1);
            case 1 -> Pair.of(PAGEABLE_PAGE2, key.trim().isEmpty() ? usersPage2 : Collections.emptyList());
            default -> throw new InvalidRequestException(INVALID_PAGE_NUMBER + page);
        };
        Page<User> filteredActiveUsersPage = new PageImpl<>(pageableActiveUsersPair.getRight());
        if (key.trim().isEmpty()) {
            given(userRepository.findAllByIsActiveTrue(any(Pageable.class))).willReturn(filteredActiveUsersPage);
        } else {
            given(userRepository.findAllActiveByKey(any(Pageable.class), eq(key.toLowerCase()))).willReturn(filteredActiveUsersPage);
        }
        Page<UserDto> result = userService.findAllActiveByKey(pageableActiveUsersPair.getLeft(), key);
        then(result.getContent()).isEqualTo(userService.convertToDtos(pageableActiveUsersPair.getRight()));
    }
}
