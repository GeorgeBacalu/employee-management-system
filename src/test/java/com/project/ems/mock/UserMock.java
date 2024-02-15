package com.project.ems.mock;

import com.project.ems.user.User;
import com.project.ems.user.UserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.project.ems.mock.AuthorityMock.getMockedAuthorities1;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorities2;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMock {

    public static List<User> getMockedUsers() {
        return List.of(getMockedUser1(), getMockedUser2());
    }

    public static List<User> getMockedActiveUsers() {
        return getMockedUsers().stream().filter(User::getIsActive).toList();
    }

    public static List<UserDto> getMockedUserDtos() {
        return List.of(getMockedUserDto1(), getMockedUserDto2());
    }

    public static List<UserDto> getMockedActiveUserDtos() {
        return getMockedUserDtos().stream().filter(UserDto::getIsActive).toList();
    }

    public static User getMockedUser1() {
        return User.builder()
              .id(1)
              .name("test_name1")
              .email("test_email1@email.com")
              .password("#Test_password1")
              .mobile("+40700000000")
              .address("test_address1")
              .birthday(LocalDate.of(2000, 1, 1))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser2() {
        return User.builder()
              .id(2)
              .name("test_name2")
              .email("test_email2@email.com")
              .password("#Test_password2")
              .mobile("+40700000001")
              .address("test_address2")
              .birthday(LocalDate.of(2000, 1, 2))
              .role(getMockedRole2())
              .authorities(getMockedAuthorities2())
              .isActive(false)
              .build();
    }

    public static UserDto getMockedUserDto1() {
        return UserDto.builder()
              .id(1)
              .name("test_name1")
              .email("test_email1@email.com")
              .password("#Test_password1")
              .mobile("+40700000000")
              .address("test_address1")
              .birthday(LocalDate.of(2000, 1, 1))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto2() {
        return UserDto.builder()
              .id(2)
              .name("test_name2")
              .email("test_email2@email.com")
              .password("#Test_password2")
              .mobile("+40700000001")
              .address("test_address2")
              .birthday(LocalDate.of(2000, 1, 2))
              .roleId(2)
              .authoritiesIds(List.of(3, 4))
              .isActive(false)
              .build();
    }
}
