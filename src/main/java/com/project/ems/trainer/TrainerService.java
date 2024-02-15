package com.project.ems.trainer;

import java.util.List;

public interface TrainerService {

    List<TrainerDto> findAll();

    List<TrainerDto> findAllActive();

    TrainerDto findById(Integer id);

    TrainerDto save(TrainerDto trainerDto);

    TrainerDto updateById(TrainerDto trainerDto, Integer id);

    TrainerDto disableById(Integer id);

    List<TrainerDto> convertToDtos(List<Trainer> trainers);

    List<Trainer> convertToEntities(List<TrainerDto> trainerDtos);

    TrainerDto convertToDto(Trainer trainer);

    Trainer convertToEntity(TrainerDto trainerDto);

    Trainer findEntityById(Integer id);
}
