package com.project.ems.base;

import com.google.common.reflect.TypeToken;
import com.project.ems.authority.Authority;
import com.project.ems.authority.AuthorityService;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.role.RoleService;
import com.project.ems.study.Study;
import com.project.ems.study.StudyService;
import com.project.ems.user.User;
import com.project.ems.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.ems.constants.Constants.USER_NOT_FOUND;

@RequiredArgsConstructor
public abstract class BaseUserServiceImpl<E, D, R extends BaseUserRepository<E>> implements BaseUserService<E, D> {

    protected final R userRepository;
    protected final RoleService roleService;
    protected final AuthorityService authorityService;
    protected final ExperienceService experienceService;
    protected final StudyService studyService;
    protected final ModelMapper modelMapper;

    @Override
    public List<D> findAll() {
        return convertToDtos(userRepository.findAll());
    }

    @Override
    public List<D> findAllActive() {
        return convertToDtos(userRepository.findAllByIsActiveTrue());
    }

    @Override
    public Page<D> findAllByKey(Pageable pageable, String key) {
        Page<E> usersPage = key.trim().isEmpty() ? userRepository.findAll(pageable) : userRepository.findAllByKey(pageable, key.toLowerCase());
        return usersPage.hasContent() ? usersPage.map(this::convertToDto) : Page.empty();
    }

    @Override
    public Page<D> findAllActiveByKey(Pageable pageable, String key) {
        Page<E> activeUsersPage = key.trim().isEmpty() ? userRepository.findAllByIsActiveTrue(pageable) : userRepository.findAllActiveByKey(pageable, key.toLowerCase());
        return activeUsersPage.hasContent() ? activeUsersPage.map(this::convertToDto) : Page.empty();
    }

    @Override
    public D findById(Integer id) {
        return convertToDto(findEntityById(id));
    }

    @Override
    public D save(D userDto) {
        E userToSave = convertToEntity(userDto);
        if (userToSave instanceof User user) {
            user.setIsActive(true);
        }
        return convertToDto(userRepository.save(userToSave));
    }

    @Override
    public D updateById(D updateUserDto, Integer id) {
        E userToUpdate = findEntityById(id);
        updateEntityFromDto(userToUpdate, updateUserDto);
        return convertToDto(userRepository.save(userToUpdate));
    }

    @Override
    public D disableById(Integer id) {
        E userToDisable = findEntityById(id);
        if (userToDisable instanceof User user) {
            user.setIsActive(false);
        }
        return convertToDto(userRepository.save(userToDisable));
    }

    @Override
    public List<D> convertToDtos(List<E> users) {
        return users.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<E> convertToEntities(List<D> userDtos) {
        return userDtos.stream().map(this::convertToEntity).toList();
    }

    @Override
    public D convertToDto(E userToMap) {
        D mappedUserDto = modelMapper.map(userToMap, new TypeToken<D>(getClass()) {
        }.getType());
        if (mappedUserDto instanceof UserDto userDto && userToMap instanceof User user) {
            userDto.setAuthoritiesIds(user.getAuthorities().stream().map(Authority::getId).toList());
            userDto.setExperiencesIds(user.getExperiences().stream().map(Experience::getId).toList());
            userDto.setStudiesIds(user.getStudies().stream().map(Study::getId).toList());
        }
        return mappedUserDto;
    }

    @Override
    public E convertToEntity(D userDtoToMap) {
        E mappedUser = modelMapper.map(userDtoToMap, new TypeToken<E>(getClass()) {
        }.getType());
        if (mappedUser instanceof User user && userDtoToMap instanceof UserDto userDto) {
            user.setRole(roleService.findEntityById(userDto.getRoleId()));
            user.setAuthorities(userDto.getAuthoritiesIds().stream().map(authorityService::findEntityById).toList());
            user.setExperiences(userDto.getExperiencesIds().stream().map(experienceService::findEntityById).toList());
            user.setStudies(userDto.getStudiesIds().stream().map(studyService::findEntityById).toList());
        }
        return mappedUser;
    }

    @Override
    public E findEntityById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND, id)));
    }

    @Override
    public void updateEntityFromDto(E userToUpdate, D updatedUserDto) {
        if (userToUpdate instanceof User user && updatedUserDto instanceof UserDto userDto) {
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setMobile(userDto.getMobile());
            user.setAddress(userDto.getAddress());
            user.setBirthday(userDto.getBirthday());
            user.setRole(roleService.findEntityById(userDto.getRoleId()));
            user.setAuthorities(userDto.getAuthoritiesIds().stream().map(authorityService::findEntityById).collect(Collectors.toList()));
            user.setEmploymentType(userDto.getEmploymentType());
            user.setPosition(userDto.getPosition());
            user.setGrade(userDto.getGrade());
            user.setSalary(userDto.getSalary());
            user.setHiredAt(userDto.getHiredAt());
            user.setExperiences(userDto.getExperiencesIds().stream().map(experienceService::findEntityById).collect(Collectors.toList()));
            user.setStudies(userDto.getStudiesIds().stream().map(studyService::findEntityById).collect(Collectors.toList()));
        }
    }
}
