package com.project.ems.service;

import com.project.ems.entity.User;
import com.project.ems.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    @Override
    public User save(User user) {
        user.setIsActive(true);
        return userRepository.save(user);
    }

    @Override
    public User updateById(User user, Integer id) {
        User userToUpdate = findById(id);
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setMobile(user.getMobile());
        userToUpdate.setAddress(user.getAddress());
        userToUpdate.setBirthday(user.getBirthday());
        userToUpdate.setRole(user.getRole());
        userToUpdate.setAuthorities(user.getAuthorities());
        return userRepository.save(userToUpdate);
    }

    @Override
    public User disableById(Integer id) {
        User userToDisable = findById(id);
        userToDisable.setIsActive(false);
        return userRepository.save(userToDisable);
    }
}
