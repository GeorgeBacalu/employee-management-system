package com.project.ems.authority;

import java.util.List;

public interface AuthorityService {

    List<Authority> findAll();

    Authority findById(Integer id);

    Authority save(Authority authority);
}
