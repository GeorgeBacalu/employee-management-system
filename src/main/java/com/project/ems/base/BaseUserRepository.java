package com.project.ems.base;

import com.project.ems.experience.Experience;
import com.project.ems.study.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.project.ems.constants.Constants.USER_ACTIVE_FILTER_QUERY;
import static com.project.ems.constants.Constants.USER_FILTER_QUERY;

@NoRepositoryBean
public interface BaseUserRepository<E> extends JpaRepository<E, Integer> {

    List<E> findAllByIsActiveTrue();

    Page<E> findAllByIsActiveTrue(Pageable pageable);

    List<E> findAllByExperiencesContains(Experience experience);

    List<E> findAllByStudiesContains(Study study);

    @Query(value = USER_FILTER_QUERY, nativeQuery = true) Page<E> findAllByKey(Pageable pageable, @Param("key") String key);

    @Query(value = USER_ACTIVE_FILTER_QUERY, nativeQuery = true) Page<E> findAllActiveByKey(Pageable pageable, @Param("key") String key);
}
