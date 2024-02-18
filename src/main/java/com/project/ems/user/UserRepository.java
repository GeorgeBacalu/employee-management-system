package com.project.ems.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.project.ems.constants.Constants.USER_ACTIVE_FILTER_QUERY;
import static com.project.ems.constants.Constants.USER_FILTER_QUERY;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByIsActiveTrue();

    Page<User> findAllByIsActiveTrue(Pageable pageable);

    @Query(USER_FILTER_QUERY) Page<User> findAllByKey(Pageable pageable, @Param("key") String key);

    @Query(USER_ACTIVE_FILTER_QUERY) Page<User> findAllActiveByKey(Pageable pageable, @Param("key") String key);
}
