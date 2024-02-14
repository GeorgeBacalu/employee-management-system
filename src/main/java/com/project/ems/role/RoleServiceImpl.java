package com.project.ems.role;

import com.project.ems.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<RoleDto> findAll() {
        return convertToDtos(roleRepository.findAll());
    }

    @Override
    public RoleDto findById(Integer id) {
        return convertToDto(findEntityById(id));
    }

    @Override
    public RoleDto save(RoleDto roleDto) {
        return convertToDto(roleRepository.save(convertToEntity(roleDto)));
    }

    @Override
    public List<RoleDto> convertToDtos(List<Role> roles) {
        return roles.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<Role> convertToEntities(List<RoleDto> roleDtos) {
        return roleDtos.stream().map(this::convertToEntity).toList();
    }

    @Override
    public RoleDto convertToDto(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }

    @Override
    public Role convertToEntity(RoleDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }

    @Override
    public Role findEntityById(Integer id) {
        return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role with id " + id + " not found"));
    }
}
