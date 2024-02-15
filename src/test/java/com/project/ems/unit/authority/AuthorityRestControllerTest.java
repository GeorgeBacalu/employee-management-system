package com.project.ems.unit.authority;

import com.project.ems.authority.AuthorityDto;
import com.project.ems.authority.AuthorityRestController;
import com.project.ems.authority.AuthorityService;
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
import static com.project.ems.mock.AuthorityMock.getMockedAuthorityDto1;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorityDtos;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthorityRestControllerTest {

    @InjectMocks
    private AuthorityRestController authorityRestController;

    @Mock
    private AuthorityService authorityService;

    @Spy
    private ModelMapper modelMapper;

    private AuthorityDto authorityDto;
    private List<AuthorityDto> authorityDtos;

    @BeforeEach
    void setUp() {
        authorityDto = getMockedAuthorityDto1();
        authorityDtos = getMockedAuthorityDtos();
    }

    @Test
    void findAll_test() {
        given(authorityService.findAll()).willReturn(authorityDtos);
        ResponseEntity<List<AuthorityDto>> response = authorityRestController.findAll();
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(authorityDtos);
    }

    @Test
    void findById_test() {
        given(authorityService.findById(VALID_ID)).willReturn(authorityDto);
        ResponseEntity<AuthorityDto> response = authorityRestController.findById(VALID_ID);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(authorityDto);
    }

    @Test
    void save_test() {
        given(authorityService.save(authorityDto)).willReturn(authorityDto);
        ResponseEntity<AuthorityDto> response = authorityRestController.save(authorityDto);
        then(response).isNotNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        then(response.getBody()).isEqualTo(authorityDto);
    }
}
