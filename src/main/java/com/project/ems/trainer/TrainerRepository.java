package com.project.ems.trainer;

import com.project.ems.experience.Experience;
import com.project.ems.study.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.project.ems.constants.Constants.TRAINER_ACTIVE_FILTER_QUERY;
import static com.project.ems.constants.Constants.TRAINER_FILTER_QUERY;

public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

    List<Trainer> findAllByIsActiveTrue();

    Page<Trainer> findAllByIsActiveTrue(Pageable pageable);

    List<Trainer> findAllByExperiencesContains(Experience experience);

    List<Trainer> findAllByStudiesContains(Study study);

    List<Trainer> findAllBySupervisingTrainer(Trainer trainer);

    @Query(TRAINER_FILTER_QUERY) Page<Trainer> findAllByKey(Pageable pageable, @Param("key") String key);

    @Query(TRAINER_ACTIVE_FILTER_QUERY) Page<Trainer> findAllActiveByKey(Pageable pageable, @Param("key") String key);
}
