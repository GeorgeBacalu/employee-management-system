package com.project.ems.unit.user;

import com.project.ems.exception.InvalidRequestException;
import com.project.ems.user.UserDto;
import com.project.ems.user.UserRestController;
import com.project.ems.user.UserService;
import com.project.ems.wrapper.PageWrapper;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.UserMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    private List<UserDto> activeUserDtos;
    private List<UserDto> userDtosPage1;
    private List<UserDto> userDtosPage2;
    private List<UserDto> userDtosPage3;

    @BeforeEach
    void setUp() {
        userDto1 = getMockedUserDto1();
        userDto2 = getMockedUserDto2();
        userDtos = getMockedUserDtos();
        activeUserDtos = getMockedActiveUserDtos();
        userDtosPage1 = getMockedUserDtosPage1();
        userDtosPage2 = getMockedUserDtosPage2();
        userDtosPage3 = getMockedUserDtosPage3();
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
    void findAllActive_test() {
        given(userService.findAllActive()).willReturn(activeUserDtos);
        ResponseEntity<List<UserDto>> response = userRestController.findAllActive();
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(activeUserDtos);
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

    @ParameterizedTest
    @CsvSource({"0, ${USER_FILTER_KEY}", "1, ${USER_FILTER_KEY}", "2, ${USER_FILTER_KEY}", "0, ''", "1, ''", "2, ''"})
    void findAllByKey_test(int page, String key) {
        Pair<Pageable, List<UserDto>> pageableUserDtosPair = switch (page) {
            case 0 -> Pair.of(PAGEABLE_PAGE1, userDtosPage1);
            case 1 -> Pair.of(PAGEABLE_PAGE2, userDtosPage2);
            case 2 -> Pair.of(PAGEABLE_PAGE3, key.trim().isEmpty() ? userDtosPage3 : Collections.emptyList());
            default -> throw new InvalidRequestException(INVALID_PAGE_NUMBER + page);
        };
        Page<UserDto> filteredUserDtosPage = new PageImpl<>(pageableUserDtosPair.getRight());
        given(userService.findAllByKey(any(Pageable.class), eq(key))).willReturn(filteredUserDtosPage);
        ResponseEntity<PageWrapper<UserDto>> response = userRestController.findAllByKey(pageableUserDtosPair.getLeft(), key);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody().getContent()).isEqualTo(filteredUserDtosPage.getContent());
    }

    @ParameterizedTest
    @CsvSource({"0, ${USER_FILTER_KEY}", "1, ${USER_FILTER_KEY}", "0, ''", "1, ''"})
    void findAllActiveByKey_test(int page, String key) {
        Pair<Pageable, List<UserDto>> pageableActiveUserDtosPair = switch (page) {
            case 0 -> Pair.of(PAGEABLE_PAGE1, userDtosPage1);
            case 1 -> Pair.of(PAGEABLE_PAGE2, key.trim().isEmpty() ? userDtosPage2 : Collections.emptyList());
            default -> throw new InvalidRequestException(INVALID_PAGE_NUMBER + page);
        };
        Page<UserDto> filteredActiveUserDtosPage = new PageImpl<>(pageableActiveUserDtosPair.getRight());
        given(userService.findAllActiveByKey(any(Pageable.class), eq(key))).willReturn(filteredActiveUserDtosPage);
        ResponseEntity<PageWrapper<UserDto>> response = userRestController.findAllActiveByKey(pageableActiveUserDtosPair.getLeft(), key);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody().getContent()).isEqualTo(filteredActiveUserDtosPage.getContent());
    }
}
