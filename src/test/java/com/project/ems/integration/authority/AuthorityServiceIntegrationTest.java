package com.project.ems.integration.authority;

import com.project.ems.authority.*;
import com.project.ems.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;
import java.util.Optional;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.AuthorityMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AuthorityServiceIntegrationTest {

    @Autowired
    private AuthorityServiceImpl authorityService;

    @MockBean
    private AuthorityRepository authorityRepository;

    @SpyBean
    private ModelMapper modelMapper;

    private Authority authority;
    private AuthorityDto authorityDto;
    private List<Authority> authorities;
    private List<AuthorityDto> authorityDtos;

    @BeforeEach
    void setUp() {
        authority = getMockedAuthority1();
        authorityDto = getMockedAuthorityDto1();
        authorities = getMockedAuthorities();
        authorityDtos = getMockedAuthorityDtos();
    }

    @Test
    void findAll_test() {
        given(authorityRepository.findAll()).willReturn(authorities);
        List<AuthorityDto> result = authorityService.findAll();
        then(result).isEqualTo(authorityDtos);
    }

    @Test
    void findById_validId_test() {
        given(authorityRepository.findById(VALID_ID)).willReturn(Optional.ofNullable(authority));
        AuthorityDto result = authorityService.findById(VALID_ID);
        then(result).isEqualTo(authorityDto);
    }

    @Test
    void findById_invalidId_test() {
        thenThrownBy(() -> authorityService.findById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(AUTHORITY_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_test() {
        given(authorityRepository.save(any(Authority.class))).willReturn(authority);
        AuthorityDto result = authorityService.save(authorityDto);
        verify(authorityRepository).save(authority);
        then(result).isEqualTo(authorityDto);
    }
}
