package com.project.ems.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseUserService<E, D> {

    List<D> findAll();

    List<D> findAllActive();

    Page<D> findAllByKey(Pageable pageable, String key);

    Page<D> findAllActiveByKey(Pageable pageable, String key);

    D findById(Integer id);

    D save(D userDto);

    D updateById(D userDto, Integer id);

    D disableById(Integer id);

    List<D> convertToDtos(List<E> users);

    List<E> convertToEntities(List<D> userDtos);

    D convertToDto(E user);

    E convertToEntity(D userDto);

    E findEntityById(Integer id);

    void updateEntityFromDto(E user, D userDto);
}
