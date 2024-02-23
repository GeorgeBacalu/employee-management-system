package com.project.ems.trainer;

import com.project.ems.base.BaseUserRepository;

import java.util.List;

public interface TrainerRepository extends BaseUserRepository<Trainer> {

    List<Trainer> findAllBySupervisingTrainer(Trainer trainer);
}
