package com.project.ems.user;

import com.project.ems.authority.AuthorityService;
import com.project.ems.base.BaseUserServiceImpl;
import com.project.ems.experience.ExperienceService;
import com.project.ems.role.RoleService;
import com.project.ems.study.StudyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseUserServiceImpl<User, UserDto, UserRepository> implements UserService {

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, AuthorityService authorityService, ExperienceService experienceService, StudyService studyService, ModelMapper modelMapper) {
        super(userRepository, roleService, authorityService, experienceService, studyService, modelMapper);
    }
}
