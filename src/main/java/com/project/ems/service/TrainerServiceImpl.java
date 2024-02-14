package com.project.ems.service;

import com.project.ems.entity.Trainer;
import com.project.ems.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    @Override
    public List<Trainer> findAll() {
        return trainerRepository.findAll();
    }

    @Override
    public Trainer findById(Integer id) {
        return trainerRepository.findById(id).orElseThrow(() -> new RuntimeException("Trainer with id " + id + " not found"));
    }

    @Override
    public Trainer save(Trainer trainer) {
        trainer.setIsActive(true);
        return trainerRepository.save(trainer);
    }

    @Override
    public Trainer updateById(Trainer trainer, Integer id) {
        Trainer trainerToUpdate = findById(id);
        trainerToUpdate.setName(trainer.getName());
        trainerToUpdate.setEmail(trainer.getEmail());
        trainerToUpdate.setPassword(trainer.getPassword());
        trainerToUpdate.setMobile(trainer.getMobile());
        trainerToUpdate.setAddress(trainer.getAddress());
        trainerToUpdate.setBirthday(trainer.getBirthday());
        trainerToUpdate.setRole(trainer.getRole());
        trainerToUpdate.setAuthorities(trainer.getAuthorities());
        trainerToUpdate.setEmploymentType(trainer.getEmploymentType());
        trainerToUpdate.setPosition(trainer.getPosition());
        trainerToUpdate.setGrade(trainer.getGrade());
        trainerToUpdate.setSalary(trainer.getSalary());
        trainerToUpdate.setHiredAt(trainer.getHiredAt());
        trainerToUpdate.setExperiences(trainer.getExperiences());
        trainerToUpdate.setStudies(trainer.getStudies());
        trainerToUpdate.setSupervisingTrainer(trainer.getSupervisingTrainer());
        trainerToUpdate.setNrTrainees(trainer.getNrTrainees());
        trainerToUpdate.setMaxTrainees(trainer.getMaxTrainees());
        return trainerRepository.save(trainerToUpdate);
    }

    @Override
    public Trainer disableById(Integer id) {
        Trainer trainerToDisable = findById(id);
        trainerToDisable.setIsActive(false);
        return trainerRepository.save(trainerToDisable);
    }
}
