package com.project.ems.trainer;

import com.project.ems.experience.Experience;
import com.project.ems.study.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

    List<Trainer> findAllByIsActiveTrue();

    Page<Trainer> findAllByIsActiveTrue(Pageable pageable);

    List<Trainer> findAllByExperiencesContains(Experience experience);

    List<Trainer> findAllByStudiesContains(Study study);

    List<Trainer> findAllBySupervisingTrainer(Trainer trainer);

    @Query("SELECT t FROM Trainer t JOIN t.authorities a JOIN t.experiences ex JOIN t.studies s WHERE LOWER(CONCAT(t.name, ' ', t.email, ' ', t.mobile, ' ', t.address, ' ', t.birthday, ' ', t.role.type, ' ', a.type, ' ', t.employmentType, ' ', t.position, ' ', t.grade, ' ', t.salary, ' ', t.hiredAt, ' ', ex.title, ' ', s.title, ' ', t.supervisingTrainer.name)) LIKE %:key%")
    Page<Trainer> findAllByKey(Pageable pageable, @Param("key") String key);

    @Query("SELECT t FROM Trainer t JOIN t.authorities a JOIN t.experiences ex JOIN t.studies s WHERE LOWER(CONCAT(t.name, ' ', t.email, ' ', t.mobile, ' ', t.address, ' ', t.birthday, ' ', t.role.type, ' ', a.type, ' ', t.employmentType, ' ', t.position, ' ', t.grade, ' ', t.salary, ' ', t.hiredAt, ' ', ex.title, ' ', s.title, ' ', t.supervisingTrainer.name)) LIKE %:key% AND t.isActive = true")
    Page<Trainer> findAllActiveByKey(Pageable pageable, @Param("key") String key);
}
