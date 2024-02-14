package com.project.ems.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Override
    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }

    @Override
    public Authority findById(Integer id) {
        return authorityRepository.findById(id).orElseThrow(() -> new RuntimeException("Authority with id " + id + " not found"));
    }

    @Override
    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }
}
