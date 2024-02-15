package com.project.ems.unit.authority;

import com.project.ems.authority.*;
import com.project.ems.exception.ResourceNotFoundException;
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
import static com.project.ems.mock.AuthorityMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthorityServiceImplTest {

    @InjectMocks
    private AuthorityServiceImpl authorityService;

    @Mock
    private AuthorityRepository authorityRepository;

    @Spy
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
