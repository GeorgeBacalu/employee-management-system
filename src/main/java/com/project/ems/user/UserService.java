package com.project.ems.user;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    UserDto findById(Integer id);

    UserDto save(UserDto userDto);

    UserDto updateById(UserDto userDto, Integer id);

    UserDto disableById(Integer id);

    List<UserDto> convertToDtos(List<User> users);

    List<User> convertToEntities(List<UserDto> userDtos);

    UserDto convertToDto(User user);

    User convertToEntity(UserDto userDto);

    User findEntityById(Integer id);
}
