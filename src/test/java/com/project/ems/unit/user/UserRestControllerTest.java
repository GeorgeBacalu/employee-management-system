package com.project.ems.unit.user;

import com.project.ems.user.UserDto;
import com.project.ems.user.UserRestController;
import com.project.ems.user.UserService;
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
import static com.project.ems.mock.UserMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserRestControllerTest {

    @InjectMocks
    private UserRestController userRestController;

    @Mock
    private UserService userService;

    @Spy
    private ModelMapper modelMapper;

    private UserDto userDto1;
    private UserDto userDto2;
    private List<UserDto> userDtos;

    @BeforeEach
    void setUp() {
        userDto1 = getMockedUserDto1();
        userDto2 = getMockedUserDto2();
        userDtos = getMockedUserDtos();
    }

    @Test
    void findAll_test() {
        given(userService.findAll()).willReturn(userDtos);
        ResponseEntity<List<UserDto>> response = userRestController.findAll();
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(userDtos);
    }

    @Test
    void findById_test() {
        given(userService.findById(VALID_ID)).willReturn(userDto1);
        ResponseEntity<UserDto> response = userRestController.findById(VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(userDto1);
    }

    @Test
    void save_test() {
        given(userService.save(userDto1)).willReturn(userDto1);
        ResponseEntity<UserDto> response = userRestController.save(userDto1);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        then(response.getBody()).isEqualTo(userDto1);
    }

    @Test
    void updateById_test() {
        given(userService.updateById(userDto2, VALID_ID)).willReturn(userDto2);
        ResponseEntity<UserDto> response = userRestController.updateById(userDto2, VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(userDto2);
    }

    @Test
    void disableById_test() {
        given(userService.disableById(VALID_ID)).willReturn(userDto1);
        ResponseEntity<UserDto> response = userRestController.disableById(VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(userDto1);
    }
}
