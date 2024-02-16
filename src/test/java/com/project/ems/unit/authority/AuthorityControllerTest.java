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
import org.springframework.ui.Model;

import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.AuthorityMock.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthorityControllerTest {

    @InjectMocks
    private AuthorityController authorityController;

    @Mock
    private AuthorityService authorityService;

    @Spy
    private Model model;

    @Spy
    private ModelMapper modelMapper;

    private Authority authority;
    private List<Authority> authorities;
    private AuthorityDto authorityDto;
    private List<AuthorityDto> authorityDtos;

    @BeforeEach
    void setUp() {
        authority = getMockedAuthority1();
        authorities = getMockedAuthorities();
        authorityDto = getMockedAuthorityDto1();
        authorityDtos = getMockedAuthorityDtos();
    }

    @Test
    void findAllPage_test() {
        given(authorityService.findAll()).willReturn(authorityDtos);
        given(authorityService.convertToEntities(authorityDtos)).willReturn(authorities);
        given(model.getAttribute(AUTHORITIES_ATTRIBUTE)).willReturn(authorities);
        String viewName = authorityController.findAllPage(model);
        then(viewName).isEqualTo(AUTHORITIES_VIEW);
        then(model.getAttribute(AUTHORITIES_ATTRIBUTE)).isEqualTo(authorities);
    }

    @Test
    void findByIdPage_validId_test() {
        given(authorityService.findEntityById(VALID_ID)).willReturn(authority);
        given(model.getAttribute(AUTHORITY_ATTRIBUTE)).willReturn(authority);
        String viewName = authorityController.findByIdPage(model, VALID_ID);
        then(viewName).isEqualTo(AUTHORITY_DETAILS_VIEW);
        then(model.getAttribute(AUTHORITY_ATTRIBUTE)).isEqualTo(authority);
    }

    @Test
    void findByIdPage_invalidId_test() {
        String message = String.format(AUTHORITY_NOT_FOUND, INVALID_ID);
        given(authorityService.findEntityById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        thenThrownBy(() -> authorityController.findByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void getSavePage_test() {
        given(model.getAttribute(AUTHORITY_DTO_ATTRIBUTE)).willReturn(new AuthorityDto());
        String viewName = authorityController.getSavePage(model);
        then(viewName).isEqualTo(SAVE_AUTHORITY_VIEW);
        then(model.getAttribute(AUTHORITY_DTO_ATTRIBUTE)).isEqualTo(new AuthorityDto());
    }

    @Test
    void save_test() {
        String viewName = authorityController.save(authorityDto);
        then(viewName).isEqualTo(REDIRECT_AUTHORITIES_VIEW);
    }
}
