package com.project.ems.authority;

import com.project.ems.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<AuthorityDto> findAll() {
        return convertToDtos(authorityRepository.findAll());
    }

    @Override
    public AuthorityDto findById(Integer id) {
        return convertToDto(findEntityById(id));
    }

    @Override
    public AuthorityDto save(AuthorityDto authorityDto) {
        return convertToDto(authorityRepository.save(convertToEntity(authorityDto)));
    }

    @Override
    public List<AuthorityDto> convertToDtos(List<Authority> authorities) {
        return authorities.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<Authority> convertToEntities(List<AuthorityDto> authorityDtos) {
        return authorityDtos.stream().map(this::convertToEntity).toList();
    }

    @Override
    public AuthorityDto convertToDto(Authority authority) {
        return modelMapper.map(authority, AuthorityDto.class);
    }

    @Override
    public Authority convertToEntity(AuthorityDto authorityDto) {
        return modelMapper.map(authorityDto, Authority.class);
    }

    @Override
    public Authority findEntityById(Integer id) {
        return authorityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Authority with id " + id + " not found"));
    }
}
