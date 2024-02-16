package com.project.ems.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByIsActiveTrue();

    Page<User> findAllByIsActiveTrue(Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.authorities a WHERE LOWER(CONCAT(u.name, ' ', u.email, ' ', u.mobile, ' ', u.address, ' ', u.birthday, ' ', u.role.type, ' ', a.type)) LIKE %:key%")
    Page<User> findAllByKey(Pageable pageable, @Param("key") String key);

    @Query("SELECT u FROM User u JOIN u.authorities a WHERE LOWER(CONCAT(u.name, ' ', u.email, ' ', u.mobile, ' ', u.address, ' ', u.birthday, ' ', u.role.type, ' ', a.type)) LIKE %:key% AND u.isActive = true")
    Page<User> findAllActiveByKey(Pageable pageable, @Param("key") String key);
}
