package com.project.ems.mock;

import com.project.ems.role.Role;
import com.project.ems.role.RoleDto;
import com.project.ems.role.enums.RoleType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleMock {

    public static List<Role> getMockedRoles() {
        return List.of(getMockedRole1(), getMockedRole2());
    }

    public static List<RoleDto> getMockedRoleDtos() {
        return List.of(getMockedRoleDto1(), getMockedRoleDto2());
    }

    public static Role getMockedRole1() {
        return Role.builder().id(1).type(RoleType.USER).build();
    }

    public static Role getMockedRole2() {
        return Role.builder().id(2).type(RoleType.ADMIN).build();
    }

    public static RoleDto getMockedRoleDto1() {
        return RoleDto.builder().id(1).type(RoleType.USER).build();
    }

    public static RoleDto getMockedRoleDto2() {
        return RoleDto.builder().id(2).type(RoleType.ADMIN).build();
    }
}
