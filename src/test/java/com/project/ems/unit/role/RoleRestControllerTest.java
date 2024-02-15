package com.project.ems.unit.role;

import com.project.ems.role.RoleDto;
import com.project.ems.role.RoleRestController;
import com.project.ems.role.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.project.ems.constants.Constants.VALID_ID;
import static com.project.ems.mock.RoleMock.getMockedRoleDto1;
import static com.project.ems.mock.RoleMock.getMockedRoleDtos;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RoleRestControllerTest {

    @InjectMocks
    private RoleRestController roleRestController;

    @Mock
    private RoleService roleService;

    @Spy
    private ModelMapper modelMapper;

    private RoleDto roleDto;
    private List<RoleDto> roleDtos;

    @BeforeEach
    void setUp() {
        roleDto = getMockedRoleDto1();
        roleDtos = getMockedRoleDtos();
    }

    @Test
    void findAll_test() {
        given(roleService.findAll()).willReturn(roleDtos);
        ResponseEntity<List<RoleDto>> response = roleRestController.findAll();
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(roleDtos);
    }

    @Test
    void findById_test() {
        given(roleService.findById(VALID_ID)).willReturn(roleDto);
        ResponseEntity<RoleDto> response = roleRestController.findById(VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(roleDto);
    }

    @Test
    void save_test() {
        given(roleService.save(roleDto)).willReturn(roleDto);
        ResponseEntity<RoleDto> response = roleRestController.save(roleDto);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        then(response.getBody()).isEqualTo(roleDto);
    }
}
