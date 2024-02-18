package com.project.ems.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    List<UserDto> findAllActive();

    Page<UserDto> findAllByKey(Pageable pageable, String key);

    Page<UserDto> findAllActiveByKey(Pageable pageable, String key);

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
