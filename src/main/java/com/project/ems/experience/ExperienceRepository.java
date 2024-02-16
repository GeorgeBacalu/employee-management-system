package com.project.ems.experience;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> {

    @Query("SELECT e FROM Experience e WHERE LOWER(CONCAT(e.title, ' ', e.organization, ' ', e.description, ' ', e.type, ' ', e.startedAt, ' ', e.finishedAt)) LIKE %:key%")
    Page<Experience> findAllByKey(Pageable pageable, @Param("key") String key);
}
