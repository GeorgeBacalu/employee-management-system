package com.project.ems.trainer;

import java.util.List;

public interface TrainerService {

    List<Trainer> findAll();

    Trainer findById(Integer id);

    Trainer save(Trainer trainer);

    Trainer updateById(Trainer trainer, Integer id);

    Trainer disableById(Integer id);
}
