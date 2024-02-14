package com.project.ems.role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findById(Integer id);
    
    Role save(Role role);
}