package com.project.ems.mock;

import com.project.ems.authority.Authority;
import com.project.ems.authority.AuthorityDto;
import com.project.ems.authority.enums.AuthorityType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorityMock {

    public static List<Authority> getMockedAuthorities() {
        return Stream.of(getMockedAuthorities1(), getMockedAuthorities2()).flatMap(List::stream).toList();
    }

    public static List<AuthorityDto> getMockedAuthorityDtos() {
        return Stream.of(getMockedAuthorityDtos1(), getMockedAuthorityDtos2()).flatMap(List::stream).toList();
    }

    public static List<Authority> getMockedAuthorities1() {
        return List.of(getMockedAuthority1(), getMockedAuthority2());
    }

    public static List<Authority> getMockedAuthorities2() {
        return List.of(getMockedAuthority3(), getMockedAuthority4());
    }

    public static List<AuthorityDto> getMockedAuthorityDtos1() {
        return List.of(getMockedAuthorityDto1(), getMockedAuthorityDto2());
    }

    public static List<AuthorityDto> getMockedAuthorityDtos2() {
        return List.of(getMockedAuthorityDto3(), getMockedAuthorityDto4());
    }

    public static Authority getMockedAuthority1() {
        return Authority.builder().id(1).type(AuthorityType.CREATE).build();
    }

    public static Authority getMockedAuthority2() {
        return Authority.builder().id(2).type(AuthorityType.READ).build();
    }

    public static Authority getMockedAuthority3() {
        return Authority.builder().id(3).type(AuthorityType.UPDATE).build();
    }

    public static Authority getMockedAuthority4() {
        return Authority.builder().id(4).type(AuthorityType.DELETE).build();
    }

    public static AuthorityDto getMockedAuthorityDto1() {
        return AuthorityDto.builder().id(1).type(AuthorityType.CREATE).build();
    }

    public static AuthorityDto getMockedAuthorityDto2() {
        return AuthorityDto.builder().id(2).type(AuthorityType.READ).build();
    }

    public static AuthorityDto getMockedAuthorityDto3() {
        return AuthorityDto.builder().id(3).type(AuthorityType.UPDATE).build();
    }

    public static AuthorityDto getMockedAuthorityDto4() {
        return AuthorityDto.builder().id(4).type(AuthorityType.DELETE).build();
    }
}
