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
import org.springframework.ui.Model;

import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.RoleMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

    @InjectMocks
    private RoleController roleController;

    @Mock
    private RoleService roleService;

    @Spy
    private Model model;

    @Spy
    private ModelMapper modelMapper;

    private Role role;
    private List<Role> roles;
    private RoleDto roleDto;
    private List<RoleDto> roleDtos;

    @BeforeEach
    void setUp() {
        role = getMockedRole1();
        roles = getMockedRoles();
        roleDto = getMockedRoleDto1();
        roleDtos = getMockedRoleDtos();
    }

    @Test
    void findAllPage_test() {
        given(roleService.findAll()).willReturn(roleDtos);
        given(roleService.convertToEntities(roleDtos)).willReturn(roles);
        given(model.getAttribute(ROLES_ATTRIBUTE)).willReturn(roles);
        String viewName = roleController.findAllPage(model);
        then(viewName).isEqualTo(ROLES_VIEW);
        then(model.getAttribute(ROLES_ATTRIBUTE)).isEqualTo(roles);
    }

    @Test
    void findByIdPage_validId_test() {
        given(roleService.findEntityById(VALID_ID)).willReturn(role);
        given(model.getAttribute(ROLE_ATTRIBUTE)).willReturn(role);
        String viewName = roleController.findByIdPage(model, VALID_ID);
        then(viewName).isEqualTo(ROLE_DETAILS_VIEW);
        then(model.getAttribute(ROLE_ATTRIBUTE)).isEqualTo(role);
    }

    @Test
    void findByIdPage_invalidId_test() {
        String message = String.format(ROLE_NOT_FOUND, INVALID_ID);
        given(roleService.findEntityById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> roleController.findByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void getSavePage_test() {
        given(model.getAttribute(ROLE_DTO_ATTRIBUTE)).willReturn(new RoleDto());
        String viewName = roleController.getSavePage(model);
        then(viewName).isEqualTo(SAVE_ROLE_VIEW);
        then(model.getAttribute(ROLE_DTO_ATTRIBUTE)).isEqualTo(new RoleDto());
    }

    @Test
    void save_test() {
        String viewName = roleController.save(roleDto);
        then(viewName).isEqualTo(REDIRECT_ROLES_VIEW);
    }
}
