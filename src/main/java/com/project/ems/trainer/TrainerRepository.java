package com.project.ems.trainer;

import com.project.ems.experience.Experience;
import com.project.ems.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

    List<Trainer> findAllByIsActiveTrue();

    List<Trainer> findAllByExperiencesContains(Experience experience);

    List<Trainer> findAllByStudiesContains(Study study);

    List<Trainer> findAllBySupervisingTrainer(Trainer trainer);
}
