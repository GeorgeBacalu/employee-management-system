package com.project.ems.user;

import com.project.ems.authority.Authority;
import com.project.ems.authority.AuthorityService;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.ems.constants.Constants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final AuthorityService authorityService;
    private final ModelMapper modelMapper;

    @Override
    public List<UserDto> findAll() {
        return convertToDtos(userRepository.findAll());
    }

    @Override
    public List<UserDto> findAllActive() {
        return convertToDtos(userRepository.findAllByIsActiveTrue());
    }

    @Override
    public UserDto findById(Integer id) {
        return convertToDto(findEntityById(id));
    }

    @Override
    public UserDto save(UserDto userDto) {
        User userToSave = convertToEntity(userDto);
        userToSave.setIsActive(true);
        return convertToDto(userRepository.save(userToSave));
    }

    @Override
    public UserDto updateById(UserDto userDto, Integer id) {
        User userToUpdate = findEntityById(id);
        updateEntityFromDto(userToUpdate, userDto);
        return convertToDto(userRepository.save(userToUpdate));
    }

    @Override
    public UserDto disableById(Integer id) {
        User userToDisable = findEntityById(id);
        userToDisable.setIsActive(false);
        return convertToDto(userRepository.save(userToDisable));
    }

    @Override
    public List<UserDto> convertToDtos(List<User> users) {
        return users.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<User> convertToEntities(List<UserDto> userDtos) {
        return userDtos.stream().map(this::convertToEntity).toList();
    }

    @Override
    public UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setAuthoritiesIds(user.getAuthorities().stream().map(Authority::getId).toList());
        return userDto;
    }

    @Override
    public User convertToEntity(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setRole(roleService.findEntityById(userDto.getRoleId()));
        user.setAuthorities(userDto.getAuthoritiesIds().stream().map(authorityService::findEntityById).toList());
        return user;
    }

    @Override
    public User findEntityById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND, id)));
    }

    private void updateEntityFromDto(User user, UserDto userDto) {
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setMobile(userDto.getMobile());
        user.setAddress(userDto.getAddress());
        user.setBirthday(userDto.getBirthday());
        user.setRole(roleService.findEntityById(userDto.getRoleId()));
        user.setAuthorities(userDto.getAuthoritiesIds().stream().map(authorityService::findEntityById).collect(Collectors.toList()));
    }
}
