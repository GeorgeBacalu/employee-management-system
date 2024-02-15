package com.project.ems.unit.role;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.RoleMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @Spy
    private ModelMapper modelMapper;

    private Role role;
    private RoleDto roleDto;
    private List<Role> roles;
    private List<RoleDto> roleDtos;

    @BeforeEach
    void setUp() {
        role = getMockedRole1();
        roleDto = getMockedRoleDto1();
        roles = getMockedRoles();
        roleDtos = getMockedRoleDtos();
    }

    @Test
    void findAll_test() {
        given(roleRepository.findAll()).willReturn(roles);
        List<RoleDto> result = roleService.findAll();
        then(result).isEqualTo(roleDtos);
    }

    @Test
    void findById_validId_test() {
        given(roleRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(role));
        RoleDto result = roleService.findById(VALID_ID);
        then(result).isEqualTo(roleDto);
    }

    @Test
    void findById_invalidId_test() {
        thenThrownBy(() -> roleService.findById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(ROLE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_test() {
        given(roleRepository.save(any(Role.class))).willReturn(role);
        RoleDto result = roleService.save(roleDto);
        verify(roleRepository).save(role);
        then(result).isEqualTo(roleDto);
    }
}
