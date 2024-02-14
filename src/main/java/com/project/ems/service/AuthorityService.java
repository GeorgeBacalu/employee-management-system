package com.project.ems.service;

import com.project.ems.entity.Authority;

import java.util.List;

public interface AuthorityService {

    List<Authority> findAll();

    Authority findById(Integer id);

    Authority save(Authority authority);
}
