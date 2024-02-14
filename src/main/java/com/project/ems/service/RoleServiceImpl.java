package com.project.ems.service;

import com.project.ems.entity.Role;
import com.project.ems.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Integer id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role with id " + id + " not found"));
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
